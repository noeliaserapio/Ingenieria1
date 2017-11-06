package tusLibros;

public class Ticket {
	

	private long numeroTransaccion;
	private double totalCompra;
	
	public Ticket(long numeroTransaccion, double totalCompra) {
		this.numeroTransaccion = numeroTransaccion;
		this.totalCompra = totalCompra;
	}
	
	public long getNumeroTransaccion() {
		return numeroTransaccion;
	}
	public double getTotalCompra() {
		return totalCompra;
	}
	

}
