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

public class Stack {

	public static final String STACK_EMPTY_DESCRIPTION = "Stack is Empty";

	public void push (Object anObject)
	{
		shouldImplement();
	}
	
	public Object pop()
	{
		return shouldImplement();
	}
	
	public Object top()
	{
		return shouldImplement();
	}

	public Boolean isEmpty()
	{
		return true;
	}

	public Integer size()
	{
		return (Integer) shouldImplement();
	}
	
	private Object shouldImplement()
	{
		throw new RuntimeException ("Should Implement");
	}
	
}
