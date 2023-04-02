package principal;

import java.util.List;

public class Pronostico {
	
	private String pronostico;
	private String nombre;
	private String nroPartido;
	private String nroDNI;
	
	public Pronostico() {
		
	}
	
	public Pronostico(String pronostico, String nombre, String nroPartido, String nroDNI) {
		this.pronostico = pronostico;
		this.nombre = nombre;
		this.nroPartido = nroPartido;
		this.nroDNI = nroDNI;
	}
	
	public String getPronostico() {
		return pronostico;
	}
	public void setPronostico(String pronostico) {
		this.pronostico = pronostico;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	public String getNroPartido() {
		return nroPartido;
	}
	public void setNroPartido(String nroPartido) {
		this.nroPartido = nroPartido;
	}
	public String getNroDNI() {
		return nroDNI;
	}
	public void setNroDNI(String nroDNI) {
		this.nroDNI = nroDNI;
	}

	public boolean validarPronostico(String pronosticoDato) {
		if(!pronosticoDato.equals("1") && !pronosticoDato.equals("2") && !pronosticoDato.equals("E")) {
			return false;
		} else {
			return true;
		}
	}
	
	
	
}
