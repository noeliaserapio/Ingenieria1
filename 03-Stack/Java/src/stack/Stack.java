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

	private StackState state;
	
	public static final String STACK_EMPTY_DESCRIPTION = "Stack is Empty";
	
	public Stack()
	{
		state = new StackVaciaState(this);
	}

	public void push (Object anObject)
	{
		state.push(anObject);
	}
	
	public Object pop()
	{
		return state.pop();
	}
	
	public Object top()
	{
		return state.top();
	}

	public Boolean isEmpty()
	{
		return state.isEmpty();
	}

	public Integer size()
	{
		return state.size();
	}

	public void setState(StackState stateStack) {
		this.state = stateStack;
	}
	
}
