package com.tenpines.advancetdd;

public interface ErpSystem {
	
	public static final String CUSTOMER_NOT_FOUND = "The customer doesn't exit";

	public void beginTransaction();

	public void configureSession();

	public void stop();

	public void commit();
	//customer
	public Customer customerIdentifiedAs(String idType,
			String idNumber);
	public void addCustomer(Customer newCustomer);
	
	public int numberOfCustomers();
    //supplier
	public void addSupplier(Supplier newSupplier);

	public int numberOfSuppliers();

	public Supplier supplierIdentifiedAs(String idType, String idNumber);

	
}