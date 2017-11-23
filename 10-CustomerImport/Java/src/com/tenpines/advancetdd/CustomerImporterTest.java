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

	public StringReader validCustomerReader() {
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
	public void test01importsValidCustomersCorrectly() throws IOException {
		new CustomerImporter(system, validCustomerReader()).importCustomers();
		
		assertEquals(2,system.numberOfCustomers());
		assertPepeSanchezWasImportedCorrectly();
		assertJuanPerezWasImportedCorrectly();		
	}
	
	@Test
	public void test02WithoutCustomerThereAreNotError() throws IOException {
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
	public void test05EachLineShouldBeginWithCorrectLetter() throws IOException {
		CustomerImporter customerImporter = new CustomerImporter(system, validDataReaderIncorrectLetter());
		try {
			customerImporter.importCustomers();	
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_BEGIN_FORMAT_LINE,e.getMessage());
			assertCustomerPepeSanchezAddressSanMartin();
		}
	}

	private void assertCustomerPepeSanchezAddressSanMartin() {
		assertEquals(1, system.numberOfCustomers());
		assertCustomerPepeSanchez();
		Customer customer = getCustomerPepeSanchez();	
		assertEquals(1,customer.numberOfAddresses());
		assertPepeSanchezAddressSanMartin(customer);
	}
	
	@Test
	public void test06EachAddressLineShouldBeginWithUniqueA() throws IOException {
		try {
			new CustomerImporter(system, validDataReaderIncorrectLetterA()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_BEGIN_FORMAT_LINE,e.getMessage());
			assertCustomerPepeSanchezAddressSanMartin();
		}
	}
	
	@Test
	public void test07EachCustomerLineShouldBeginWithUniqueC() throws IOException {
		try {
			new CustomerImporter(system, validDataReaderIncorrectLetterC()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_BEGIN_FORMAT_LINE,e.getMessage());
			assertEquals(0, system.numberOfCustomers());
		}
	}
	
	public StringReader inputIdentificationType() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,X,22333444\n" +
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n");
							
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test08IdentificationTypeCanOnlyBeDorC() throws IOException {		
		try {
			new CustomerImporter(system, inputIdentificationType()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_TYPE,e.getMessage());
			assertEquals(0, system.numberOfCustomers());
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
	public void test09CustomerNameCanNotBeEmpty() throws IOException {		
		try {
			new CustomerImporter(system, inputCustomerName()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_NAME_EMPTY,e.getMessage());
			assertEquals(0, system.numberOfCustomers());
		}
	}
	
	public StringReader inputCustomerLastName() {
		StringWriter writer = new StringWriter();
		writer.write("C,Juan,,C,23-25666777-9\n"+
					"A,Alem,1122,CABA,1001,CABA\n");
							
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}

	@Test
	public void test10CustomerLastNameCanNotBeEmpty() throws IOException {
		try {
			new CustomerImporter(system, inputCustomerLastName()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_CUSTOMER_LAST_NAME_EMPTY,e.getMessage());
			assertEquals(0, system.numberOfCustomers());
		}
	}
	
	public StringReader inputCustomerIdentificationNumber() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n");
							
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test11CustomerIdentificationNumberCanNotBeEmpty() throws IOException {
		try {
			new CustomerImporter(system, inputCustomerIdentificationNumber()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_NUMBER_EMPTY,e.getMessage());
			assertEquals(0, system.numberOfCustomers());
		}
	}
	
	public StringReader inputAddressStreetEmpty() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,,888,Florida,1122,Buenos Aires\n");
							
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}

	@Test
	public void test12AddressStreetNameEmpty() throws IOException {		
		try {
			new CustomerImporter(system, inputAddressStreetEmpty()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_ADDRESS_STREET_NAME_EMPTY,e.getMessage());
			assertCustomerPepeSanchezAddressSanMartin();
		}
	}
	public StringReader inputAddressTownEmpty() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,,1122,Buenos Aires\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test13AddressTownEmpty() throws IOException {
		try {
			new CustomerImporter(system, inputAddressTownEmpty()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_ADDRESS_TOWN_EMPTY,e.getMessage());
			assertCustomerPepeSanchezAddressSanMartin();
		}
	}
	public StringReader inputStreetNumber() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,0,Florida,1122,Buenos Aires\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	@Test
	public void test14AddressStreetNumberLowValue() throws IOException {
		try {
			new CustomerImporter(system, inputStreetNumber()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_STREET_NUMBER_LOW,e.getMessage());
			assertCustomerPepeSanchezAddressSanMartin();
		}
	}
	public StringReader inputZipCode() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,999,Buenos Aires\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test15AddressZipCodeLowValue() throws IOException {
		try {
			new CustomerImporter(system, inputZipCode()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_ZIP_CODE_LOW,e.getMessage());
			assertCustomerPepeSanchezAddressSanMartin();
		}
	}
	
	public StringReader inputCustomerLowCantColumns() {
		StringWriter writer = new StringWriter();
		writer.write("C,Juan,Perez,\n"+
					"A,Alem,1122,CABA,1001,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test16CustomerLowCantColumns() throws IOException {
		try {
			new CustomerImporter(system, inputCustomerLowCantColumns()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
			assertEquals(0, system.numberOfCustomers());
		}
	}
	
	public StringReader inputCustomerHighCantColums() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444,extraa\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	@Test
	public void test17CustomerHighCantColumns() throws IOException {
		try {
			new CustomerImporter(system, inputCustomerHighCantColums()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
			assertEquals(0, system.numberOfCustomers());
		}
	}
	
	public StringReader customerLowDigitsIdentificationNumberWhenTypeIsD() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,2233344\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Maipu,888,Florida,1122,Buenos Aires\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test18CustomertestLowDigitsIdentificationNumberWhenTypeIsD() throws IOException {
		try {
			new CustomerImporter(system, customerLowDigitsIdentificationNumberWhenTypeIsD() ).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_NUMBER,e.getMessage());
			assertEquals(0, system.numberOfCustomers());
		}
	}
	public StringReader customerFormatIdentificationNumberWhenTypeIsC() {
		StringWriter writer = new StringWriter();
		writer.write("C,Juan,Perez,C,23-25666777-97\n"+
					"A,Alem,1122,CABA,1001,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	
	@Test
	public void test19CustomertestFormatIdentificationNumberWhenTypeIsC() throws IOException {
		try {
			new CustomerImporter(system, customerFormatIdentificationNumberWhenTypeIsC()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_NUMBER,e.getMessage());
			assertEquals(0, system.numberOfCustomers());
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
	public void test20AddressLowCantColumns() throws IOException {
		try {
			new CustomerImporter(system, inputAddressLowCantColumns()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
			assertCustomerPepeSanchezAddressSanMartin();
		}
	}
	public StringReader inputAddressHighCantColums() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
		"A,San Martin,3322,Olivos,1636,BsAs\n"+
		"A,Alem,1122,CABA,1001,CABA,extraa\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	@Test
	public void test21AddressHighCantColumns() throws IOException {
		try {
			new CustomerImporter(system, inputAddressHighCantColums()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
			assertCustomerPepeSanchezAddressSanMartin();	
		}
	}
	
	public StringReader inputAddressFormatStreetNumber() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Alem,streetNumber,CABA,1001,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test22AddressFormatStreetNumber() throws IOException {
		try {
			new CustomerImporter(system,  inputAddressFormatStreetNumber()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_STREET_NUMBER,e.getMessage());
			assertCustomerPepeSanchezAddressSanMartin();
		}
	}
	
	public StringReader inputAddressFormatZipCode() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Alem,1122,CABA,zipCode,CABA\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test23AddressFormatZipCode() throws IOException {
		try {
			new CustomerImporter(system, inputAddressFormatZipCode()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_ZIP_CODE,e.getMessage());
			assertCustomerPepeSanchezAddressSanMartin();
		}
	}
	
	public StringReader inputAddressFormatProvinceEmpty() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n"+
					"A,San Martin,3322,Olivos,1636,BsAs\n"+
					"A,Alem,1122,CABA,1001,\n");				
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test24AddressFormatProvinceEmpty() throws IOException {
		try {
			new CustomerImporter(system, inputAddressFormatProvinceEmpty()).importCustomers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_ADDRESS_PROVINCE_EMPTY,e.getMessage());
			assertCustomerPepeSanchezAddressSanMartin();
		}
	}
	
	
	public StringReader newSupplierWithNewCustomerReader() {
		StringWriter writer = new StringWriter();
		writer.write("S,Supplier1,D,12345678\n");
		writer.write("NC,Pepe,Pedro,D,12345675\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}		
	
	@Test
	public void test25ImportValidSupplierWithNewCustomerCorrectly() throws IOException {
		new SupplierImporter(system, newSupplierWithNewCustomerReader()).importSuppliers();
		assertEquals(1,system.numberOfSuppliers());
		assertSupplier1NewCustomerWasImportedCorrectly();
	}
	
	private void assertSupplier1(Supplier supplier) {
		assertEquals("Supplier1",supplier.getName());
		assertEquals("D",supplier.getIdentificationType());
		assertEquals("12345678",supplier.getIdentificationNumber());
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
	
	public StringReader newSupplierWithNoExistingCustomerReader() {
		StringWriter writer = new StringWriter();
		writer.write("S,Supplier1,D,12345678\n");
		writer.write("EC,D,5456774\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	private void assertSupplier1NotExistingCustomerWasImportedCorrectly() {
 		Supplier supplier = (Supplier) system.supplierIdentifiedAs("D","12345678");
  		assertSupplier1(supplier);
  
 		assertEquals(0, supplier.numberOfCustomers());
  		
 		assertEquals(0, system.numberOfCustomers());
  		
 		assertEquals(0, supplier.numberOfAddresses());
  	
  	}
	
	@Test
	public void test26CanNotImportExistingCustomerInSupplierIfCustomerNotExist() throws IOException {
		SupplierImporter supplierI = new SupplierImporter(system, newSupplierWithNoExistingCustomerReader());
		try {
			supplierI.importSuppliers();
			fail();
		}catch(Error e){
			assertEquals(Importer.CUSTOMER_NOT_FOUND, e.getMessage());
			assertEquals(1,system.numberOfSuppliers());
  			assertSupplier1NotExistingCustomerWasImportedCorrectly();
		}
	}

	public void assertSupplier1WasImportedCorrectly() {
		Address address;
		Supplier supp = system.supplierIdentifiedAs("D", "12345666");
		assertEquals("Pepe",supp.getName());
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
	
	public StringReader newSupplierReader() {
		StringWriter writer = new StringWriter();
		writer.write("S,Pepe,D,12345666\n");
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
	
	@Test
	public void test27ImportExistingClientsOfSuppliersCorrec() throws IOException {
		new SupplierImporter(system, newSupplierReader()).importSuppliers();
		assertSupplier1WasImportedCorrectly();
		assertSupplier2WasImportedCorrectly();
		assertSupplier3WasImportedCorrectly();
	}
	
	
	public StringReader supplierReader() {
		StringWriter writer = new StringWriter();
		writer.write("S,Pepe,D,12345666\n");
		writer.write("EC,D,12345666\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		writer.write("S,Supplier2,D,123547698\n");
		writer.write("EC,D,14785214\n");
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
	
	public StringReader customerReader() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Pedro,D,12345666\n");
		writer.write("A,San Martin,3322,Olivos,1636,BsAs\n");
		writer.write("A,Maipu,888,Florida,1122,Buenos Aires\n");
		writer.write("C,Carlos,Pedro,D,14785214\n");
		writer.write("A,Alem,1122,CABA,1001,CABA\n");

		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	@Test
	public void test28ImportExistingClientsOfSuppliersImportedFromCustomerImporter() throws IOException {
		new CustomerImporter(system, customerReader()).importCustomers();
		new SupplierImporter(system, supplierReader()).importSuppliers();
		assertSupplier1WasImportedCorrectly();
		assertSupplier2WasImportedCorrectly();
		assertSupplier3WasImportedCorrectly();
	}

	public StringReader newSupplierCientRepeatedReader() {
		StringWriter writer = new StringWriter();
		writer.write("S,Supplier1,D,12345678\n");
		writer.write("NC,Pepe,Pedro,D,12345666\n");
		writer.write("EC,D,12345666\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	public void assertSupplier1WithClientNotRepeated() {
		
		assertEquals(1, system.numberOfSuppliers());
		Supplier supp = system.supplierIdentifiedAs("D", "12345678");
		assertEquals("Supplier1",supp.getName());
		assertEquals("D",supp.getIdentificationType());
		assertEquals("12345678",supp.getIdentificationNumber());

		assertEquals(1, system.numberOfCustomers());
		assertEquals(1,supp.numberOfCustomers());
		Customer c = supp.customerIdentifiedAs("D", "12345666");
		assertEquals("Pepe",c.getFirstName());
		assertEquals("Pedro",c.getLastName());
		assertEquals("D",c.getIdentificationType());
		assertEquals("12345666",c.getIdentificationNumber());
		
	}
	
	@Test
	public void test30CanNotAddRepeatedClientForSupplier() throws IOException {
		SupplierImporter supI = new SupplierImporter(system, newSupplierCientRepeatedReader());
		try {
			supI.importSuppliers();
		} catch (RuntimeException e) {
			assertEquals(Supplier.CAN_NOT_ADD_REPEATED_CLIENT_FOR_SUPPLIER,e.getMessage());
			assertSupplier1WithClientNotRepeated();
		}
	}
	
	@Test
	public void test31WithoutDataThereAreNotErrorSupplier() throws IOException {
		new SupplierImporter(system, validDataReaderEmpty()).importSuppliers();
		assertEquals(0, system.numberOfSuppliers());			
	}
	
	
	public StringReader validDuplicateTipDocReader() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n");
		writer.write("A,San Martin,3322,Olivos,1636,BsAs\n");
		writer.write("C,Juan,Perez,D,22333444\n");
		writer.write("A,Alem,1122,CABA,1001,CABA\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test32CanNotImportCustomerWithRepeatedDoc() throws IOException {
		CustomerImporter custI = new CustomerImporter(system, validDuplicateTipDocReader());
		try {
			custI.importCustomers();
			fail();
		}catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_DOC_DUPLICATE,e.getMessage());
			assertEquals(1, system.numberOfCustomers());
			Customer c = system.customerIdentifiedAs("D", "22333444");
			assertPepeSanchezAddressSanMartin(c);
		}
	}
	
	
	public StringReader newSupplierIconcorrectIdentificationType() {
		StringWriter writer = new StringWriter();
		writer.write("S,Supplier1,X,12345666\n");
		writer.write("NC,Pepe,Pedro,D,12345666\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test33CanNotImportSupplierWithIncorrectIdType() throws IOException {
		try {
			new SupplierImporter(system, newSupplierIconcorrectIdentificationType()).importSuppliers();
			fail();
		}catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_TYPE,e.getMessage());
			assertEquals(0, system.numberOfSuppliers());
		}
	}
	
	public StringReader newSupplierNameEmpty() {
		StringWriter writer = new StringWriter();
		writer.write("S,,D,12345666\n");
		writer.write("NC,Pepe,Pedro,D,12345666\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test34CanNotImportSupplierWithNameEmpty() throws IOException {
		try {
			new SupplierImporter(system, newSupplierNameEmpty()).importSuppliers();
			fail();
		}catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_NAME_EMPTY,e.getMessage());
			assertEquals(0, system.numberOfSuppliers());
		}
	}
	
	public StringReader newSupplierIdNumberEmpty() {
		StringWriter writer = new StringWriter();
		writer.write("S,SupName,D,\n");
		writer.write("NC,Pepe,Pedro,D,12345666\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test35CanNotImportSupplierWithIdNumberEmpty() throws IOException {
		try {
			new SupplierImporter(system, newSupplierIdNumberEmpty()).importSuppliers();
			fail();
		}catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_NUMBER_EMPTY,e.getMessage());
			assertEquals(0, system.numberOfSuppliers());
		}
	}
	
	public StringReader newSupplierIdNumberInvalid() {
		StringWriter writer = new StringWriter();
		writer.write("S,SupName,D,otro\n");
		writer.write("NC,Pepe,Pedro,D,12345666\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test36CanNotImportSupplierWithIdNumberInvalid() throws IOException {
		try {
			new SupplierImporter(system, newSupplierIdNumberInvalid()).importSuppliers();
			fail();
		}catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_NUMBER,e.getMessage());
			assertEquals(0, system.numberOfSuppliers());
		}
	}
	
	public StringReader validTipDocSupplReader() {
		StringWriter writer = new StringWriter();
		writer.write("S,Supplier1,D,12345678\n");
		writer.write("NC,Pepe,Pedro,D,12345666\n");
		writer.write("S,Sup1,D,12345678\n");
		writer.write("NC,Pepe,Pedro,D,17645666\n");
		writer.write("A,Alberti,1258,Olivos,1636,BsAs \n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test37CanNotImportSupplierWithRepeatedDoc() throws IOException {
		try {
			new SupplierImporter(system, validTipDocSupplReader()).importSuppliers();
			fail();
		}catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_DOC_DUPLICATE,e.getMessage());
			assertSupplier1WithClientNotRepeated();
		}
	}
	
	
	public StringReader supplierCustomExist() {
		StringWriter writer = new StringWriter();
		writer.write("S,SupName,D,22333444\n");
		writer.write("EC,D,22333444\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	@Test
	public void test38CanNotImportSupplierWithExistentCustomer() throws IOException {
		try {
			new CustomerImporter(system, validCustomerReader()).importCustomers();
			new SupplierImporter(system, supplierCustomExist()).importSuppliers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.CUSTOMER_AND_SUPPLIER_SAME_IDENTIFICATION_DIFFERENT_NAME,e.getMessage());
			assertEquals(2,system.numberOfCustomers());
			assertPepeSanchezWasImportedCorrectly();
			assertJuanPerezWasImportedCorrectly();		
			assertEquals(0, system.numberOfSuppliers());			
		}
	}
	
	public StringReader customerSupplierExist() {
		StringWriter writer = new StringWriter();
		writer.write("S,Supplier1,D,12345678\n");
		writer.write("NC,Pepe,Pedro,D,12345678\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	@Test
	public void test39CanNotImportnewCustomerWithExistentSupplier() throws IOException {
		try {
			new SupplierImporter(system, customerSupplierExist()).importSuppliers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.CUSTOMER_AND_SUPPLIER_SAME_IDENTIFICATION_DIFFERENT_NAME,e.getMessage());
			assertEquals(1, system.numberOfSuppliers());
			Supplier supplier = (Supplier) system.supplierIdentifiedAs("D","12345678");
			assertSupplier1(supplier);
			assertEquals(0, system.numberOfCustomers());
		}
	}
	
	public StringReader supplierExistCustomerInvalidIdType() {
		StringWriter writer = new StringWriter();
		writer.write("S,Pepe,D,22333444\n");
		writer.write("EC,x,12345666\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
public void assertSupplierPepe() {
		
		assertEquals(1, system.numberOfSuppliers());
		Supplier supp = system.supplierIdentifiedAs("D", "22333444");
		assertEquals("Pepe",supp.getName());
		assertEquals("D",supp.getIdentificationType());
		assertEquals("22333444",supp.getIdentificationNumber());

		assertEquals(0, supp.numberOfCustomers());
		
	}
	
	@Test
	public void test40CanNotImportExistentCustomerInvalidIdType() throws IOException {
		try {
			new CustomerImporter(system, validCustomerReader()).importCustomers();
			new SupplierImporter(system, supplierExistCustomerInvalidIdType()).importSuppliers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_TYPE,e.getMessage());
			assertTotalCustomerAndOnlySupplierImport();
		}
	}
	
	public StringReader supplierExistCustomerEmptyIdNumber() {
		StringWriter writer = new StringWriter();
		writer.write("S,Pepe,D,22333444\n");
		writer.write("EC,D,\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	@Test
	public void test41CanNotImportExistentCustomerEmptyIdNumber() throws IOException {
		new CustomerImporter(system, validCustomerReader()).importCustomers();
		try {
			new SupplierImporter(system, supplierExistCustomerEmptyIdNumber()).importSuppliers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_NUMBER_EMPTY,e.getMessage());
			assertTotalCustomerAndOnlySupplierImport();
		}
	}
	
	public StringReader supplierExistCustomerInvalidIdNumber() {
		StringWriter writer = new StringWriter();
		writer.write("S,Pepe,D,22333444\n");
		writer.write("EC,D,idNumb\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	
	@Test
	public void test42CanNotImportExistentCustomerInvalidIdNumber() throws IOException {
		try {
			new CustomerImporter(system, validCustomerReader()).importCustomers();
			new SupplierImporter(system, supplierExistCustomerInvalidIdNumber()).importSuppliers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_IDENTIFICATION_NUMBER,e.getMessage());
			assertTotalCustomerAndOnlySupplierImport();
		}
	}
	
	public StringReader supplierExistCustomerInvalidCantColumns() {
		StringWriter writer = new StringWriter();
		writer.write("S,Pepe,D,22333444\n");
		writer.write("EC,D,12345666,extra\n");
		writer.write("A,Irigoyen,3322,Olivos,1636,BsAs\n");
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	@Test
	public void test43CanNotImportExistentCustomerInvalidCantColumns() throws IOException {
		try {
			new CustomerImporter(system, validCustomerReader()).importCustomers();
			new SupplierImporter(system, supplierExistCustomerInvalidCantColumns()).importSuppliers();
			fail();
		} catch (RuntimeException e) {
			assertEquals(Importer.INVALID_FORMAT_CANT_COLUMNS,e.getMessage());
			assertTotalCustomerAndOnlySupplierImport();
		}
	}

	private void assertTotalCustomerAndOnlySupplierImport() {
		assertEquals(2,system.numberOfCustomers());
		assertPepeSanchezWasImportedCorrectly();
		assertJuanPerezWasImportedCorrectly();	
		assertSupplierPepe();
	}

}
