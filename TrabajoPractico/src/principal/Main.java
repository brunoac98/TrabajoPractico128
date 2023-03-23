package principal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		String rutaPronostico = args[0];
		String rutaResultado = args[1];
		//String[] pronosticoSplit;
		List<String> pronosticoLista;
		List<Pronostico> PronosticosColec = new ArrayList<>();
		List<Persona> PersonaColec = new ArrayList<>();
		List<String> resultadoLista;
		List<Resultado> ResultadoColec = new ArrayList<>();
		//String[] resultadoSplit;
		//Resultado resultado = new Resultado();
		//Persona persona = new Persona();
		
		//System.out.println("---------PRONOSTICO--------");
		pronosticoLista = leerArchivo(rutaPronostico);
		PronosticosColec = cargarPronostico(pronosticoLista,PronosticosColec);
		PersonaColec   = cargarPersona(PronosticosColec,PersonaColec);
		//System.out.println("---------RESULTADOS--------");
		
		resultadoLista = leerArchivo(rutaResultado);
		ResultadoColec = cargarResultado(resultadoLista,ResultadoColec);
		
		PersonaColec = calcularPuntaje(ResultadoColec,PronosticosColec,PersonaColec);
		
		System.out.println("------------------------");
		
		for(Persona personaFinal : PersonaColec) {
			System.out.println("NOMBRE: " + personaFinal.getNombre() + " ||PUNTAJE FINAL: " + personaFinal.getPuntaje());
		}	
		
	}

	
	public static List<String> leerArchivo(String ruta) {
		
		try {
			
			List<String> archivoLista = Files.readAllLines(Paths.get(ruta));
			return archivoLista;
			
		} catch (IOException e) {
			
			System.out.println("Error leyendo resultados.");
			List<String> archivoLista =	new ArrayList<>();
			return archivoLista;
		}
					
	}
	
public static List<Pronostico> cargarPronostico(List<String> pronosticoLista, List<Pronostico> pronosticosColec ) {
		
		String resultado;
				
		for (String linea : pronosticoLista ) {
			
			Pronostico pronosticoObj = new Pronostico();
			
			String[] pronosticoSplit = linea.split(";");
			pronosticoObj.setEquipo1(pronosticoSplit[0]);
			pronosticoObj.setEquipo2(pronosticoSplit[1]);
			pronosticoObj.setPronostico(pronosticoSplit[2]);
			pronosticoObj.setNombre(pronosticoSplit[3]);
			pronosticoObj.setNroPartido(pronosticoSplit[4]);
			pronosticoObj.setNroDNI(pronosticoSplit[5]);
			
			pronosticosColec.add(pronosticoObj);
					
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

		//	System.out.println("PRONOSTICO DE: " + pronosticoObj.getNombre() + "\n||PARTIDO " + pronosticoObj.getNroPartido() + " : " + pronosticoObj.getEquipo1() + " VS " + pronosticoObj.getEquipo2() + "\n||EQUIPO GANADOR ESPERADO: " + resultado);			
				  
		}
		
		return pronosticosColec;
			}

public static List<Resultado> cargarResultado(List<String> resultadoLista, List<Resultado> resultadoColec) {
	
		String resultado;
		
		for (String linea : resultadoLista ) {
			
			Resultado resultadoObj = new Resultado();
			
			String[] resultadoSplit = linea.split(";");
			resultadoObj.setEquipo1(resultadoSplit[0]);
			resultadoObj.setEquipo2(resultadoSplit[1]);
			resultadoObj.setResultado(resultadoSplit[2]);
			resultadoObj.setNroPartido(resultadoSplit[3]);
			
			resultadoColec.add(resultadoObj);
	
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
	
		//System.out.println("||PARTIDO " + resultadoObj.getNroPartido()  + " : " + resultadoObj.getEquipo1() + " VS " + resultadoObj.getEquipo2() + "\n||EQUIPO GANADOR: " + resultado);
		}
		
		return resultadoColec;
		

	}

public static List<Persona> cargarPersona(List<Pronostico> PronosticoColec, List<Persona> PersonaColec) {
	
	
	for(Pronostico pronosticoObj : PronosticoColec) {
		
		boolean encontrado = false;
		 
		for(Persona personaObjL : PersonaColec) {
			if(personaObjL.getNroDNI().equals(pronosticoObj.getNroDNI())) {	
				encontrado=true;
				break;
			}		
		}
		
			if(!encontrado) {
					
				Persona personaObj = new Persona();
				personaObj.setNombre(pronosticoObj.getNombre());
				personaObj.setNroDNI(pronosticoObj.getNroDNI());
				personaObj.setPuntaje(0);
				
				PersonaColec.add(personaObj);
				} 	
	}

	return PersonaColec;
		}

public static List<Persona> calcularPuntaje(List<Resultado> resultadoColec, List<Pronostico> pronosticoColec, List<Persona> personaColec) {
	
	//String resultado;
	//String pronostico;
	
	for(Resultado resultado : resultadoColec) {
		
		for(Pronostico pronostico : pronosticoColec) {
			
			if(pronostico.getNroPartido().equals(resultado.getNroPartido())) {
				
				for(Persona persona : personaColec) {
					
					 if(pronostico.getNroDNI().equals(persona.getNroDNI())) {
						 int puntaje = persona.getPuntaje();
						 
						 if(pronostico.getPronostico().equals(resultado.getResultado())) {
							
							 puntaje += 1;
							 persona.setPuntaje(puntaje);
						 } else {
							 
							 puntaje -= 1;
							 persona.setPuntaje(puntaje);
						 }
						 persona.setPuntaje(puntaje);
					 }
					
				}
				
			}
			
		}
		
	}
	
	return personaColec;

}
}
	
	
	