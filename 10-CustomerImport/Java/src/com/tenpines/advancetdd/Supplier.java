package com.tenpines.advancetdd;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table( name = "SUPPLIERS" )
public class Supplier extends Party {

	public static final String CAN_NOT_ADD_REPEATED_CLIENT_FOR_SUPPLIER = "Can not add repeated client for this supplier";

	@NotEmpty
	private String name;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Customer> customers = new HashSet<Customer>();
	
	
	public static final String ADDRESS_NOT_FOUND = "The address doesn't correspond to this customer";
	
	public Supplier(String name, String identificationType, String identificationNumber)
	{
		super(identificationType,identificationNumber);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addCustomer(Customer c){
		for(Customer cus : customers){
			if(c.isIdentifiedAs(cus.getIdentificationType(), cus.getIdentificationNumber())){
				throw new RuntimeException(CAN_NOT_ADD_REPEATED_CLIENT_FOR_SUPPLIER);
			}
		}
		customers.add(c);
	}
	
	public int numberOfCustomers(){
		return customers.size();
	}
	
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

	public Set<Customer> getCustomers() {
		return customers;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

}
