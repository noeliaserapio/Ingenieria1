package com.tenpines.advancetdd;

import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table( name = "CUSTOMERS" )
public class Customer extends Party {

	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	public Customer()
	{
		addresses = new HashSet<Address>();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Customer){
			return (identificationType.equals(((Customer) obj).getIdentificationType()) && identificationNumber.equals(((Customer) obj).getIdentificationNumber())) ;
		}else{
			return false;
		}
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}