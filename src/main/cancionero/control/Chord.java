package cancionero.control;

/**
 * Esta clase representa los acordes que pueden ser usados en la notacion
 * para guitarra de acompanamiento y sus diferentes modificadores.
 * */
public class Chord {
	
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String C_BEMOL = "Cb";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String C = "C";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String C_SHARP = "C#";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String D_BEMOL = "Db";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String D = "D";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String D_SHARP = "D#";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String E_BEMOL = "Eb";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String E = "E";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String F_BEMOL = "Fb";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String F = "F";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String F_SHARP = "F#";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String G_BEMOL = "Gb";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String G = "G";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String G_SHARP = "G#";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String A_BEMOL = "Ab";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String A = "A";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String A_SHARP = "A#";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String B_BEMOL = "Bb";
	/**
	 * Contiene la cadena que indica su nombre.
	 * */
	public static final String B = "B";
	
	/**
	 * Calificador de la nota menor
	 * */
	public static final String MENOR_QUALITY = "m";
	/**
	 * Calificador de la nota Septima
	 * */
	public static final String SEVENTH_QUALITY = "7";
	/**
	 * Calificador de la nota Quinta
	 * */
	public static final String FIFTH_QUALITY = "5";
	/**
	 * Calificador sin calificador.
	 * */
	public static final String NO_QUALITY = "?";

	/**
	 * Devuel un arreglo con los acordes naturales.
	 * @return String Arreglo de Cadena con los acordes naturales
	 * representados como campos de esta clase.
	 * */
	public static String[] getNaturalChords() {
		String[] aux = {C,D,E,F,G,A,B};
		return aux;
	}
	
	/**
	 * Devuel un arreglo con todos los acordes.
	 * @return String Arreglo de Cadena con los acordes
	 * representados como campos de esta clase.
	 * */
	public static String[] getAllChords() {
		String[] aux = {C,D,E,F,G,A,B,C_BEMOL,D_BEMOL,E_BEMOL,F_BEMOL,G_BEMOL,A_BEMOL,B_BEMOL,
				C_SHARP,D_SHARP,F_SHARP,G_SHARP,A_SHARP};
		return aux;
	}
}
