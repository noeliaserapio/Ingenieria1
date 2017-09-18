package stack;

public class StackEmptyState implements StackState {
	
	public static final String STACK_EMPTY_DESCRIPTION = "Stack is Empty";

	private Stack context;
	
	public StackEmptyState(Stack aContext) {
		this.context = aContext;
	}

	@Override
	public void push(Object anObject) {
		StackState nuevoEstado = new StackNotEmptyState(context, anObject);
		context.setState(nuevoEstado);	
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
