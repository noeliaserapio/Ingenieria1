package com.tenpines.advancetdd;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
@Entity
@Table(name="IDENTIFICATIONS")
@IdClass(Identification.class)
public class Identification implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1707524195227803326L;
	private static long numId = 0;
	
	@Id
	private Long iden;;
	@Id
	@Pattern(regexp = "D|C")
	private String identificationType;;
	@Id
	@NotEmpty
	private String identificationNumber;

	public Identification() {;
		this.iden = ++numId;
	}	
	
	public Identification(String identificationType, String identificationNumber) {
		super();
		this.identificationType = identificationType;
		this.identificationNumber = identificationNumber;
		this.iden = ++numId;
	}	

	public String getIdentificationType() {
		return identificationType;
	}
	
	public String getIdentificationNumber() {
		return identificationNumber;
	}

}