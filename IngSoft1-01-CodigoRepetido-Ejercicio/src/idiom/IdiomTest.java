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

import junit.framework.TestCase;

public class IdiomTest extends TestCase {
	
	protected CustomerBook customerBook;

	public void setUp(){
		customerBook = new CustomerBook ();
	}
	
	public void testAddingCustomerShouldNotTakeMoreThan50Milliseconds(){
		
		long millisecondsBeforeRunning = System.currentTimeMillis();
		customerBook.addCustomerNamed("John Lennon");
		long millisecondsAfterRunning = System.currentTimeMillis();
		
		assertTrue( (millisecondsAfterRunning-millisecondsBeforeRunning) < 50 );
	}

	public void testRemovingCustomerShouldNotTakeMoreThan100Milliseconds(){
		String paulMcCartney = "Paul McCartney";
		
		customerBook.addCustomerNamed(paulMcCartney);

		long millisecondsBeforeRunning = System.currentTimeMillis();
		customerBook.removeCustomerNamed(paulMcCartney);
		long millisecondsAfterRunning = System.currentTimeMillis();
		
		assertTrue( (millisecondsAfterRunning-millisecondsBeforeRunning) < 100 );
	}
	
	public void testCanNotAddACustomerWithEmptyName (){
		
		try {
			customerBook.addCustomerNamed("");
			fail();
		} catch (RuntimeException e) {
			assertEquals(e.getMessage(),CustomerBook.CUSTOMER_NAME_EMPTY);
			assertTrue(customerBook.isEmpty());
		}
	}

	public void testCanNotRemoveNotAddedCustomers (){
		
		try {
			customerBook.removeCustomerNamed("John Lennon");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(),CustomerBook.INVALID_CUSTOMER_NAME);
			assertEquals(0,customerBook.numberOfCustomers());
		}
	}
}
