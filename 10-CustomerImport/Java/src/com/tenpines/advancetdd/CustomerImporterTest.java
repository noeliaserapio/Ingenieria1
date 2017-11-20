package com.tenpines.advancetdd;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerImporterTest {

	CustomerSystem system;

	public StringReader validDataReader() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n");
		writer.write("A,San Martin,3322,Olivos,1636,BsAs\n");
		writer.write("A,Maipu,888,Florida,1122,Buenos Aires\n");
		writer.write("C,Juan,Perez,C,23-25666777-9\n");
		writer.write("A,Alem,1122,CABA,1001,CABA\n");
		
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	public StringReader validDataReaderIncorrectLetter() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n");
		writer.write("A,San Martin,3322,Olivos,1636,BsAs\n");
		writer.write("X,Maipu,888,Florida,1122,Buenos Aires\n");
		writer.write("C,Juan,Perez,C,23-25666777-9\n");
		writer.write("A,Alem,1122,CABA,1001,CABA\n");
		
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	public StringReader validDataReaderIncorrectLetterA() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n");
		writer.write("A,San Martin,3322,Olivos,1636,BsAs\n");
		writer.write("AA,Maipu,888,Florida,1122,Buenos Aires\n");
		writer.write("C,Juan,Perez,C,23-25666777-9\n");
		writer.write("A,Alem,1122,CABA,1001,CABA\n");
		
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	public StringReader validDataReaderIncorrectLetterC() {
		StringWriter writer = new StringWriter();
		writer.write("CC,Pepe,Sanchez,D,22333444\n");
		writer.write("A,San Martin,3322,Olivos,1636,BsAs\n");
		writer.write("A,Maipu,888,Florida,1122,Buenos Aires\n");
		writer.write("C,Juan,Perez,C,23-25666777-9\n");
		writer.write("A,Alem,1122,CABA,1001,CABA\n");
		
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}

	public void assertJuanPerezWasImportedCorrectly() {
		Customer customer;
		Address address;
		customer = (Customer) system.customerIdentifiedAs("C", "23-25666777-9");
		assertEquals("Juan",customer.getFirstName());
		assertEquals("Perez",customer.getLastName());
		assertEquals("C",customer.getIdentificationType());
		assertEquals("23-25666777-9",customer.getIdentificationNumber());

		assertEquals(1,customer.numberOfAddresses());
		address = customer.addressAt("Alem");
		assertEquals(1122,address.getStreetNumber());
		assertEquals("CABA", address.getTown());
		assertEquals(1001, address.getZipCode());
		assertEquals("CABA", address.getProvince());
	}

	public void assertPepeSanchezWasImportedCorrectly() {
		
		assertCustomerPepeSanchez();
		Customer customer = getCustomerPepeSanchez();
	
		assertEquals(2,customer.numberOfAddresses());
		Address address;
		assertPepeSanchezAddressSanMartin(customer);
		
		address = customer.addressAt("Maipu");
		assertEquals(888,address.getStreetNumber());
		assertEquals("Florida", address.getTown());
		assertEquals(1122, address.getZipCode());
		assertEquals("Buenos Aires", address.getProvince());
	}

	private void assertPepeSanchezAddressSanMartin(Customer customer) {
		Address address = customer.addressAt("San Martin");
		assertEquals(3322,address.getStreetNumber());
		assertEquals("Olivos", address.getTown());
		assertEquals(1636, address.getZipCode());
		assertEquals("BsAs", address.getProvince());
	}

	private void assertCustomerPepeSanchez() {
		Customer customer = getCustomerPepeSanchez();
		assertEquals("Pepe",customer.getFirstName());
		assertEquals("Sanchez",customer.getLastName());
		assertEquals("D",customer.getIdentificationType());
		assertEquals("22333444",customer.getIdentificationNumber());
	}

	private Customer getCustomerPepeSanchez() {
		Customer customer = (Customer) system.customerIdentifiedAs("D", "22333444");
		return customer;
	}

	@After
	public void closeSession() {
		system.commit();
		system.stop();
	}

	@Before
	public void openSession() {
		system = Environment.createCutomerSystem();
		system.configureSession();
		system.beginTransaction();
		
	}

	@Test
	public void test01importsValidDataCorrectly() throws IOException {
		new CustomerImporter(system, validDataReader()).importCustomers();

		assertEquals(2,system.numberOfCustomers());
		assertPepeSanchezWasImportedCorrectly();
		assertJuanPerezWasImportedCorrectly();		
	}
	
	@Test
	public void test02WithoutDataThereAreNotError() throws IOException {
		new CustomerImporter(system, validDataReaderEmpty()).importCustomers();
		
		assertEquals(0, system.numberOfCustomers());
				
	}
	
	@Test
	public void test03CanNotHaveAddressWithoutCustomer() throws IOException {
		CustomerImporter customerImporter = new CustomerImporter(system, invalidAdrressWithoutCustomer());
		
		try{
			customerImporter.importCustomers();
			fail();
		}catch(RuntimeException e){
			assertEquals(e.getMessage(), CustomerImporter.ADDRESS_WITHOUT_CUSTOMER);
			assertEquals(0, system.numberOfCustomers());
		}
	
	}
	
	@Test
	public void test04CanHaveCustomerWithoutAddress() throws IOException {
		new CustomerImporter(system, validDataCustomerWithoutAddress()).importCustomers();
		
		assertEquals(1,system.numberOfCustomers());
		assertCustomerPepeSanchez();
		assertEquals(0, getCustomerPepeSanchez().numberOfAddresses());
	
	}
	
	

	@Test
	public void test05eachLineShouldBeginWithCorrectLetter() throws IOException {
		CustomerImporter customerImporter = new CustomerImporter(system, validDataReaderIncorrectLetter());
		try {
			customerImporter.importCustomers();
		//	assertEquals(2,numberOfCustomers());
			//assertPepeSanchezWasImportedCorrectly();
		//	assertJuanPerezWasImportedCorrectly();	
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_BEGIN_FORMAT_LINE,e.getMessage());
			assertEquals(1, system.numberOfCustomers());
		}
	}
	
	@Test
	public void eachAddressLineShouldBeginWithUniqueA() throws IOException {
		try {
			new CustomerImporter(system, validDataReaderIncorrectLetterA()).importCustomers();
			assertEquals(2,system.numberOfCustomers());
			assertPepeSanchezWasImportedCorrectly();
			assertJuanPerezWasImportedCorrectly();	
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_BEGIN_FORMAT_LINE,e.getMessage());
		}
	}
	
	@Test
	public void eachCustomerLineShouldBeginWithUniqueC() throws IOException {
		try {
			new CustomerImporter(system, validDataReaderIncorrectLetterC()).importCustomers();
			assertEquals(2,system.numberOfCustomers());
			assertPepeSanchezWasImportedCorrectly();
			assertJuanPerezWasImportedCorrectly();	
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_BEGIN_FORMAT_LINE,e.getMessage());
		}
	}
	
	@Test
	public void identificationTypeCanOnlyBeDorC() throws IOException {
		FileReader reader = new FileReader("resources/inputIdentificationType.txt");
		
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_IDENTIFICATION_TYPE,e.getMessage());
		}
	}
	
	@Test
	public void customerNameCanNotBeEmpty() throws IOException {
		FileReader reader = new FileReader("resources/inputCustomerName.txt");
		
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_CUSTOMER_NAME_EMPTY,e.getMessage());
		}
	}
	
	@Test
	public void customerLastNameCanNotBeEmpty() throws IOException {
		FileReader reader = new FileReader("resources/inputCustomerLastName.txt");
		
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_CUSTOMER_LAST_NAME_EMPTY,e.getMessage());
		}
	}
	
	@Test
	public void customerIdentificationNumberCanNotBeEmpty() throws IOException {
		FileReader reader = new FileReader("resources/inputCustomerIdentificationNumber.txt");
		
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_CUSTOMER_IDENTIFICATION_NUMBER_EMPTY,e.getMessage());
		}
	}
	
	
	

	@Test
	public void addressStreetNameEmpty() throws IOException {
		FileReader reader = new FileReader("resources/inputAddressStreetEmpty.txt");
		
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_ADDRESS_STREET_NAME_EMPTY,e.getMessage());
		}
	}
	
	
	@Test
	public void addressTownEmpty() throws IOException {
		FileReader reader = new FileReader("resources/inputAddressTownEmpty.txt");
		
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_ADDRESS_TOWN_EMPTY,e.getMessage());
		}
	}
	
	@Test
	public void addressStreetNumberLowValue() throws IOException {
		FileReader reader = new FileReader("resources/inputStreetNumber.txt");
		
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_STREET_NUMBER_LOW,e.getMessage());
		}
	}
	
	@Test
	public void addressZipCodeLowValue() throws IOException {
		FileReader reader = new FileReader("resources/inputZipCode.txt");
		
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_ZIP_CODE_LOW,e.getMessage());
		}
	}
	
	@Test
	public void customerLowCantColumns() throws IOException {
		FileReader reader = new FileReader("resources/inputCustomerLowCantColumns.txt");	
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
		}
	}
	
	@Test
	public void customerHighCantColumns() throws IOException {
		FileReader reader = new FileReader("resources/inputCustomerHighCantColums.txt");	
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
		}
	}
	
	
	@Test
	public void customerLowDigitsIdentificationNumberWhenTypeIsD() throws IOException {
		FileReader reader = new FileReader("resources/customerLowDigitsIdentificationNumberWhenTypeIsD.txt");	
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_CUSTOMER_IDENTIFICATION_NUMBER,e.getMessage());
		}
	}
	
	@Test
	public void customerFormatIdentificationNumberWhenTypeIsC() throws IOException {
		FileReader reader = new FileReader("resources/customerFormatIdentificationNumberWhenTypeIsC.txt");	
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_CUSTOMER_IDENTIFICATION_NUMBER,e.getMessage());
		}
	}
	
	@Test
	public void addressLowCantColumns() throws IOException {
		FileReader reader = new FileReader("resources/inputAddressLowCantColumns.txt");	
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
		}
	}
	
	@Test
	public void addressHighCantColumns() throws IOException {
		FileReader reader = new FileReader("resources/inputAddressHighCantColums.txt");	
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
		}
	}
	
	@Test
	public void addressFormatStreetNumber() throws IOException {
		FileReader reader = new FileReader("resources/inputAddressFormatStreetNumber.txt");	
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_STREET_NUMBER,e.getMessage());
		}
	}
	
	@Test
	public void addressFormatZipCode() throws IOException {
		FileReader reader = new FileReader("resources/inputAddressFormatZipCode.txt");	
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_ZIP_CODE,e.getMessage());
		}
	}
	
	@Test
	public void addressFormatProvinceEmpty() throws IOException {
		FileReader reader = new FileReader("resources/inputAddressFormatProvinceEmpty.txt");	
		try {
			new CustomerImporter(system, reader).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_ADDRESS_PROVINCE_EMPTY,e.getMessage());
		}
	}
	
	public StringReader validDataReaderEmpty() {
		return new StringReader(new String());
	}
	
	private StringReader invalidAdrressWithoutCustomer() {
		StringWriter writer = new StringWriter();
		writer.write("A,San Martin,3322,Olivos,1636,BsAs\n");
	
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	private StringReader validDataCustomerWithoutAddress() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n");
	
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}

}
