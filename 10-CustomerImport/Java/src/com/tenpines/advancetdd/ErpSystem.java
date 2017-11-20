package com.tenpines.advancetdd;

public interface ErpSystem {

	public void beginTransaction();

	public void configureSession();

	public void stop();

	public void commit();
	//customer
	public Customer customerIdentifiedAs(String idType,
			String idNumber);
	
	public boolean existsCustomerIdentifiedAs(String idType, String idNumber);

	public void addCustomer(Customer newCustomer);
	
	public int numberOfCustomers();
    //supplier
	public void addSupplier(Supplier newSupplier);

	public int numberOfSuppliers();

	public Supplier supplierIdentifiedAs(String idType, String idNumber);

	public boolean existsSupplierIdentifiedAs(String idType, String idNumber);

	
}