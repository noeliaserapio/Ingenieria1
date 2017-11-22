package com.tenpines.advancetdd;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.*;

@Entity  
@Table(name = "PARTIES")  
@Inheritance(strategy=InheritanceType.JOINED)  
public class Party {
	@Id
	@GeneratedValue
	private long id;

	@OneToOne(cascade = CascadeType.ALL)
	protected Identification identification;

	@OneToMany(cascade = CascadeType.ALL)
	protected Set<Address> addresses = new HashSet<Address>();
	
	public static final String ADDRESS_NOT_FOUND = "The address doesn't correspond to this customer";

	public Party(String identificationType, String identificationNumber) {
		identification = new Identification(identificationType,identificationNumber);
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
	
	public String getIdentificationType() {
		return identification.getIdentificationType();
	}


	public String getIdentificationNumber() {
		return identification.getIdentificationNumber();
	}


}