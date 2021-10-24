package cancionero.control;

import javax.swing.SwingUtilities;

/**
 * Inicia el nuevo proceso de manera segura.
 * y crea la instancia del engine fuera del proceso
 * principal.
 * 
 * Esta clase se encargara de lanzar el engine desde el modo consola.
 * Dando asi una interfaz CLI.
 */
public class Launcher {
	
	/**
	 * Instancia que inicia el programa en si
	 * de manera visual.
	 */
	static Engine engine;
	
	/**
	 * Constructor vacio. 
	 */
	public Launcher() {
		
	}
	
	/**
	 * Punto de entrada del programa y resolucion de argumentos.
	 * 
	 * @param args Lista de parametros por CLI.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				engine  = new Engine();
			}
		});
	}

}
