package com.tenpines.advancetdd;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerImporterTest extends TestCase {

	private static Session session;
	private static FileReader reader;

	public static void importCustomers() throws IOException{
		
		reader = new FileReader("resources/input.txt");   // ReadStream on: hay que cambiar el tipo y pego todo lo que esta en el archivo.
		LineNumberReader lineReader = new LineNumberReader(reader);
		
		Configuration configuration = new Configuration();
	    configuration.configure();
	
	    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
		session.beginTransaction();
		
		Customer newCustomer = null;
		String line = lineReader.readLine(); 
		while (line!=null) {
			if (line.startsWith("C")){
				String[] customerData = line.split(",");
				newCustomer = new Customer();
				newCustomer.setFirstName(customerData[1]);
				newCustomer.setLastName(customerData[2]);
				newCustomer.setIdentificationType(customerData[3]);
				newCustomer.setIdentificationNumber(customerData[4]);
				session.persist(newCustomer);
			}
			else if (line.startsWith("A")) {
				String[] addressData = line.split(",");
				Address newAddress = new Address();
	
				newCustomer.addAddress(newAddress);
				newAddress.setStreetName(addressData[1]);
				newAddress.setStreetNumber(Integer.parseInt(addressData[2]));
				newAddress.setTown(addressData[3]);
				newAddress.setZipCode(Integer.parseInt(addressData[4]));
				newAddress.setProvince(addressData[5]);
			}
			
			line = lineReader.readLine();
		}
			
		session.getTransaction().commit();
		session.close();
		
		reader.close();
	}
	
	@Test
	public void testImportCustomers(){
		try {
			importCustomers();
		//	session.geE
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
