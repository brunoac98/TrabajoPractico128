package principal;

public class Pronostico {
	
	private String equipo1;
	private String equipo2;
	private String pronostico;
	private String nombre;
	
	public Pronostico() {
		
	}
	
	public Pronostico(String equipo1, String equipo2, String pronostico, String nombre) {
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.pronostico = pronostico;
		this.nombre = nombre;
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
	
	
	
	
}
