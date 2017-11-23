package com.tenpines.advancetdd;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="ADDRESSES")
public class Address {
	
	@Id
	@GeneratedValue
	private Long id;
	@NotEmpty
	private String streetName;
	@Min(1)
	private int streetNumber;
	@NotEmpty
	private String town;
	@Min(1000)
	private int zipCode;
	@NotEmpty
	private String province;
	

	public Address(String streetName, int streetNumber, String town, int zipCode, String province) {
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.town = town;
		this.zipCode = zipCode;
		this.province = province;
	}

	public String getStreetName() {
		return streetName;
	}
	
	public int getStreetNumber() {
		return streetNumber;
	}
	
	public String getTown() {
		return town;
	}
	
	
	public int getZipCode() {
		return zipCode;
	}
	
	
	public String getProvince() {
		return province;
	}
	

	public boolean isAt(String streetName) {
		return this.streetName.equals(streetName);
	}
}
