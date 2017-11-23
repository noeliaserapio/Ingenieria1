package com.tenpines.advancetdd;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;


public class SupplierImporter extends Importer {

	public SupplierImporter(ErpSystem system, Reader readStream) {
		this.system = system;
		this.readStream = readStream;
	}
	
	public void importSuppliers() throws IOException{
		
		lineReader = new LineNumberReader(readStream);

		while (hasNextLine()) {
			readLine();
			parseRegister();	
		}			
	}
	
	protected void parseRegister() {
		if(isSupplier()){
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
	
	private void parseCustomerSupExist() {
		validateCustomerExist();
		Customer customerExis = (Customer) system.customerIdentifiedAs(records[1], records[2]);
		 ((Supplier) lastParty).addCustomer( customerExis);
	}
	
	private void validateCustomerExist() {
		validateCantColumns(3);	
		if(!records[1].matches("D|C")) throw new RuntimeException(INVALID_FORMAT_IDENTIFICATION_TYPE);
		if(line.endsWith(",")) throw new RuntimeException(INVALID_FORMAT_IDENTIFICATION_NUMBER_EMPTY);
		if(!records[2].matches("[0-9]{1,9}")) throw new RuntimeException(INVALID_FORMAT_IDENTIFICATION_NUMBER);
		if(!system.existsCustomerIdentifiedAs(records[1], records[2])) throw new Error(CUSTOMER_NOT_FOUND); 
	}

	private void parseCustomerSupNew() {
		validateNewCustomer();
		 ((Supplier) lastParty).addCustomer(addNewCustomer());
	}

	void parseSupplier() {
		validateNewSupplier();
		lastParty = addNewSupplier();
	}

	protected Supplier addNewSupplier() {
		Supplier newSupplier = new Supplier(records[1],records[2],records[3]);
		system.persist(newSupplier);
		return newSupplier;
	}
	
	boolean isSupplier() {
		return records[0].equals("S");
	}

	boolean isExistCustomerSup() {
		return records[0].equals("EC");
	}
	
	boolean isNewCustomerSup() {
		return records[0].equals("NC");
	}
}
