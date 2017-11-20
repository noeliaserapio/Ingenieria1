package com.tenpines.advancetdd;

public class DevelopEnvironment extends Environment{
	
	public static boolean isCurrent(){
		return !IntegrationEnvironment.isCurrent();
	}
	
	public static ErpSystem createSystem(){
		return new TransientErpSystem();
	}

}
