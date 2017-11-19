package com.tenpines.advancetdd;

public class Environment {

	
	public static CustomerSystem createCutomerSystem(){
		if(IntegrationEnvironment.isCurrent()){
			return IntegrationEnvironment.createSystem();
		}else if(DevelopEnvironment.isCurrent()){
			return DevelopEnvironment.createSystem();
		}else{
			throw new Error("No hay un environmet activo");
		}
	}
}

