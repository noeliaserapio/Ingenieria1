package tusLibros;

public class Cajero {
	
	public static long transactionId = 1;
	private Carrito carrito;
	private TarjetaDeCredito tarjeta;
	private Multiconjunto<Object,Integer> libroDeVentas;
	private MerchantProcessor merch;
	
	public Cajero(Carrito carrito, TarjetaDeCredito tarjeta, Multiconjunto<Object,Integer> libroDeVentas, MerchantProcessor merch){
		this.carrito = carrito;
		this.tarjeta = tarjeta;
		this.libroDeVentas = libroDeVentas;
		this.merch = merch;
	}
	
	public Ticket checkOut(){
		double totalCompra = carrito.checkOut();
		String totalCompraString = String.format("%.2f", totalCompra);
		tarjeta.esValidaParaMerchantProccesor(totalCompraString);
		tarjeta.noEstaVencidaActualmente();
		merch.debitarTarjeta(tarjeta, totalCompraString);
		libroDeVentas.agregarAll(carrito.getProductos());
		carrito.vaciar();
		return new Ticket(++transactionId,totalCompra) ;
	}
	
	public Multiconjunto<Object,Integer> libroDeVentas(){
		return libroDeVentas;
	}

}
