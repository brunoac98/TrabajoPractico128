package principal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {

	public static void main(String[] args) {

		String rutaPronostico = args[0];
		String rutaResultado = args[1];
		String[] pronosticoSplit;
		String[] resultadoSplit;
		Pronostico pronostico = new Pronostico();
		Resultado resultado = new Resultado();
		Persona persona = new Persona();
		
		System.out.println("---------PRONOSTICO--------");
		pronosticoSplit = leerArchivo(rutaPronostico);
		pronostico = cargarPronostico(pronostico,pronosticoSplit);
		persona    = cargarPersona(pronostico,persona);
		
		System.out.println("---------RESULTADOS--------");
		
		resultadoSplit = leerArchivo(rutaResultado);
		resultado = cargarResultado(resultado,resultadoSplit);
		
		persona = calcularPuntaje(resultado,pronostico,persona);
		
		System.out.println("------------------------");
		
		System.out.println("NOMBRE: " + persona.getNombre() + " ||PUNTAJE FINAL: " + persona.getPuntaje());
		
		
	}

	
	public static String[] leerArchivo(String ruta) {
		
		try {
			for (String pronostico : Files.readAllLines(Paths.get(ruta))) {
				
				String[] pronosticoSplit = pronostico.split(";");
				
				return pronosticoSplit;				
			}
		} catch (IOException e) {
			
			System.out.println("Error leyendo resultados.");
			
		}
			
		return new String[0];
		
	}
	
public static Pronostico cargarPronostico(Pronostico pronosticoObj, String[] pronosticoSplit) {
		
		String resultado;
				
		pronosticoObj.setEquipo1(pronosticoSplit[0]);
		pronosticoObj.setEquipo2(pronosticoSplit[1]);
		pronosticoObj.setPronostico(pronosticoSplit[2]);
		pronosticoObj.setNombre(pronosticoSplit[3]);
				
		switch(pronosticoObj.getPronostico()) {
		case "1":
			resultado = pronosticoObj.getEquipo1();
			break;
		case "2":	
			resultado = pronosticoObj.getEquipo2();
			break;
		case "E":	
			resultado = "Ninguno (empate)";
			break;
		default:
			resultado = "Error al cargar pronostico";
				};

		System.out.println("PRONOSTICO DE: " + pronosticoObj.getNombre() + " ||PARTIDO: " + pronosticoObj.getEquipo1() + " VS " + pronosticoObj.getEquipo2() + "\n||EQUIPO GANADOR ESPERADO: " + resultado);			
				
		return pronosticoObj;
			}

public static Resultado cargarResultado(Resultado resultadoObj, String[] resultadoSplit) {
	
		String resultado;
		
		resultadoObj.setEquipo1(resultadoSplit[0]);
		resultadoObj.setEquipo2(resultadoSplit[1]);
		resultadoObj.setResultado(resultadoSplit[2]);
	
		switch(resultadoObj.getResultado()) {
		case "1":
			resultado = resultadoObj.getEquipo1();
			break;
		case "2":	
			resultado = resultadoObj.getEquipo2();
			break;
		case "E":	
			resultado = "Ninguno (empate)";
			break;
		default:
			resultado = "Error al cargar pronostico";
		};
	
		System.out.println("||PARTIDO: " + resultadoObj.getEquipo1() + " VS " + resultadoObj.getEquipo2() + "\n||EQUIPO GANADOR: " + resultado);
		
		return resultadoObj;
	
	}

public static Persona cargarPersona(Pronostico pronosticoObj, Persona personaObj) {
	
	personaObj.setNombre(pronosticoObj.getNombre());
	personaObj.setPuntaje(0);		

	return personaObj;
		}

public static Persona calcularPuntaje(Resultado resultadoObj, Pronostico pronosticoObj, Persona personaObj) {
	
	String resultado;
	String pronostico;
	
	resultado = resultadoObj.getResultado();
	pronostico = pronosticoObj.getPronostico();
	
	if (resultado.equals(pronostico)){
		
		personaObj.setPuntaje(personaObj.getPuntaje() + 1);
		
	} else {
		
		personaObj.setPuntaje(personaObj.getPuntaje() - 1);
		
	}
	
	return personaObj;

}
}
	
	
	