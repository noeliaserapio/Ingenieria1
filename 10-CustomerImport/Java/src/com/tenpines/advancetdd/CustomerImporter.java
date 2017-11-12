package com.tenpines.advancetdd;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.hibernate.Session;

public class CustomerImporter {

	public static final String INVALID_BEGIN_FORMAT_LINE = "This line doesn't start correctly";
	public static final String INVALID_FORMAT_IDENTIFICATION_TYPE = "The identification type doesn�t have the correct format";
	public static final String INVALID_FORMAT_CUSTOMER_NAME_EMPTY = "Customer's name is empty";
	public static final String INVALID_FORMAT_CUSTOMER_LAST_NAME_EMPTY = "Customer's last name is empty";
	public static final String INVALID_FORMAT_CUSTOMER_IDENTIFICATION_NUMBER_EMPTY = "Customer's identification number is empty";
	public static final String INVALID_FORMAT_CUSTOMER_IDENTIFICATION_NUMBER = "The format of Customer's identification number is invalid";

	
	public static final String INVALID_FORMAT_CANT_COLUMNS = "The quantity of columns of thisline is invalid";
//	public static final String INVALID_FORMAT_CANT_COLUMNS_ADDRESS = "The quantity of columns of this address's line is invalid";

	
	
	public static final String INVALID_FORMAT_ADDRESS_STREET_NAME_EMPTY = "Address street name is empty";
	public static final String INVALID_FORMAT_ADDRESS_TOWN_EMPTY = "Address town is empty";
	public static final String INVALID_FORMAT_ADDRESS_PROVINCE_EMPTY = "Address province is empty";
	public static final String INVALID_FORMAT_STREET_NUMBER_LOW = "The street number is less than 1";
	public static final String INVALID_FORMAT_STREET_NUMBER = "The format of street is invalid";
	public static final String INVALID_FORMAT_ZIP_CODE_LOW = "The zip code is less than 1000";
	public static final String INVALID_FORMAT_ZIP_CODE = "The format of zip code is invalid";

	

	
	public void importCustomers(Session session, Reader fileReader) throws IOException{
		
		LineNumberReader lineReader = new LineNumberReader(fileReader);
		
		Customer newCustomer = null;
		String line = lineReader.readLine(); 
		while (hasNext(line)) {
			String[] records = line.split(",");
			if (isACustomer(records)){
				validateNewCustomer(records, line);
				newCustomer = addNewCustomer(session, records);
			}else if (isAnAddress(records)) {
				validateAddress(records,line);
				addAddress(newCustomer, records);
			}else{
				throw new RuntimeException(INVALID_BEGIN_FORMAT_LINE);
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
		newAddress.setStreetName(records[1]);
		newAddress.setStreetNumber(Integer.parseInt(records[2]));
		newAddress.setTown(records[3]);
		newAddress.setZipCode(Integer.parseInt(records[4]));	
		newAddress.setProvince(records[5]);	
		newCustomer.addAddress(newAddress);
	}

	private void validateAddress(String[] records, String line) {
		validateCantColumns(records, line, 6);
		if(records[1].length() == 0) throw new RuntimeException(INVALID_FORMAT_ADDRESS_STREET_NAME_EMPTY);
		if(!records[2].matches("[0-9]{1,9}")) throw new RuntimeException(INVALID_FORMAT_STREET_NUMBER);
		if(Integer.parseInt(records[2]) <1) throw new RuntimeException(INVALID_FORMAT_STREET_NUMBER_LOW);
		if(records[3].length() == 0) throw new RuntimeException(INVALID_FORMAT_ADDRESS_TOWN_EMPTY);
		if(!records[4].matches("[0-9]{1,9}")) throw new RuntimeException(INVALID_FORMAT_ZIP_CODE);
		if(Integer.parseInt(records[4]) <1000) throw new RuntimeException(INVALID_FORMAT_ZIP_CODE_LOW);
		if(line.endsWith(",")) throw new RuntimeException(INVALID_FORMAT_ADDRESS_PROVINCE_EMPTY);		

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

	private void validateNewCustomer(String[] records, String line) {
		validateCantColumns(records, line, 5);	
		if(records[1].length() == 0) throw new RuntimeException(INVALID_FORMAT_CUSTOMER_NAME_EMPTY);
		if(records[2].length() == 0) throw new RuntimeException(INVALID_FORMAT_CUSTOMER_LAST_NAME_EMPTY);
		if(!records[3].matches("D|C")) throw new RuntimeException(INVALID_FORMAT_IDENTIFICATION_TYPE);
		if(line.endsWith(",")) throw new RuntimeException(INVALID_FORMAT_CUSTOMER_IDENTIFICATION_NUMBER_EMPTY);		
		if(records[3].equals("D")){
			if(!records[4].matches("[0-9]{8,9}")) throw new RuntimeException(INVALID_FORMAT_CUSTOMER_IDENTIFICATION_NUMBER);
		}else{
			if(!records[4].matches("[0-9]{2}-[0-9]{8,9}-[0-9]{1}")) throw new RuntimeException(INVALID_FORMAT_CUSTOMER_IDENTIFICATION_NUMBER);
		}

	}

	private void validateCantColumns(String[] records, String line, int validCant) {
		int cantColumn = records.length;
		if(line.endsWith(",")) cantColumn++;
		if(!(cantColumn == validCant)) throw new RuntimeException(INVALID_FORMAT_CANT_COLUMNS);
	}

	private boolean isAnAddress(String[] records) {
		return records[0].equals("A");
	}

	private boolean isACustomer(String[] records) {
		return records[0].equals("C");
	}

}
