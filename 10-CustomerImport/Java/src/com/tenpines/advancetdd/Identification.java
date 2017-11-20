package com.tenpines.advancetdd;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="IDENTIFICATIONS")
@IdClass(Identification.class)
public class Identification implements Serializable{

	@Id
	@Pattern(regexp = "D|C")
	private String identificationType;;
	@Id
	@NotEmpty
	private String identificationNumber;



	public Identification() {

	}
	
	@Override
	public String toString() {
		return "Identification [" + ", identificationType=" + identificationType
				+ ", identificationNumber=" + identificationNumber + "]";
	}

	public Identification(String identificationType, String identificationNumber) {
		super();
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
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