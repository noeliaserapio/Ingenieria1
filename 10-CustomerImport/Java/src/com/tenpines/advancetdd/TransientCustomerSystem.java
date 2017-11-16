package com.tenpines.advancetdd;

import java.util.ArrayList;
import java.util.List;

public class TransientCustomerSystem implements CustomerSystem {
	
	private List<Customer> customers = new ArrayList<Customer>();

	@Override
	public void beginTransaction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureSession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Customer> customersIdentifiedAs(String idType, String idNumber) {
		List<Customer> lCustomerRes = new ArrayList<Customer>();
		for(Customer c : customers){
			if(c.getIdentificationType().equals(idType) && 
					c.getIdentificationNumber().equals(idNumber))
					lCustomerRes.add(c);
		}
		return lCustomerRes;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Customer> customers() {
		return customers;
	}

	@Override
	public void persistSystem(Customer newCustomer) {
		customers.add(newCustomer);
	}

}
