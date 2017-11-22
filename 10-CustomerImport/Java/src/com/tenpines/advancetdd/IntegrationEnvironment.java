package com.tenpines.advancetdd;

public class IntegrationEnvironment extends Environment{
	
	public static boolean isCurrent(){
		return true;
	}
	
	public static ErpSystem createSystem(){
		return new PersistentErpSystem();
	}
}
