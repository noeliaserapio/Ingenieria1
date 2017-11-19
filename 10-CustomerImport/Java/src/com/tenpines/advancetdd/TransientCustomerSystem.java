package com.tenpines.advancetdd;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class TransientCustomerSystem implements CustomerSystem {
	
	private List<Customer> customers;

	@Override
	public void beginTransaction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureSession() {
		customers = new ArrayList<Customer>();	
	}

	@Override
	public Customer customerIdentifiedAs(String idType, String idNumber) {
		List<Customer> lCustomerRes = new ArrayList<Customer>();
		for(Customer c : customers){
			if(c.isIdentifiedAs(idType, idNumber)){
				lCustomerRes.add(c);
			}
		}
		assertEquals(1,lCustomerRes.size());
		return lCustomerRes.get(0);	
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCustomer(Customer newCustomer) {
		customers.add(newCustomer);
	}

	@Override
	public int numberOfCustomers() {
		return customers.size();
	}

}
