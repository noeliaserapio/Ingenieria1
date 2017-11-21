package com.tenpines.advancetdd;

public class IntegrationEnvironment extends Environment{
	
	public static boolean isCurrent(){
		return false;
	}
	
	public static ErpSystem createSystem(){
		return new PersistentErpSystem();
	}
}
