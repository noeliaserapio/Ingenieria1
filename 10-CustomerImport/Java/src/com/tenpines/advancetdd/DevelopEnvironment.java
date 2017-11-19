package com.tenpines.advancetdd;

public class DevelopEnvironment extends Environment{
	
	public static boolean isCurrent(){
		return !IntegrationEnvironment.isCurrent();
	}
	
	public static CustomerSystem createSystem(){
		return new TransientCustomerSystem();
	}

}
