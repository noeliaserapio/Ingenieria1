package com.tenpines.advancetdd;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerImporterTest {

	ErpSystem system;

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
			assertEquals(e.getMessage(), Importer.ADDRESS_WITHOUT_ASIGNATION);
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
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_BEGIN_FORMAT_LINE,e.getMessage());
			assertEquals(1, system.numberOfCustomers());
		}
	}
	
	@Test
	public void eachAddressLineShouldBeginWithUniqueA() throws IOException {
		try {
			new CustomerImporter(system, validDataReaderIncorrectLetterA()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_BEGIN_FORMAT_LINE,e.getMessage());
		}
	}
	
	@Test
	public void eachCustomerLineShouldBeginWithUniqueC() throws IOException {
		try {
			new CustomerImporter(system, validDataReaderIncorrectLetterC()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_BEGIN_FORMAT_LINE,e.getMessage());
		}
	}
	
	public StringReader inputIdentificationType() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n" +
						"A,San Martin,3322,Olivos,1636,BsAs\n"+
						"A,Maipu,888,Florida,1122,Buenos Aires\n"+
						"C,Juan,Perez,X,23-25666777-9\n"+
						"A,Alem,1122,CABA,1001,CABA\n");
							
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void identificationTypeCanOnlyBeDorC() throws IOException {		
		try {
			new CustomerImporter(system, inputIdentificationType()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_TYPE,e.getMessage());
		}
	}
	
	public StringReader inputCustomerName() {
		StringWriter writer = new StringWriter();
		writer.write("C,,Sanchez,D,22333444\n"+
				"A,San Martin,3322,Olivos,1636,BsAs\n"+
				"A,Maipu,888,Florida,1122,Buenos Aires\n"+
				"C,Juan,Perez,C,23-25666777-9\n"+
				"A,Alem,1122,CABA,1001,CABA\n");
							
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void customerNameCanNotBeEmpty() throws IOException {		
		try {
			new CustomerImporter(system, inputCustomerName()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_NAME_EMPTY,e.getMessage());
		}
	}
	
	public StringReader inputCustomerLastName() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,,C,23-25666777-9\n"+
					"A,Alem,1122,CABA,1001,CABA\n");
							
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}

	@Test
	public void customerLastNameCanNotBeEmpty() throws IOException {
		try {
			new CustomerImporter(system, inputCustomerLastName()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_CUSTOMER_LAST_NAME_EMPTY,e.getMessage());
		}
	}
	
	public StringReader inputCustomerIdentificationNumber() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,Perez,C,23-25666777-9\n"+
					"A,Alem,1122,CABA,1001,CABA\n");
							
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void customerIdentificationNumberCanNotBeEmpty() throws IOException {
		try {
			new CustomerImporter(system, inputCustomerIdentificationNumber()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_NUMBER_EMPTY,e.getMessage());
		}
	}
	
	public StringReader inputAddressStreetEmpty() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,Perez,C,23-25666777-9\n"+
					"A,Alem,1122,CABA,1001,CABA\n");
							
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}

	@Test
	public void addressStreetNameEmpty() throws IOException {		
		try {
			new CustomerImporter(system, inputAddressStreetEmpty()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_ADDRESS_STREET_NAME_EMPTY,e.getMessage());
		}
	}
	public StringReader inputAddressTownEmpty() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,,1122,Buenos Aires\n"+
					"C,Juan,Perez,C,23-25666777-9\n"+
					"A,Alem,1122,CABA,1001,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void addressTownEmpty() throws IOException {
		try {
			new CustomerImporter(system, inputAddressTownEmpty()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_ADDRESS_TOWN_EMPTY,e.getMessage());
		}
	}
	public StringReader inputStreetNumber() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,Perez,C,23-25666777-9\n"+
					"A,Alem,0,CABA,1001,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	@Test
	public void addressStreetNumberLowValue() throws IOException {
		try {
			new CustomerImporter(system, inputStreetNumber()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_STREET_NUMBER_LOW,e.getMessage());
		}
	}
	public StringReader inputZipCode() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,Perez,C,23-25666777-9\n"+
					"A,Alem,1122,CABA,999,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void addressZipCodeLowValue() throws IOException {
		try {
			new CustomerImporter(system, inputZipCode()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_ZIP_CODE_LOW,e.getMessage());
		}
	}
	
	public StringReader inputCustomerLowCantColumns() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,Perez,\n"+
					"A,Alem,1122,CABA,1001,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void customerLowCantColumns() throws IOException {
		try {
			new CustomerImporter(system, inputCustomerLowCantColumns()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
		}
	}
	
	public StringReader inputCustomerHighCantColums() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444,extraa\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,Perez,C,23-25666777-9\n"+
					"A,Alem,1122,CABA,1001,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	@Test
	public void customerHighCantColumns() throws IOException {
		try {
			new CustomerImporter(system, inputCustomerHighCantColums()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
		}
	}
	
	public StringReader customerLowDigitsIdentificationNumberWhenTypeIsD() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,2233344\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,Perez,C,23-25666777-9\n"+
					"A,Alem,1122,CABA,1001,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void customertestLowDigitsIdentificationNumberWhenTypeIsD() throws IOException {
		try {
			new CustomerImporter(system, customerLowDigitsIdentificationNumberWhenTypeIsD() ).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_NUMBER,e.getMessage());
		}
	}
	public StringReader customerFormatIdentificationNumberWhenTypeIsC() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,Perez,C,23-25666777-97\n"+
					"A,Alem,1122,CABA,1001,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	
	@Test
	public void customertestFormatIdentificationNumberWhenTypeIsC() throws IOException {
		try {
			new CustomerImporter(system, customerFormatIdentificationNumberWhenTypeIsC()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_NUMBER,e.getMessage());
		}
	}
	
	public StringReader inputAddressLowCantColumns() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,\n"+
					"C,Juan,Perez,C,23-25666777-9\n"+
					"A,Alem,1122,CABA,1001,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void addressLowCantColumns() throws IOException {
		try {
			new CustomerImporter(system, inputAddressLowCantColumns()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
		}
	}
	public StringReader inputAddressHighCantColums() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
		"A,San Martin,3322,Olivos,1636,BsAs\n"+
		"A,Maipu,888,Florida,1122,Buenos Aires\n"+
		"C,Juan,Perez,C,23-25666777-9\n"+
		"A,Alem,1122,CABA,1001,CABA,extraa\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	@Test
	public void addressHighCantColumns() throws IOException {
		try {
			new CustomerImporter(system, inputAddressHighCantColums()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
		}
	}
	
	public StringReader inputAddressFormatStreetNumber() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,Perez,C,23-25666777-9\n"+
					"A,Alem,streetNumber,CABA,1001,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void addressFormatStreetNumber() throws IOException {
		try {
			new CustomerImporter(system,  inputAddressFormatStreetNumber()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_STREET_NUMBER,e.getMessage());
		}
	}
	
	public StringReader inputAddressFormatZipCode() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,Perez,C,23-25666777-9\n"+
					"A,Alem,1122,CABA,zipCode,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void addressFormatZipCode() throws IOException {
		try {
			new CustomerImporter(system, inputAddressFormatZipCode()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_ZIP_CODE,e.getMessage());
		}
	}
	
	public StringReader inputAddressFormatProvinceEmpty() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n"+
					"C,Juan,Perez,C,23-25666777-9\n"+
					"A,Alem,1122,CABA,1001,\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void addressFormatProvinceEmpty() throws IOException {
		try {
			new CustomerImporter(system, inputAddressFormatProvinceEmpty()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_ADDRESS_PROVINCE_EMPTY,e.getMessage());
		}
	}
	


	public StringReader validTipDocReader() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n");
		writer.write("A,San Martin,3322,Olivos,1636,BsAs\n");
		writer.write("A,Maipu,888,Florida,1122,Buenos Aires\n");
		writer.write("C,Juan,Perez,D,22333444\n");
		writer.write("A,Alem,1122,CABA,1001,CABA\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	public StringReader newSupplierWithNewCustomerReader() {
		StringWriter writer = new StringWriter();
		writer.write("S,Supplier1,D,12345678\n");
		writer.write("NC,Pepe,Pedro,D,12345675\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}		
		
		
	
	public StringReader newSupplierWithExistingCustomerReader() {
		StringWriter writer = new StringWriter();
		writer.write("S,Supplier1,D,12345678\n");
		writer.write("EC,D,5456774\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void testImportValidSupplierWithNewCustomerCorrectly() throws IOException {
		new SupplierImporter(system, newSupplierWithNewCustomerReader()).importSuppliers();
		
		assertEquals(1,system.numberOfSuppliers());
		assertSupplier1NewCustomerWasImportedCorrectly();
	}

	private void assertSupplier1NewCustomerWasImportedCorrectly() {
		Supplier supplier;
		Address address;
		supplier = (Supplier) system.supplierIdentifiedAs("D","12345678");
		assertSupplier1(supplier);

		assertEquals(1, supplier.numberOfCustomers());
		Customer customerS = (Customer) supplier.customerIdentifiedAs("D", "12345675");
		assertEquals("Pepe",customerS.getFirstName());
		assertEquals("Pedro",customerS.getLastName());
		assertEquals("D",customerS.getIdentificationType());
		assertEquals("12345675",customerS.getIdentificationNumber());
		
		assertEquals(1, system.numberOfCustomers());
		Customer customer = (Customer) system.customerIdentifiedAs("D", "12345675");
		assertEquals("Pepe",customer.getFirstName());
		assertEquals("Pedro",customer.getLastName());
		assertEquals("D",customer.getIdentificationType());
		assertEquals("12345675",customer.getIdentificationNumber());
		
		assertEquals(1, supplier.numberOfAddresses());
		address = supplier.addressAt("Irigoyen");
		assertEquals(3322,address.getStreetNumber());
		assertEquals("Olivos", address.getTown());
		assertEquals(1636, address.getZipCode());
		assertEquals("BsAs", address.getProvince());
		
	}

	private void assertSupplier1(Supplier supplier) {
		assertEquals("Supplier1",supplier.getName());
		assertEquals("D",supplier.getIdentificationType());
		assertEquals("12345678",supplier.getIdentificationNumber());
	}
	
	private void assertSupplier1NotExistingCustomerWasImportedCorrectly() {
		Supplier supplier;
		supplier = (Supplier) system.supplierIdentifiedAs("D","12345678");
		assertSupplier1(supplier);

		assertEquals(0, supplier.numberOfCustomers());
		
		assertEquals(0, system.numberOfCustomers());
		
		assertEquals(0, supplier.numberOfAddresses());
	
	}
	
	@Test
	public void testCanNotImportSupplierWithoutExistingCustomer() throws IOException {
		SupplierImporter supplierI = new SupplierImporter(system, newSupplierWithExistingCustomerReader());
		
		try {
			supplierI.importSuppliers();
			fail();
		}catch(Error e){
			assertEquals(Importer.CUSTOMER_NOT_FOUND, e.getMessage());
			assertEquals(1,system.numberOfSuppliers());
			assertSupplier1NotExistingCustomerWasImportedCorrectly();
		}
	}
		

	public void testCanNotImportCustomerWithRepeatedDoc() throws IOException {
		try {
			new CustomerImporter(system, validTipDocReader()).importCustomers();
			fail();
		}catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_DOC_DUPLICATE,e.getMessage());
		}
	}

	public StringReader validTipDocSupplReader() {
		StringWriter writer = new StringWriter();
		writer.write("S,Supplier1,D,12345678\n");
		writer.write("NC,Pepe,Pedro,D,12345666\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs \n");
		writer.write("S,Sup1,D,12345678\n");
		writer.write("NC,Pepe,Pedro,D,17645666\n");
		writer.write("A,Alberti,1258,Olivos,1636,BsAs \n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void testCanNotImportSupplierWithRepeatedDoc() throws IOException {
		try {
			new SupplierImporter(system, validTipDocSupplReader()).importSuppliers();
			fail();
		}catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_DOC_DUPLICATE,e.getMessage());
		}
	}
	public StringReader newSupplierReader() {
		StringWriter writer = new StringWriter();
		writer.write("S,Supplier1,D,12345666\n");
		writer.write("NC,Pepe,Pedro,D,12345666\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		writer.write("S,Supplier2,D,123547698\n");
		writer.write("NC,Carlos,Pedro,D,14785214\n");
		writer.write("A,Irigoyen,1264,Olivos,1666,BsAs\n");
		writer.write("EC,D,12345666\n");
		writer.write("A,Misiones,1345,Olivos,1636,BsAs\n");
		writer.write("S,Supplier3,D,12345432\n");
		writer.write("EC,D,14785214\n");
		writer.write("A,Irigoyen,1264,Olivos,1636,BsAs\n");
		writer.write("A,Misiones,1345,Olivos,1638,BsAs\n");

		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	public void assertSupplier1WasImportedCorrectly() {
		Address address;
		Supplier supp = system.supplierIdentifiedAs("D", "12345666");
		assertEquals("Supplier1",supp.getName());
		assertEquals("D",supp.getIdentificationType());
		assertEquals("12345666",supp.getIdentificationNumber());

		assertEquals(1,supp.numberOfAddresses());
		address = supp.addressAt("Irigoyen");
		assertEquals(3322,address.getStreetNumber());
		assertEquals("Olivos", address.getTown());
		assertEquals(1636, address.getZipCode());
		assertEquals("BsAs", address.getProvince());
		
		assertEquals(1,supp.getCustomers().size());
		Customer c =(Customer) supp.getCustomers().toArray()[0];
		
		assertEquals("Pepe",c.getFirstName());
		assertEquals("Pedro",c.getLastName());
		assertEquals("D",c.getIdentificationType());
		assertEquals("12345666",c.getIdentificationNumber());
		
	}
	
	public void assertSupplier2WasImportedCorrectly() {
		Address address;
		Supplier supp = system.supplierIdentifiedAs("D", "123547698");
		assertEquals("Supplier2",supp.getName());
		assertEquals("D",supp.getIdentificationType());
		assertEquals("123547698",supp.getIdentificationNumber());

		assertEquals(2,supp.numberOfAddresses());
		address = supp.addressAt("Irigoyen");
		assertEquals(1264,address.getStreetNumber());
		assertEquals("Olivos", address.getTown());
		assertEquals(1666, address.getZipCode());
		assertEquals("BsAs", address.getProvince());
		
		assertEquals(2,supp.getCustomers().size());
		for(Customer cus :supp.getCustomers() ){
			if(cus.getFirstName().equals("Carlos")){
				assertEquals("Pedro",cus.getLastName());
				assertEquals("D",cus.getIdentificationType());
				assertEquals("14785214",cus.getIdentificationNumber());
			}else{
				assertEquals("Pepe",cus.getFirstName());
				assertEquals("Pedro",cus.getLastName());
				assertEquals("D",cus.getIdentificationType());
				assertEquals("12345666",cus.getIdentificationNumber());	
			}
		}
	}
	
	private void assertSupplier3WasImportedCorrectly() {
		Supplier supp = system.supplierIdentifiedAs("D", "12345432");
		assertEquals("Supplier3",supp.getName());
		assertEquals("D",supp.getIdentificationType());
		assertEquals("12345432",supp.getIdentificationNumber());

		assertEquals(2,supp.numberOfAddresses());
		
		for(Address adr : supp.getAddresses() ){
			if(adr.getStreetName().equals("Irigoyen")){
				assertEquals(1264,adr.getStreetNumber());
				assertEquals("Olivos", adr.getTown());
				assertEquals(1636, adr.getZipCode());
				assertEquals("BsAs", adr.getProvince());
			}else{
				assertEquals("Misiones",adr.getStreetName());
				assertEquals("Olivos",adr.getTown());
				assertEquals(1638,adr.getZipCode());
				assertEquals("BsAs",adr.getProvince());	
			}
		}
		
		assertEquals(1,supp.getCustomers().size());
		Customer c =(Customer) supp.getCustomers().toArray()[0];
		
		assertEquals("Carlos",c.getFirstName());
		assertEquals("Pedro",c.getLastName());
		assertEquals("D",c.getIdentificationType());
		assertEquals("14785214",c.getIdentificationNumber());
		
	}
	
	
	
	@Test
	public void testImportsValidDataSupplierCorrec() throws IOException {
		new SupplierImporter(system, newSupplierReader()).importSuppliers();
		assertSupplier1WasImportedCorrectly();
		assertSupplier2WasImportedCorrectly();
		assertSupplier3WasImportedCorrectly();
	}
	
	public StringReader newSupplierCientRepetidoReader() {
		StringWriter writer = new StringWriter();
		writer.write("S,Supplier1,D,12345678\n");
		writer.write("NC,Pepe,Pedro,D,12345666\n");
		writer.write("EC,D,12345666\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	@Test
	public void testNosePuedeAgregarClienteRepetidoParaSupplier() throws IOException {
		try {
			new SupplierImporter(system, newSupplierCientRepetidoReader()).importSuppliers();
		} catch (RuntimeException e) {
			assertEquals(Supplier.NO_SE_PUEDE_AGREGAR_UN_CLIENTE_REPETIDO_PARA_ESTE_SUPPLIER,e.getMessage());
		}
	}
	
	@Test
	public void testWithoutDataThereAreNotErrorSupplier() throws IOException {
		new SupplierImporter(system, validDataReaderEmpty()).importSuppliers();
		assertEquals(0, system.numberOfSuppliers());			
	}


	


	


}
