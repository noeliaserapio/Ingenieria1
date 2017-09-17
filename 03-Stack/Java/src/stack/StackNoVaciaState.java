package stack;

import java.util.ArrayList;
import java.util.List;

public class StackNoVaciaState implements StackState{

	private List<WrapperObject> elements;
	private Stack contexto;
	
	public StackNoVaciaState(Stack contexto, Object obj) {
		this.elements = new ArrayList<WrapperObject>();
		this.elements.add(new WrapperObject(obj, new StackVaciaState(contexto)));
		this.contexto = contexto;		
	}
	
	@Override
	public void push(Object anObject) {
		elements.add(new WrapperObject(anObject, this));
	}

	@Override
	public Object pop() {
		contexto.setState(elements.get(this.size()-1).getStateAnterior());
		return elements.remove(this.size()-1).getObjeto();
	}

	@Override
	public Object top() {
		return elements.get(this.size()-1).getObjeto();
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
