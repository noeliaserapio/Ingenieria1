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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.TestCase;

public class IdiomTest extends TestCase {
	
	protected CustomerBook customerBook;

	public void setUp(){
		customerBook = new CustomerBook ();
	}
	
	private void timeShouldNotTakeMoreThan(long milliseconds,String methodCustomerBook, String customer ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		long millisecondsBeforeRunning = System.currentTimeMillis();	
		Method methodMeasured = customerBook.getClass().getDeclaredMethod(methodCustomerBook,String.class);
		methodMeasured.invoke(customerBook,customer);
		long millisecondsAfterRunning = System.currentTimeMillis();
		
		assertTrue( (millisecondsAfterRunning-millisecondsBeforeRunning) < milliseconds);
	}
	
	public void testAddingCustomerShouldNotTakeMoreThan50Milliseconds() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		timeShouldNotTakeMoreThan(50 ,"addCustomerNamed", "John Lennon" );
	
	}

	public void testRemovingCustomerShouldNotTakeMoreThan100Milliseconds() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String paulMcCartney = "Paul McCartney";
		customerBook.addCustomerNamed(paulMcCartney);

		timeShouldNotTakeMoreThan(100 ,"removeCustomerNamed", paulMcCartney );

	}
	
	public void testCanNotAddACustomerWithEmptyName (){
		
		try {
			customerBook.addCustomerNamed("");
			fail();
		} catch (RuntimeException e) {
			assertEqualsMessageAndAssertCustomerBookIsEmty(e.getMessage(),CustomerBook.CUSTOMER_NAME_EMPTY);
		}
	}

	public void testCanNotRemoveNotAddedCustomers (){
		
		try {
			customerBook.removeCustomerNamed("John Lennon");
			fail();
		} catch (IllegalArgumentException e) {
			assertEqualsMessageAndAssertCustomerBookIsEmty(e.getMessage(),CustomerBook.INVALID_CUSTOMER_NAME);
		}
	}
	
	private void assertEqualsMessageAndAssertCustomerBookIsEmty(String exceptionMessage, String customerMessage){
		assertEquals(exceptionMessage, customerMessage);
		assertTrue(customerBook.isEmpty());  
	}
	
	private void tryFunctionExceptionAndAssertCustomerBookIsEmty(String customerMessageError, String methodCustomerBook, String customer, Class<? extends Exception>  claseException) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{		
		try {
			Method methodTry = customerBook.getClass().getDeclaredMethod(methodCustomerBook,String.class);
			methodTry.invoke(customerBook,customer);		
	        fail();
	        claseException gg;//
	    } catch (Exception(claseException) e) {
			assertEquals(e.getMessage(), customerMessageError);
			assertTrue(customerBook.isEmpty());
	    }
	}
}
