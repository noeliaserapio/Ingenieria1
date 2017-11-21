package com.tenpines.advancetdd;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity  
@Table(name = "PARTIES")  
@Inheritance(strategy=InheritanceType.JOINED)  
public class Party {
	@Id
	@GeneratedValue
	private long id;

	@Pattern(regexp = "D|C")
	protected String identificationType;;

	@NotEmpty
	protected String identificationNumber;

	@OneToMany(cascade = CascadeType.ALL)
	protected Set<Address> addresses;
	
	public static final String ADDRESS_NOT_FOUND = "The address doesn't correspond to this customer";

	public Party() {
		super();
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
		return identificationType.equals(idType) && identificationNumber.equals(idNumber);
	}
	
	public String getIdentificationType() {
		return identificationType;
	}


	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}


	public String getIdentificationNumber() {
		return identificationNumber;
	}


	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}


}