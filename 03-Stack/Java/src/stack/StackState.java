package stack;

public interface StackState {
	
	public void push (Object anObject);
	
	public Object pop();
	
	public Object top();
	
	public Boolean isEmpty();
	
	public Integer size();

}
