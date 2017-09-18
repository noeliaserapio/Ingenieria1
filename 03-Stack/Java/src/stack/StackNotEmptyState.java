package stack;

import java.util.ArrayList;
import java.util.List;

public class StackNotEmptyState implements StackState{

	private List<WrapperObject> elements;
	private Stack context;
	
	public StackNotEmptyState(Stack aContext, Object anObject) {
		this.elements = new ArrayList<WrapperObject>();
		this.elements.add(new WrapperObject(anObject, new StackEmptyState(aContext)));
		this.context = aContext;		
	}
	
	@Override
	public void push(Object anObject) {
		elements.add(new WrapperObject(anObject, this));
	}

	@Override
	public Object pop() {
		context.setState(elements.get(this.size()-1).getPreviousState());
		return elements.remove(this.size()-1).getObjet();
	}

	@Override
	public Object top() {
		return elements.get(this.size()-1).getObjet();
	}

	@Override
	public Boolean isEmpty() {
		return false;
	}

	@Override
	public Integer size() {
		return new Integer(elements.size());	
	}

}
