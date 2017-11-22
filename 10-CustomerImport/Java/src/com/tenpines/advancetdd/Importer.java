package com.tenpines.advancetdd;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

public abstract class Importer {

	protected LineNumberReader lineReader;
	private String line;
	protected String[] records;
	protected ErpSystem system;
	protected Reader readStream;
	protected Party lastParty;
	public static final String ADDRESS_WITHOUT_ASIGNATION = "Can not have address without asignation";
	public static final String INVALID_BEGIN_FORMAT_LINE = "This line doesn't start correctly";
	public static final String INVALID_FORMAT_IDENTIFICATION_TYPE = "The identification type doesn´t have the correct format";
	public static final String INVALID_FORMAT_NAME_EMPTY = "Customer's name is empty";
	public static final String INVALID_FORMAT_CUSTOMER_LAST_NAME_EMPTY = "Customer's last name is empty";
	public static final String INVALID_FORMAT_IDENTIFICATION_NUMBER_EMPTY = "Customer's identification number is empty";
	public static final String INVALID_FORMAT_IDENTIFICATION_NUMBER = "The format of Customer's identification number is invalid";
	public static final String INVALID_FORMAT_CANT_COLUMNS = "The quantity of columns of thisline is invalid";
	public static final String INVALID_FORMAT_ADDRESS_STREET_NAME_EMPTY = "Address street name is empty";
	public static final String INVALID_FORMAT_ADDRESS_TOWN_EMPTY = "Address town is empty";
	public static final String INVALID_FORMAT_ADDRESS_PROVINCE_EMPTY = "Address province is empty";
	public static final String INVALID_FORMAT_STREET_NUMBER_LOW = "The street number is less than 1";
	public static final String INVALID_FORMAT_STREET_NUMBER = "The format of street is invalid";
	public static final String INVALID_FORMAT_ZIP_CODE_LOW = "The zip code is less than 1000";
	public static final String INVALID_FORMAT_ZIP_CODE = "The format of zip code is invalid";
	public static final String INVALID_FORMAT_DOC_DUPLICATE = "The identification number is duplicate";
	public static final String CUSTOMER_NOT_FOUND = "The customer doesn't exit";
	

	protected void readLine() {
		records = line.split(",");
	}

	protected boolean hasNextLine() throws IOException {
		line = lineReader.readLine(); 
		return line!=null;
	}

	protected void parseAddress() {
		validateAddress();
		addAddress();
	}

	protected Customer addNewCustomer() {
		Customer newCustomer = new Customer(records[1],records[2],records[3],records[4]);
		system.persist(newCustomer);
		return newCustomer;
	}

	private void addAddress() {
		Address newAddress = setNewAddress();	
		lastParty.addAddress(newAddress);
	}



	protected Address setNewAddress() {
		Address newAddress = new Address();
		newAddress.setStreetName(records[1]);
		newAddress.setStreetNumber(Integer.parseInt(records[2]));
		newAddress.setTown(records[3]);
		newAddress.setZipCode(Integer.parseInt(records[4]));	
		newAddress.setProvince(records[5]);
		return newAddress;
	}

	private void validateAddress() {
		if(lastParty == null) throw new RuntimeException(ADDRESS_WITHOUT_ASIGNATION);
		validateCantColumns(6);
		if(records[1].length() == 0) throw new RuntimeException(INVALID_FORMAT_ADDRESS_STREET_NAME_EMPTY);
		if(!records[2].matches("[0-9]{1,9}")) throw new RuntimeException(INVALID_FORMAT_STREET_NUMBER);
		if(Integer.parseInt(records[2]) <1) throw new RuntimeException(INVALID_FORMAT_STREET_NUMBER_LOW);
		if(records[3].length() == 0) throw new RuntimeException(INVALID_FORMAT_ADDRESS_TOWN_EMPTY);
		if(!records[4].matches("[0-9]{1,9}")) throw new RuntimeException(INVALID_FORMAT_ZIP_CODE);
		if(Integer.parseInt(records[4]) <1000) throw new RuntimeException(INVALID_FORMAT_ZIP_CODE_LOW);
		if(line.endsWith(",")) throw new RuntimeException(INVALID_FORMAT_ADDRESS_PROVINCE_EMPTY);		
	
	}

	protected void validateNewCustomer() {
		validateCantColumns(5);	
		if(records[1].length() == 0) throw new RuntimeException(INVALID_FORMAT_NAME_EMPTY);
		if(records[2].length() == 0) throw new RuntimeException(INVALID_FORMAT_CUSTOMER_LAST_NAME_EMPTY);
		if(!records[3].matches("D|C")) throw new RuntimeException(INVALID_FORMAT_IDENTIFICATION_TYPE);
		if(line.endsWith(",")) throw new RuntimeException(INVALID_FORMAT_IDENTIFICATION_NUMBER_EMPTY);		
		if(records[3].equals("D")){
			if(!records[4].matches("[0-9]{8}")) throw new RuntimeException(INVALID_FORMAT_IDENTIFICATION_NUMBER);
		}else{
			if(!records[4].matches("[0-9]{2}-[0-9]{8}-[0-9]{1}")) throw new RuntimeException(INVALID_FORMAT_IDENTIFICATION_NUMBER);
		}
		if(system.existsCustomerIdentifiedAs(records[3], records[4])) throw new RuntimeException(INVALID_FORMAT_DOC_DUPLICATE);

	}
	
	protected void validateNewSupplier() {
		validateCantColumns(4);	
		if(records[1].length() == 0) throw new RuntimeException(INVALID_FORMAT_NAME_EMPTY);
		if(!records[2].matches("D|C")) throw new RuntimeException(INVALID_FORMAT_IDENTIFICATION_TYPE);
		if(line.endsWith(",")) throw new RuntimeException(INVALID_FORMAT_IDENTIFICATION_NUMBER_EMPTY);		
		if(!records[3].matches("[0-9]{1,9}")) throw new RuntimeException(INVALID_FORMAT_IDENTIFICATION_NUMBER);
		if(system.existsSupplierIdentifiedAs(records[2], records[3])) throw new RuntimeException(INVALID_FORMAT_DOC_DUPLICATE);

	}

	private void validateCantColumns(int validCant) {
		int cantColumn = records.length;
		if(line.endsWith(",")) cantColumn++;
		if(!(cantColumn == validCant)) throw new RuntimeException(INVALID_FORMAT_CANT_COLUMNS);
	}

	protected boolean isAddress() {
		return records[0].equals("A");
	}

}