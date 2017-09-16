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
package stack;
import junit.framework.TestCase;


public class StackTest extends TestCase {

	public void testStackShouldBeEmptyWhenCreated(){
		Stack stack = new Stack();
		
		assertTrue(stack.isEmpty());
	}
	
	public void testPushAddElementsToTheStack(){
		Stack stack = new Stack();
		stack.push("Something");
		
		assertFalse(stack.isEmpty());
	}

	public void testPopRemovesElementsFromTheStack() throws Exception{
		Stack stack = new Stack();
		stack.push("Something");
		stack.pop();
		
		assertTrue(stack.isEmpty());
	}
	
	public void testPopReturnsLastPushedObject() throws Exception{
		Stack stack = new Stack();
		String pushedObject = "Something";
		stack.push(pushedObject);
		assertEquals(pushedObject, stack.pop());
	}

	public void testStackBehavesLIFO () throws Exception{
		String firstPushed = "First";
		String secondPushed = "Second";
		Stack stack = new Stack();
		stack.push(firstPushed);
		stack.push(secondPushed);
		
		assertEquals(secondPushed,stack.pop());
		assertEquals(firstPushed,stack.pop());
		assertTrue(stack.isEmpty());
	}

	public void testTopReturnsLastPushedObject() throws Exception{
		Stack stack = new Stack();
		String pushedObject = "Something";

		stack.push(pushedObject);

		assertEquals(pushedObject, stack.top());
	}

	public void testTopDoesNotRemoveObjectFromStack() throws Exception{
		Stack stack = new Stack();
		String pushedObject = "Something";

		stack.push(pushedObject);

		assertEquals((Integer) 1,stack.size()); 
		stack.top();
		assertEquals((Integer) 1,stack.size());
	}

	public void testCanNotPopWhenThereAreNoObjectsInTheStack(){
		Stack stack = new Stack();
		
		try {
			stack.pop();
			fail();
		} catch (Exception stackIsEmpty){
			assertEquals(Stack.STACK_EMPTY_DESCRIPTION,stackIsEmpty.getMessage());
		}
	}

	public void testCanNotTopWhenThereAreNoObjectsInTheStack(){
		Stack stack = new Stack();

		try {
			stack.top();
			fail();
		} catch (Exception stackIsEmpty){
			assertEquals(Stack.STACK_EMPTY_DESCRIPTION,stackIsEmpty.getMessage());
		}
	}
}
