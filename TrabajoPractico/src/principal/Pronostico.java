package principal;

public class Pronostico {
	
	private String equipo1;
	private String equipo2;
	private String pronostico;
	private String nombre;
	private String nroPartido;
	private String nroDNI;
	
	public Pronostico() {
		
	}
	
	public Pronostico(String equipo1, String equipo2, String pronostico, String nombre, String nroPartido, String nroDNI) {
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.pronostico = pronostico;
		this.nombre = nombre;
		this.nroPartido = nroPartido;
		this.nroDNI = nroDNI;
	}
	
	public String getEquipo1() {
		return equipo1;
	}
	public void setEquipo1(String equipo1) {
		this.equipo1 = equipo1;
	}
	public String getEquipo2() {
		return equipo2;
	}
	public void setEquipo2(String equipo2) {
		this.equipo2 = equipo2;
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

	
	
	
}
