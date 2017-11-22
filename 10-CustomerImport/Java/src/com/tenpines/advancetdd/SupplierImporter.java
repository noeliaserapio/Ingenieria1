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
		if(!system.existsCustomerIdentifiedAs(records[1], records[2])) throw new Error(CUSTOMER_NOT_FOUND); 
		
		Customer customerExis = (Customer) system.customerIdentifiedAs(records[1], records[2]);
		 ((Supplier) lastParty).addCustomer( customerExis);
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
		persist(newSupplier);
		return newSupplier;
	}
	
	void persist(Supplier newSupplier) {
		system.addSupplier(newSupplier);
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
