package stack;

public class StackVaciaState implements StackState{
	
	public static final String STACK_EMPTY_DESCRIPTION = "Stack is Empty";

	private Stack contexto;
	
	public StackVaciaState(Stack contexto) {
		this.contexto = contexto;
	}

	@Override
	public void push(Object anObject) {
		StackState nuevoEstado = new StackNoVaciaState(contexto, anObject);
		contexto.setState(nuevoEstado);	
	}

	@Override
	public Object pop() {
		throw new RuntimeException (STACK_EMPTY_DESCRIPTION);
	}

	@Override
	public Object top() {
		throw new RuntimeException (STACK_EMPTY_DESCRIPTION);
	}

	@Override
	public Boolean isEmpty() {
		return true;
	}

	@Override
	public Integer size() {
		return new Integer(0);
	}
	

}
