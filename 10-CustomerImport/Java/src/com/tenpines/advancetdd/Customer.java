package com.tenpines.advancetdd;

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
	public Customer(String firstName, String lastName, String identificationType, String identificationNumber)
	{
		super(identificationType,identificationNumber);
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}