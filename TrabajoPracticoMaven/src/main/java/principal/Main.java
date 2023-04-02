package principal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		String rutaPronostico = args[0]; //ruta de Pronostico.txt por argumentos -- src/main/java/Pronostico.txt
		String rutaResultado = args[1];	//ruta de Resultado.txt por argumentos   -- src/main/java/Resultado.txt

		List<String> pronosticoLista;
		List<String> resultadoLista;
		List<Pronostico> PronosticosColec = new ArrayList<>();
		List<Persona> PersonaColec = new ArrayList<>();	
		List<Resultado> ResultadoColec = new ArrayList<>();
		
		pronosticoLista = leerArchivo(rutaPronostico);
		PronosticosColec = cargarPronostico(pronosticoLista,PronosticosColec);
		PersonaColec   = cargarPersona(PronosticosColec,PersonaColec);
		
		resultadoLista = leerArchivo(rutaResultado);
		ResultadoColec = cargarResultado(resultadoLista,ResultadoColec);
		
		PersonaColec = calcularPuntaje(ResultadoColec,PronosticosColec,PersonaColec);
		
		System.out.println("------------------------");
		
		for(Persona personaFinal : PersonaColec) {
			System.out.println("NOMBRE: " + personaFinal.getNombre() + " ||PUNTAJE FINAL: " + personaFinal.getPuntaje() + " || PRONOSTICOS ACERTADOS: " + personaFinal.getAcertados());
		}	
		
	}

	
	public static List<String> leerArchivo(String ruta) {
		
		try {
			
			List<String> archivoLista = Files.readAllLines(Paths.get(ruta));
			return archivoLista;
			
		} catch (IOException e) {
			
			System.out.println("Error leyendo archivo.");
			List<String> archivoLista =	new ArrayList<>();
			return archivoLista;
		}
					
	}
	
public static List<Pronostico> cargarPronostico(List<String> pronosticoLista, List<Pronostico> pronosticosColec ) {
		
		String resultado;
		Boolean validacion;
				
		for (String linea : pronosticoLista ) {
			
			Pronostico pronosticoObj = new Pronostico();
			
			String[] pronosticoSplit = linea.split(";");
			
			validacion = pronosticoObj.validarPronostico(pronosticoSplit[2]);
			if (validacion) {
				
				pronosticoObj.setPronostico(pronosticoSplit[2]);
				pronosticoObj.setNombre(pronosticoSplit[3]);
				pronosticoObj.setNroPartido(pronosticoSplit[4]);
				pronosticoObj.setNroDNI(pronosticoSplit[5]);
				pronosticosColec.add(pronosticoObj);
				
			} else {
				System.out.println("El pronostico de " + pronosticoSplit[3] + " DNI: " + pronosticoSplit[5] + " PARTIDO NRO: " + pronosticoSplit[4] + " ES INVALIDO.");
				System.out.println("Se ingreso: '" + pronosticoSplit[2] + "' Valores posibles: 1 (gana equipo 1) - 2 (gana equipo 2) - E (empate)");
			}
	  
		}
		
		return pronosticosColec;
			}

public static List<Resultado> cargarResultado(List<String> resultadoLista, List<Resultado> resultadoColec) {
	
		String resultado;
		
		for (String linea : resultadoLista ) {
			
			Resultado resultadoObj = new Resultado();
			
			Equipo equipo1 = new Equipo();
			Equipo equipo2 = new Equipo();
			
			String[] resultadoSplit = linea.split(";");
			equipo1.setNombre(resultadoSplit[0]);
			equipo2.setNombre(resultadoSplit[1]);
			resultadoObj.setEquipo1(equipo1);
			resultadoObj.setEquipo2(equipo2);
			resultadoObj.setResultado(resultadoSplit[2]);
			resultadoObj.setNroPartido(resultadoSplit[3]);
			
			resultadoColec.add(resultadoObj);
			
			/*
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
			};												*/
	
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
				
				PersonaColec.add(personaObj);
				} 	
	}

	return PersonaColec;
		}

public static List<Persona> calcularPuntaje(List<Resultado> resultadoColec, List<Pronostico> pronosticoColec, List<Persona> personaColec) {
	
	for(Resultado resultado : resultadoColec) {
		
		for(Pronostico pronostico : pronosticoColec) {
			
			if(pronostico.getNroPartido().equals(resultado.getNroPartido())) {
				
				for(Persona persona : personaColec) {
					
					 if(pronostico.getNroDNI().equals(persona.getNroDNI())) {
						 int puntaje = persona.getPuntaje();
						 
						 if(pronostico.getPronostico().equals(resultado.getResultado())) {
							
							 puntaje += 1;
							 persona.setPuntaje(puntaje);
							 persona.addAcertado();
							 
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
	
	
	