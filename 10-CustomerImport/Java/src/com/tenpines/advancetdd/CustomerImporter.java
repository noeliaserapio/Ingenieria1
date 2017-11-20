package com.tenpines.advancetdd;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;


public class CustomerImporter extends Importer {
	
	public CustomerImporter(ErpSystem system, Reader readStream) {
		this.system = system;
		this.readStream = readStream;
	}
	
	protected void parseRegister() {
		if (isCustomer()){
			parseCustomer();
		}else if (isAddress()) {
			parseAddress();
		}else{
			throw new RuntimeException(INVALID_BEGIN_FORMAT_LINE);
		}
	}
	
	public void importCustomers() throws IOException{
		lineReader = new LineNumberReader(readStream);
		while (hasNextLine()) {
			readLine();
			parseRegister();	
		}			
	}

	void parseCustomer() {
		validateNewCustomer();
		lastParty = addNewCustomer();
	}
	

	boolean isCustomer() {
		return records[0].equals("C");
	}
	
}
