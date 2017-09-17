package stack;

public class WrapperObject {
	

	private Object objeto;
	private StackState stateAnterior;
	
	public WrapperObject(Object objeto, StackState stateAnterior) {
		this.objeto = objeto;
		this.stateAnterior = stateAnterior;
	}

	public Object getObjeto() {
		return objeto;
	}

	public StackState getStateAnterior() {
		return stateAnterior;
	}
	

}
