/*
 * Developed by 10Pines SRL
 * License: 
 * This work is licensed under the 
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License. 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ 
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View, 
 * California, 94041, USA.
 *  
 */
package idiom;

import java.util.ArrayList;
import java.util.List;

public class CustomerBook {

	public static final String CUSTOMER_NAME_EMPTY = "Customer name can not be empty";
	public static final String CUSTOMER_ALREADY_EXISTS = "Customer already exists";
	public static final String INVALID_CUSTOMER_NAME = "Invalid customer name";

	private List<String> customerNames = new ArrayList<String>();
	
	public void addCustomerNamed(String name) {
		if (name.isEmpty()) throw new RuntimeException (CUSTOMER_NAME_EMPTY);
		if (containsCustomerNamed(name)) throw new RuntimeException (CUSTOMER_ALREADY_EXISTS);
		
		customerNames.add (name);
	}

	public boolean isEmpty() {
		return customerNames.isEmpty();
	}

	public int numberOfCustomers() {
		return customerNames.size();
	}

	public boolean containsCustomerNamed(String name) {
		return customerNames.contains(name);
	}

	public void removeCustomerNamed(String name) {
		if (!containsCustomerNamed(name))
			throw new IllegalArgumentException(INVALID_CUSTOMER_NAME);
		
		customerNames.remove(name);
	}

}
