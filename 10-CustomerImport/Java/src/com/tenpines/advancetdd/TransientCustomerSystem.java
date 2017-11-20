package com.tenpines.advancetdd;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class TransientCustomerSystem implements CustomerSystem {
	
	private List<Party> parties;

	@Override
	public void beginTransaction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureSession() {
		parties = new ArrayList<Party>();	
	}

	@Override
	public Party customerIdentifiedAs(String idType, String idNumber) {
		List<Party> lPartiesRes = new ArrayList<Party>();
		for(Party c : parties){
			if(c.isIdentifiedAs(idType, idNumber)){
				lPartiesRes.add(c);
			}
		}
		assertEquals(1,lPartiesRes.size());
		return lPartiesRes.get(0);	
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addParty(Party newPartie) {
		parties.add(newPartie);
	}

	@Override
	public int numberOfCustomers() {
		return parties.size();
	}

}
