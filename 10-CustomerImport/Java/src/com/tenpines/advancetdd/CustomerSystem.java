package com.tenpines.advancetdd;

public interface CustomerSystem {

	public abstract void beginTransaction();

	public abstract void configureSession();

	public abstract Customer customerIdentifiedAs(String idType,
			String idNumber);

	public abstract void stop();

	public abstract void commit();

	public abstract void addCustomer(Customer newCustomer);
	
	public abstract int numberOfCustomers();
	
}