package principal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

	private static int extraAcertados = 0;
	private static String ruta = "";
	private static String usuario = "";
	private static String pass = "";
	
	public static void main(String[] args) {
		
		String datosConfig = args[0]; //ruta de Configuracion.txt por argumentos -- src/main/java/Configuracion.txt
		String rutaResultado = args[1];	//ruta de Resultado.txt por argumentos   -- src/main/java/Resultado.txt
		List<String> resultadoLista;
		List<Persona> PersonaColec = new ArrayList<>();	
		List<Resultado> ResultadoColec = new ArrayList<>();
			
		try {
				
			for(String linea : Files.readAllLines(Paths.get(datosConfig), StandardCharsets.ISO_8859_1)){
					
				String[] configSplit = linea.split(";");
				ruta = configSplit[0];
				usuario = configSplit[1];
				pass = configSplit[2];
				extraAcertados = Integer.parseInt(configSplit[3]);
					
			}	
							
		} catch (IOException e) {
					
					System.out.println("Error leyendo archivo de configuracion.");
		}
				
		PersonaColec   = cargarPersona(PersonaColec);
			
		resultadoLista = leerArchivo(rutaResultado);
		ResultadoColec = cargarResultado(resultadoLista,ResultadoColec);
			
		PersonaColec = calcularPuntaje(ResultadoColec,PersonaColec);
			
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
				pronosticoObj.setNroRonda(pronosticoSplit[6]);
				pronosticosColec.add(pronosticoObj);
					
			} else {
				System.out.println("El pronostico de " + pronosticoSplit[3] + " ||DNI: " + pronosticoSplit[5] + " ||RONDA NRO: " + pronosticoSplit[6] + " ||PARTIDO NRO: " + pronosticoSplit[4] + " ES INVALIDO.");
				System.out.println("Se ingreso resultado esperado: '" + pronosticoSplit[2] + "' - Valores posibles: 1 (gana equipo 1) - 2 (gana equipo 2) - E (empate)");
			}
		  
		}
			
		return pronosticosColec;
	}

	public static List<Resultado> cargarResultado(List<String> resultadoLista, List<Resultado> resultadoColec) {
	
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
			resultadoObj.setNroRonda(resultadoSplit[3]);
			resultadoObj.setNroPartido(resultadoSplit[4]);
			
			resultadoColec.add(resultadoObj);
		}
		
		return resultadoColec;
		
	}

	public static List<Persona> cargarPersona(List<Persona> PersonaColec) {
	
		int contPersonas = 0;
	
		try{  

			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection(ruta,usuario,pass);  // IP/nombre_db --- usuario --- contraseña
			Statement stmt=con.createStatement();  

			ResultSet rs=stmt.executeQuery("select * from pronosticos");  
			while(rs.next()) { 
			
				boolean encontrado = false;
				 
				for(Persona personaObjL : PersonaColec) {
					if(personaObjL.getNroDNI().equals(rs.getString(1))) {	
						
						encontrado=true;
						break;
					}		
				}
				
				if(!encontrado) {
							
					Persona personaObj = new Persona();
					personaObj.setNombre(rs.getString(2));
					personaObj.setNroDNI(rs.getString(1));
				
					PersonaColec.add(personaObj);
					contPersonas += 1;

				} 	
			 
			}

			con.close();  

			} catch(Exception e){ 
				System.out.println(e);
			}
	
		for(Persona persona : PersonaColec) {
			try{  
			
				int cantPronosticos = 0;
			
				Class.forName("org.postgresql.Driver");  
				Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/curso","curso","123");  // IP/nombre_db --- usuario --- contraseña
				Statement stmt=con.createStatement();  

				ResultSet rs=stmt.executeQuery("select * from pronosticos where dni = '" + persona.getNroDNI() + "'");  
			
				while(rs.next()) { 
					cantPronosticos += 1;								 
				}

				persona.setPronosticos(cantPronosticos);
			
				con.close();  

				} catch(Exception e){ 
					System.out.println(e);
				}
		}	
	
		System.out.println("Personas cargadas en total: " + contPersonas);
		return PersonaColec;
	}

	public static List<Persona> calcularPuntaje(List<Resultado> resultadoColec, List<Persona> personaColec) {
	
		int pronosticoCont = 0;
		for(Resultado resultado : resultadoColec) {
		
			try{  

				Class.forName("org.postgresql.Driver");  
				Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/curso","curso","123");  // IP/nombre_db --- usuario --- contraseña
				Statement stmt=con.createStatement();   

				ResultSet rs=stmt.executeQuery("select * from pronosticos where nropartido = '" + resultado.getNroPartido() + "' and ronda = '" + resultado.getNroRonda() + "'" );  
				
				while(rs.next()) { 
				
					pronosticoCont += 1;
					Pronostico pronosticoObj = new Pronostico();
				
					boolean validacion = pronosticoObj.validarPronostico(rs.getString(3));
					if (validacion) {
					
						for(Persona persona : personaColec) {
						
							// Si el DNI de la persona (proveniente de la tabla) es igual al Objeto PERSONA en memoria calculo el puntaje, sino sigo recorriendo la coleccion "personaColec" buscando a otra persona.
							if(rs.getString(1).equals(persona.getNroDNI())) {
								int puntaje = persona.getPuntaje();
							 		 
								// Si el pronostico de la persona es igual sumo un punto, sino resto uno.
								if(rs.getString(3).equals(resultado.getResultado())) {
								
									puntaje += 1;
									persona.setPuntaje(puntaje);
									persona.addAcertado();
								 
								} else {
								 
									puntaje -= 1;
									persona.setPuntaje(puntaje);
								}
							 
								// Guardo el puntaje
								persona.setPuntaje(puntaje);	 
							}
						
						}
					
					} else {
						System.out.println("El pronostico de " + rs.getString(2) + " ||DNI: " + rs.getString(1) + " ||RONDA NRO: " + rs.getString(5) + " ||PARTIDO NRO: " + rs.getString(4) + " ES INVALIDO.");
						System.out.println("Se ingreso el pronostico: '" + rs.getString(3) + "' - Valores posibles: 1 (gana equipo 1) - 2 (gana equipo 2) - E (empate)");
					}
				}

				con.close();  

				} catch(Exception e){ 
					System.out.println(e);
				}  
		
		}
	
		for(Persona persona : personaColec) {
		
			if(persona.getAcertados() == persona.getPronosticos()) {
				persona.setPuntaje(persona.getPuntaje() + extraAcertados);
			}
		
		}
	
		System.out.println("Pronosticos leidos: " + pronosticoCont);
		return personaColec;

  }


	public static void cargarPronostico(List<Pronostico> colecPronosticos) {
	
		try{  

			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/curso","curso","123");  // IP/nombre_db --- usuario --- contraseña
			Statement stmt=con.createStatement();  
			System.out.println("conecto");  

			ResultSet rs=stmt.executeQuery("select * from pronosticos");  
			
			while(rs.next()) { 
			
				System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5));  
			
				Pronostico pronosticoObj = new Pronostico();
			
				boolean validacion = pronosticoObj.validarPronostico(rs.getString(3));
				if (validacion) {
				
					pronosticoObj.setPronostico(rs.getString(3));
					pronosticoObj.setNombre(rs.getString(2));
					pronosticoObj.setNroPartido(rs.getString(4));
					pronosticoObj.setNroDNI(rs.getString(1));
					pronosticoObj.setNroRonda(rs.getString(5));
					colecPronosticos.add(pronosticoObj);
				
				} else {
					System.out.println("El pronostico de " + rs.getString(2) + " ||DNI: " + rs.getString(1) + " ||RONDA NRO: " + rs.getString(5) + " ||PARTIDO NRO: " + rs.getString(4) + " ES INVALIDO.");
					System.out.println("Se ingreso el pronostico: '" + rs.getString(3) + "' - Valores posibles: 1 (gana equipo 1) - 2 (gana equipo 2) - E (empate)");
				}
			}

			con.close();  

			} catch(Exception e){ 
				System.out.println(e);
			}  
		


	}

}
	
	
	