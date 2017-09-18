package stack;

public class WrapperObject {
	
	private Object object;
	private StackState previousState;
	
	public WrapperObject(Object anObject, StackState aPreviousState) {
		this.object = anObject;
		this.previousState = aPreviousState;
	}

	public Object getObjet() {
		return object;
	}

	public StackState getPreviousState() {
		return previousState;
	}
	
}
