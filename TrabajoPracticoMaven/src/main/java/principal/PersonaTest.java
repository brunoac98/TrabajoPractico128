package principal;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonaTest {
	Persona objPersonaTest;
	
	@BeforeEach
	public void setUp() {
		objPersonaTest = new Persona(); 			

	}
	
	@Test
	void testPuntajeCero() {					//Cuando inicializo un objeto Persona puntaje debe valer 0 siempre.
		int puntaje = objPersonaTest.getPuntaje();		
		int puntajeInicial = 0;
		boolean resultado = (puntaje == puntajeInicial);
		assertTrue(resultado);
	}
	
	@Test
	void testAddAcertado() {
		objPersonaTest.addAcertado();
		int acertados = objPersonaTest.getAcertados();
		int acertadosTotal = 1;
		boolean resultado = (acertados == acertadosTotal);
		assertTrue(resultado);
	}
	
	
}
