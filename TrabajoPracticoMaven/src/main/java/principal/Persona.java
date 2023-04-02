package principal;

public class Persona {
	
	private String nombre;
	private String nroDNI;
	private int puntaje;
	private int acertados;

	public Persona() {
		
	}
	
	public Persona(String nombre, String nroDNI) {
		this.nombre = nombre;
		this.puntaje = 0;
		this.nroDNI = nroDNI;
		this.acertados = 0;
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
	public int getAcertados() {
		return acertados;
	}
	public void setAcertados(int acertados) {
		this.acertados = acertados;
	}
	
	public void addAcertado() {
		this.acertados += 1;
	}
	
	
}
