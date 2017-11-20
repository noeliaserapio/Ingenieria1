package com.tenpines.advancetdd;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.*;

@Entity  
@Table(name = "Parties")  
@Inheritance(strategy=InheritanceType.JOINED)  
public class Party {
	@Id
	@GeneratedValue
	private long id;
	@OneToOne(cascade = CascadeType.ALL)
	protected Identification identification = new Identification();

	@OneToMany(cascade = CascadeType.ALL)
	protected Set<Address> addresses;
	
	public static final String ADDRESS_NOT_FOUND = "The address doesn't correspond to this customer";

	public Party() {
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getIdentificationType() {
		return identification.getIdentificationType();
	}

	public void setIdentificationType(String identificationType) {
		this.identification.setIdentificationType(identificationType);
	}

	public String getIdentificationNumber() {
		return identification.getIdentificationNumber();
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identification.setIdentificationNumber(identificationNumber);
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