package com.tenpines.advancetdd;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;


public class CustomerImporter {

	private LineNumberReader lineReader;
	private String line;
	private String[] records;
	private Party lastParty;
	
	private CustomerSystem system;
	private Reader readStream;
	
	public static final String ADDRESS_WITHOUT_ASIGNATION = "Can not have address without asignation";

	public static final String INVALID_BEGIN_FORMAT_LINE = "This line doesn't start correctly";
	public static final String INVALID_FORMAT_IDENTIFICATION_TYPE = "The identification type doesn´t have the correct format";
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

	
	public CustomerImporter(CustomerSystem system, Reader readStream) {
		this.system = system;
		this.readStream = readStream;
	}
	
	public void importCustomers() throws IOException{
		
		lineReader = new LineNumberReader(readStream);

		while (hasNextLine()) {
			readLine();
			parseRegister();	
		}
			
		readStream.close();
	}

	private void parseRegister() {
		if (isCustomer()){
			parseCustomer();
		}else if(isSupplier()){
			parseSupplier();
		}else if (isExistCustomerSup()) {
			parseCustomerSupExist();
		}else if (isNewCustomerSup()) {
			parseCustomerSupNew();
		}else if (isAddress()) {
			parseAddress();
		}else{
			throw new RuntimeException(INVALID_BEGIN_FORMAT_LINE);
		}
	}

	private void validateNewSupplier() {
		// TODO Auto-generated method stub
		
	}

	private void readLine() {
		records = line.split(",");
	}

	private boolean hasNextLine() throws IOException {
		line = lineReader.readLine(); 
		return line!=null;
	}

	private void parseAddress() {
		validateAddress();
		addAddress();
	}
	
	private void parseCustomer() {
		validateNewCustomer();
		lastParty = addNewCustomer();
	}
	
	private void parseSupplier() {
		validateNewSupplier();
		lastParty = addNewSupplier();
	}
	
	private void parseCustomerSupExist() {
		
		Customer customerExis = (Customer) system.customerIdentifiedAs(records[1], records[2]);
		((Supplier) lastParty).addCustomer( customerExis);
	}
	
	private void parseCustomerSupNew() {
		validateNewCustomer();
		((Supplier) lastParty).addCustomer(addNewCustomer());
	}
	
	
	private Customer addNewCustomer() {
		Customer newCustomer;
		newCustomer = new Customer();
		newCustomer.setFirstName(records[1]);
		newCustomer.setLastName(records[2]);
		newCustomer.setIdentificationType(records[3]);
		newCustomer.setIdentificationNumber(records[4]);
		persist(newCustomer);
		return newCustomer;
	}
	
	private Supplier addNewSupplier() {
		Supplier newSupplier = new Supplier();
		newSupplier.setName(records[1]);
		newSupplier.setIdentificationType(records[2]);
		newSupplier.setIdentificationNumber(records[3]);
		persist(newSupplier);
		return newSupplier;
	}

	
	
	private void addAddress() {
		Address newAddress = new Address();
		newAddress.setStreetName(records[1]);
		newAddress.setStreetNumber(Integer.parseInt(records[2]));
		newAddress.setTown(records[3]);
		newAddress.setZipCode(Integer.parseInt(records[4]));	
		newAddress.setProvince(records[5]);	
		lastParty.addAddress(newAddress);
	}

	private void persist(Party partie) {
		system.addParty(partie);
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

	private void validateNewCustomer() {
		validateCantColumns(5);	
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

	private void validateCantColumns(int validCant) {
		int cantColumn = records.length;
		if(line.endsWith(",")) cantColumn++;
		if(!(cantColumn == validCant)) throw new RuntimeException(INVALID_FORMAT_CANT_COLUMNS);
	}

	private boolean isAddress() {
		return records[0].equals("A");
	}

	private boolean isCustomer() {
		return records[0].equals("C");
	}
	
	private boolean isSupplier() {
		return records[0].equals("S");
	}

	private boolean isExistCustomerSup() {
		return records[0].equals("EC");
	}
	
	private boolean isNewCustomerSup() {
		return records[0].equals("NC");
	}
}
