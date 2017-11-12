package com.tenpines.advancetdd;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CustomerImporterTest {

	private Session session;

	public StringReader validDateReader() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n");
		writer.write("A,San Martin,3322,Olivos,1636,BsAs\n");
		writer.write("A,Maipu,888,Florida,1122,Buenos Aires\n");
		writer.write("C,Juan,Perez,C,23-25666777-9\n");
		writer.write("A,Alem,1122,CABA,1001,CABA\n");
		
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	public StringReader validDateReaderIncorrectLetter() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n");
		writer.write("A,San Martin,3322,Olivos,1636,BsAs\n");
		writer.write("X,Maipu,888,Florida,1122,Buenos Aires\n");
		writer.write("C,Juan,Perez,C,23-25666777-9\n");
		writer.write("A,Alem,1122,CABA,1001,CABA\n");
		
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	public StringReader validDateReaderIncorrectLetterA() {
		StringWriter writer = new StringWriter();
		writer.write("C,Pepe,Sanchez,D,22333444\n");
		writer.write("A,San Martin,3322,Olivos,1636,BsAs\n");
		writer.write("AA,Maipu,888,Florida,1122,Buenos Aires\n");
		writer.write("C,Juan,Perez,C,23-25666777-9\n");
		writer.write("A,Alem,1122,CABA,1001,CABA\n");
		
		StringReader fileReader = new StringReader(writer.getBuffer().toString());
		return fileReader;
	}
	
	public StringReader validDateReaderIncorrectLetterC() {
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
		customer = customerIdentifiedAs("C", "23-25666777-9");
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

	public Customer customerIdentifiedAs(String idType, String idNumber) {
		List<Customer> customers;
		Customer customer;
		customers = session.createCriteria(Customer.class).
				add(Restrictions.eq("identificationType", idType)).
				add(Restrictions.eq("identificationNumber",idNumber)).list();
		assertEquals(1,customers.size());
		customer = customers.get(0);
		return customer;
	}

	public void assertPepeSanchezWasImportedCorrectly() {
		
		Customer customer = customerIdentifiedAs("D", "22333444");
		assertEquals("Pepe",customer.getFirstName());
		assertEquals("Sanchez",customer.getLastName());
		assertEquals("D",customer.getIdentificationType());
		assertEquals("22333444",customer.getIdentificationNumber());
	
		assertEquals(2,customer.numberOfAddresses());
		Address address = customer.addressAt("San Martin");
		assertEquals(3322,address.getStreetNumber());
		assertEquals("Olivos", address.getTown());
		assertEquals(1636, address.getZipCode());
		assertEquals("BsAs", address.getProvince());
		
		address = customer.addressAt("Maipu");
		assertEquals(888,address.getStreetNumber());
		assertEquals("Florida", address.getTown());
		assertEquals(1122, address.getZipCode());
		assertEquals("Buenos Aires", address.getProvince());
	}

	public void assertCustomerCount() {
		List<Customer> customers = session.createCriteria(Customer.class).list();
		assertEquals(2,customers.size());
	}

	@After
	public void closeSession() {
		session.getTransaction().commit();
		session.close();
	}

	@Before
	public void openSession() {
		Configuration configuration = new Configuration();
	    configuration.configure();
	
	    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
		session.beginTransaction();
		
	}
	
	@Test
	public void importsValidDataCorrectly() throws IOException {
		new CustomerImporter().importCustomers(session, validDateReader());

		assertCustomerCount();
		assertPepeSanchezWasImportedCorrectly();
		assertJuanPerezWasImportedCorrectly();		
	}
	
	@Test
	public void eachLineShouldBeginWithCorrectLetter() throws IOException {
		try {
			new CustomerImporter().importCustomers(session, validDateReaderIncorrectLetter());
			assertCustomerCount();
			assertPepeSanchezWasImportedCorrectly();
			assertJuanPerezWasImportedCorrectly();	
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_BEGIN_FORMAT_LINE,e.getMessage());
		}
	}
	
	@Test
	public void eachAddressLineShouldBeginWithUniqueA() throws IOException {
		try {
			new CustomerImporter().importCustomers(session, validDateReaderIncorrectLetterA());
			assertCustomerCount();
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
			new CustomerImporter().importCustomers(session, validDateReaderIncorrectLetterC());
			assertCustomerCount();
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
			new CustomerImporter().importCustomers(session, reader);
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_IDENTIFICATION_TYPE,e.getMessage());
		}
	}
	
	@Test
	public void customerNameCanNotBeEmpty() throws IOException {
		FileReader reader = new FileReader("resources/inputCustomerName.txt");
		
		try {
			new CustomerImporter().importCustomers(session, reader);
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_CUSTOMER_NAME_EMPTY,e.getMessage());
		}
	}
	
	@Test
	public void customerLastNameCanNotBeEmpty() throws IOException {
		FileReader reader = new FileReader("resources/inputCustomerLastName.txt");
		
		try {
			new CustomerImporter().importCustomers(session, reader);
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_CUSTOMER_LAST_NAME_EMPTY,e.getMessage());
		}
	}

	@Test
	public void addressStreetNameEmpty() throws IOException {
		FileReader reader = new FileReader("resources/inputAddressStreetEmpty.txt");
		
		try {
			new CustomerImporter().importCustomers(session, reader);
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_ADDRESS_STREET_NAME_EMPTY,e.getMessage());
		}
	}
	
	
	@Test
	public void addressTownEmpty() throws IOException {
		FileReader reader = new FileReader("resources/inputAddressTownEmpty.txt");
		
		try {
			new CustomerImporter().importCustomers(session, reader);
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_ADDRESS_TOWN_EMPTY,e.getMessage());
		}
	}
	
	@Test
	public void addressStreetNumb() throws IOException {
		FileReader reader = new FileReader("resources/inputStreetNumber.txt");
		
		try {
			new CustomerImporter().importCustomers(session, reader);
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_STREET_NUMBER,e.getMessage());
		}
	}
	
	@Test
	public void addressZipCode() throws IOException {
		FileReader reader = new FileReader("resources/inputZipCode.txt");
		
		try {
			new CustomerImporter().importCustomers(session, reader);
			fail();
		} catch (RuntimeException e) {
			assertEquals(CustomerImporter.INVALID_FORMAT_ZIP_CODE,e.getMessage());
		}
	}
	
	
	
	
	
	

}
