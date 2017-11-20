package com.tenpines.advancetdd;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class TransientErpSystem implements ErpSystem {
	
	private List<Customer> customers;
	private List<Supplier> suppliers;
	
	@Override
	public void configureSession() {
		customers = new ArrayList<Customer>();
		suppliers = new ArrayList<Supplier>();
	}
	@Override
	public void beginTransaction() {
		
	}
	
	@Override
	public void stop() {
		
	}

	@Override
	public void commit() {
		
	}


	@Override
	public Customer customerIdentifiedAs(String idType, String idNumber) {
		List<Customer> lCustomerRes = new ArrayList<Customer>();
		for(Customer c : customers){
			if(c.isIdentifiedAs(idType, idNumber)){
				lCustomerRes.add(c);
			}
		}
		if(lCustomerRes.size() == 1){
			return lCustomerRes.get(0);	
		}else{
			throw new Error(ErpSystem.CUSTOMER_NOT_FOUND);
		}
	}


	@Override
	public void addCustomer(Customer newCustomer) {
		customers.add(newCustomer);
	}

	@Override
	public int numberOfCustomers() {
		return customers.size();
	}

	@Override
	public void addSupplier(Supplier newSupplier) {
		suppliers.add(newSupplier);
	}

	@Override
	public int numberOfSuppliers() {
		return suppliers.size();
	}

	@Override
	public Supplier supplierIdentifiedAs(String idType, String idNumber) {
		List<Supplier> lSupplierRes = new ArrayList<Supplier>();
		for(Supplier s : suppliers){
			if(s.isIdentifiedAs(idType, idNumber)){
				lSupplierRes.add(s);
			}
		}
		assertEquals(1,lSupplierRes.size());
		return lSupplierRes.get(0);	
	}

}
