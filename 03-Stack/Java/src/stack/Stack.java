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

import java.util.ArrayList;
import java.util.List;

public class Stack {

	private List<Object> stack;
	
	public static final String STACK_EMPTY_DESCRIPTION = "Stack is Empty";
	
	public Stack()
	{
		stack = new ArrayList<Object>();
	}

	public void push (Object anObject)
	{
		stack.add(anObject);
	}
	
	public Object pop() throws Exception 
	{
		if(this.isEmpty()){	
			throw new Exception (STACK_EMPTY_DESCRIPTION);	
		}else{
			return stack.remove(this.size()-1);
		}
	}
	
	public Object top() throws Exception 
	{
		if(this.isEmpty()){			
			throw new Exception (STACK_EMPTY_DESCRIPTION);
		}else{
			return stack.get(this.size()-1);
		}
	}

	public Boolean isEmpty()
	{
		return stack.isEmpty();
	}

	public Integer size()
	{
		return stack.size();
	}
	
	private Object shouldImplement()
	{
		throw new RuntimeException ("Should Implement");
	}
	
}
