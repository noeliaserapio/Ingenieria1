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
		return lCustomerRes.get(0);	
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
	public boolean existsCustomerIdentifiedAs(String idType, String idNumber) {
		for(Customer c : customers){
			if(c.isIdentifiedAs(idType, idNumber)){
				return true;
			}
		}
		return false;
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
		return lSupplierRes.get(0);	
	}
	
	
	@Override
	public boolean existsSupplierIdentifiedAs(String idType, String idNumber) {
		for(Supplier s : suppliers){
			if(s.isIdentifiedAs(idType, idNumber)){
				return true;
			}
		}
		return false;
	}


}
