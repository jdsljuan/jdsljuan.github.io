package cancionero.control;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EngineTest extends Engine {

	private static Engine engine;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//engine = new Engine();
	}

	
	@Test
	@DisplayName("Conexion correcta con el controlador de la Base de Datos.")
	void testRequestViewModelUpdate() {
		assumeTrue(true); //TODO
	}
	
	@Test
	@DisplayName("Creacion correcta de las ventana principal.")
	void testRequestViewDeleteSong() {
		assumeTrue(true); //TODO
	}

	@Test
	@DisplayName("Integridad de los Datos al ser manipulados.")
	void testRequestViewFillEditorArtistCombo() {
		assertEquals(0, 1);
	}

	@Test
	@DisplayName("Modificacion del Modelo de la vista.")
	void testRequestViewEditorSaveRecord() {
		assumeTrue(true); // TODO
	}
	
	@Test
	@DisplayName("Chekeo de la pila de Eventos ante Elementos nulos.")
	void testRequestViewFillEditorAlbum() {
		assumeTrue(true); // TODO
	}
	
	@Test
	@DisplayName("Duplicidad de instancias.")
	void testRequestView() {
		assumeTrue(true); // TODO
	}

}
