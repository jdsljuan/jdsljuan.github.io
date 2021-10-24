package cancionero.model;

/**
 * Representacion de la tabla Artistas.
 */
public class Artist {

	/**
	 * Identificador del Artista
	 */
	private final int artistID;
	/**
	 * Nombre del Artista
	 */
	private String alias;
	
	/**
	 * Crea un artista desde los campos.
	 * @param pseoudoname Nombre del Artista.
	 * @param ID Identificador del Artista.
	 */
	public Artist(String pseoudoname, int ID) {
		this.alias = pseoudoname;
		this.artistID = ID;
	}
	
	/**
	 * @return the pseudoname
	 */
	public String getPseudoname() {
		return alias;
	}

	/**
	 * @return the artistID
	 */
	public int getArtistID() {
		return artistID;
	}
}
