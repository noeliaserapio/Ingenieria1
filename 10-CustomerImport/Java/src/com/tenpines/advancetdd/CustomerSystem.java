package com.tenpines.advancetdd;

import java.util.List;

public interface CustomerSystem {

	public abstract void beginTransaction();

	public abstract void configureSession();

	public abstract List<Customer> customersIdentifiedAs(String idType,
			String idNumber);

	public abstract void close();

	public abstract void commit();

	public abstract List<Customer> customers();

	public abstract void persistSystem(Customer newCustomer);

}