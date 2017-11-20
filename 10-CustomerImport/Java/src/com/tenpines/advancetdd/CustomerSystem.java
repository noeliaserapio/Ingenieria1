package com.tenpines.advancetdd;

public interface CustomerSystem {

	public void beginTransaction();

	public void configureSession();

	public Party customerIdentifiedAs(String idType,
			String idNumber);

	public void stop();

	public void commit();

	public void addParty(Party newCustomer);
	
	public int numberOfCustomers();
	
}