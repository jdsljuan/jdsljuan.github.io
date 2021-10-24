package cancionero.model;

/**
 * Representacion de la tabla canciones.
 */
public class Song {
	
	/**
	 * Identificador de la cancion.
	 */
	private final int songID;
	/**
	 * Nombre de la cancion.
	 */
	private String name;
	/**
	 * Acordes de la cancion.
	 */
	private String chords;
	/**
	 * Ritmo de la cancion.
	 */
	private String rhythm;
	/**
	 * Identificador del Album.
	 */
	private int albumID;
	/**
	 * Tonalidad Base de la cancion.
	 */
	private String baseChord;
	/**
	 * Tonalidad Favorita de la cancion.
	 */
	private String favChord;
	
	/**
	 * Constructor para una cancion existente.
	 * 
	 * @param name Nombre de la cancion.
	 * @param chords Acordes de la cancion.
	 * @param rhythm Ritmo de la cancion.
	 * @param songID Identificador unico de la cancion.
	 * @param albumID Identificador unico de la cancion.
	 * */
	public Song(String name, String chords, String rhythm, int songID, int albumID) {
		super();
		this.name = name;
		this.chords = chords;
		this.albumID = albumID;
		this.songID = songID;
		this.rhythm = rhythm;
	}

	/**
	 * @return the songID
	 */
	public int getSongID() {
		return songID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the chords
	 */
	public String getChords() {
		return chords;
	}

	/**
	 * @return the rhythm
	 */
	public String getRhythm() {
		return rhythm;
	}

	/**
	 * @return the albumID
	 */
	public int getAlbumID() {
		return albumID;
	}

	/**
	 * @return the baseChord
	 */
	public String getBaseChord() {
		return baseChord;
	}

	/**
	 * @return the favChord
	 */
	public String getFavChord() {
		return favChord;
	}
	
	
}
