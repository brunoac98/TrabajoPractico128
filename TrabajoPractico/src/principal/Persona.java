package principal;

public class Persona {
	
	private String nombre;
	private int puntaje;
	private String nroDNI;
	
	public Persona() {
		
	}
	
	public Persona(String nombre, int puntaje, String nroDNI) {
		this.nombre = nombre;
		this.puntaje = puntaje;
		this.nroDNI = nroDNI;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	public String getNroDNI() {
		return nroDNI;
	}
	public void setNroDNI(String nroDNI) {
		this.nroDNI = nroDNI;
	}
	
	
}
