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
	}

	public void addAddress(Address anAddress){
		addresses.add(anAddress);
	}

	public Address addressAt(String streetName) {
		for (Address address : addresses) 
			if (address.isAt(streetName))
				return address;
		
		throw new RuntimeException(ADDRESS_NOT_FOUND);
	}

	public int numberOfAddresses() {
		return addresses.size();
	}

	public boolean isIdentifiedAs(String idType, String idNumber) {
		return identification.getIdentificationType().equals(idType) && identification.getIdentificationNumber().equals(idNumber);
	}


}
