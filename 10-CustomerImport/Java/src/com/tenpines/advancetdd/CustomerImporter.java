package com.tenpines.advancetdd;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.hibernate.Session;

public class CustomerImporter {

	public static final String INVALID_FORMAT_LINE = "This line hasn't the correct format";
	
	public void importCustomers(Session session, Reader fileReader) throws IOException{
		
		LineNumberReader lineReader = new LineNumberReader(fileReader);
		
		Customer newCustomer = null;
		String line = lineReader.readLine(); 
		while (hasNext(line)) {
			String[] records = line.split(",");
			if (isACustomer(records)){
				newCustomer = addNewCustomer(session, records);
			}else if (isAnAddress(records)) {
				addAddress(newCustomer, records);
			}else{
				throw new RuntimeException(INVALID_FORMAT_LINE);
			}
			
			line = lineReader.readLine();
		}
			
		fileReader.close();
	}

	private boolean hasNext(String line) {
		return line!=null;
	}

	private void addAddress(Customer newCustomer, String[] records) {
		Address newAddress = new Address();

		newCustomer.addAddress(newAddress);
		newAddress.setStreetName(records[1]);
		newAddress.setStreetNumber(Integer.parseInt(records[2]));
		newAddress.setTown(records[3]);
		newAddress.setZipCode(Integer.parseInt(records[4]));
		newAddress.setProvince(records[5]);
	}

	private Customer addNewCustomer(Session session, String[] records) {
		Customer newCustomer;
		newCustomer = new Customer();
		newCustomer.setFirstName(records[1]);
		newCustomer.setLastName(records[2]);
		newCustomer.setIdentificationType(records[3]);
		newCustomer.setIdentificationNumber(records[4]);
		session.persist(newCustomer);
		return newCustomer;
	}

	private boolean isAnAddress(String[] records) {
		return records[0].equals("A");
	}

	private boolean isACustomer(String[] records) {
		return records[0].equals("C");
	}

}
