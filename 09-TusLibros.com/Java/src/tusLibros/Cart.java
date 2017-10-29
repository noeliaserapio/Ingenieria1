package tusLibros;

import java.util.ArrayList;
import java.util.List;


public class Cart {
	
	private List<Object> libros = new ArrayList<Object>();
	
	public boolean isEmpty(){
		return libros.isEmpty();
	}
	
}
