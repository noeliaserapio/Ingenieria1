package com.tenpines.advancetdd;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table( name = "SUPPLIERS" )
public class Supplier extends Party {

	@NotEmpty
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Customer> customers;
	
	
	public static final String ADDRESS_NOT_FOUND = "The address doesn't correspond to this customer";
	
	public Supplier()
	{
		addresses = new HashSet<Address>();
		customers= new HashSet<Customer>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addCustomer(Customer c){
		customers.add(c);
	}

}
