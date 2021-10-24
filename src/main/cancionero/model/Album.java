package cancionero.model;

/**
 * Representacion de la tabla Albums.
 */
public class Album {
	
	/**
	 * Nombre del Album.
	 */
	private String name;
	/**
	 * Identificador del Artista.
	 */
	private int artistID;
	/**
	 * Identificador del Album.
	 */
	private final int albumID;

	/**
	 * Crea un nuevo album.
	 * @param name Nombre del album.
	 * @param albumID ID del Album al que pertenece.
	 * @param artistID ID del Artista al que pertenece.
	 */
	public Album(String name, int albumID, int artistID) {
		this.name = name;
		this.albumID = albumID;
		this.setArtistID(artistID);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the albumID
	 */
	public int getAlbumID() {
		return albumID;
	}

	/**
	 * @return the artistID
	 */
	public int getArtistID() {
		return artistID;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param artistID the artistID to set
	 */
	public void setArtistID(int artistID) {
		this.artistID = artistID;
	}
	
	

}
