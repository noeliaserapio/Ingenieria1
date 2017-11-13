package com.tenpines.advancetdd;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.hibernate.Session;

public class CustomerImporter {

	private LineNumberReader lineReader;
	private String line;
	private String[] records;
	private Customer newCustomer;
	private Session session;
	
	public static final String ADDRESS_WITHOUT_CUSTOMER = "Can not have address without customer";
	
	public static final String INVALID_BEGIN_FORMAT_LINE = "This line doesn't start correctly";
	public static final String INVALID_FORMAT_IDENTIFICATION_TYPE = "The identification type doesn´t have the correct format";
	public static final String INVALID_FORMAT_CUSTOMER_NAME_EMPTY = "Customer's name is empty";
	public static final String INVALID_FORMAT_CUSTOMER_LAST_NAME_EMPTY = "Customer's last name is empty";
	public static final String INVALID_FORMAT_CUSTOMER_IDENTIFICATION_NUMBER_EMPTY = "Customer's identification number is empty";
	public static final String INVALID_FORMAT_CUSTOMER_IDENTIFICATION_NUMBER = "The format of Customer's identification number is invalid";

	
	public static final String INVALID_FORMAT_CANT_COLUMNS = "The quantity of columns of this line is invalid";

	public static final String INVALID_FORMAT_ADDRESS_STREET_NAME_EMPTY = "Address street name is empty";
	public static final String INVALID_FORMAT_ADDRESS_TOWN_EMPTY = "Address town is empty";
	public static final String INVALID_FORMAT_ADDRESS_PROVINCE_EMPTY = "Address province is empty";
	public static final String INVALID_FORMAT_STREET_NUMBER = "The street number is less than 1";
	public static final String INVALID_FORMAT_ZIP_CODE = "The zip code is less than 1000";

	
	public CustomerImporter(Session session) {
		this.session = session;
	}
	
	public void importCustomers(Reader fileReader) throws IOException{
		
		lineReader = new LineNumberReader(fileReader);
			
		while (hasNextLine()) {
			readLine();
			parseRegister();	
		}
			
		fileReader.close();
	}

	private void parseRegister() {
		if (isCustomer()){
			parseCustomer();
		}else if (isAddress()) {
			parseAddress();
		}else{
			throw new RuntimeException(INVALID_BEGIN_FORMAT_LINE);
		}
	}

	private void parseCustomer() {
		validateNewCustomer();
		newCustomer = addNewCustomer();
	}

	private void readLine() {
		records = line.split(",");
	}

	private boolean hasNextLine() throws IOException {
		line = lineReader.readLine(); 
		boolean hayLinea = line!=null;
		return hayLinea;
	}

	private void parseAddress() {
		
		if(newCustomer == null) throw new RuntimeException(ADDRESS_WITHOUT_CUSTOMER);
		
		Address newAddress = new Address();

		if(records[1].length() == 0) throw new RuntimeException(INVALID_FORMAT_ADDRESS_STREET_NAME_EMPTY);
		newAddress.setStreetName(records[1]);
		int streetNum = Integer.parseInt(records[2]);
		if(streetNum <1) throw new RuntimeException(INVALID_FORMAT_STREET_NUMBER);
		newAddress.setStreetNumber(streetNum);
		if(records[3].length() == 0) throw new RuntimeException(INVALID_FORMAT_ADDRESS_TOWN_EMPTY);
		newAddress.setTown(records[3]);
		int zipCode = Integer.parseInt(records[4]);
		if(zipCode <1000) throw new RuntimeException(INVALID_FORMAT_ZIP_CODE);
		newAddress.setZipCode(zipCode);	

		newAddress.setProvince(records[5]);
		
		newCustomer.addAddress(newAddress);

	}

	private Customer addNewCustomer() {
		Customer newCustomer;
		newCustomer = new Customer();
		newCustomer.setFirstName(records[1]);
		newCustomer.setLastName(records[2]);
		newCustomer.setIdentificationType(records[3]);
		newCustomer.setIdentificationNumber(records[4]);
		session.persist(newCustomer);
		return newCustomer;
	}

	private void validateNewCustomer() {
		int cantColumn = records.length;
		if(line.endsWith(",")) cantColumn++;
		if(!(cantColumn == 5)) throw new RuntimeException(INVALID_FORMAT_CANT_COLUMNS);
		
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

	private boolean isAddress() {
		return records[0].equals("A");
	}

	private boolean isCustomer() {
		return records[0].equals("C");
	}

}
