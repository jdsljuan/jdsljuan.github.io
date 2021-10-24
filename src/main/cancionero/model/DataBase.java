package cancionero.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import cancionero.control.StringValue;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * La clase que se va a encargar de generar la conexion con la base de datos. Esta clase tambien provee campos para que se hagan solicitudes
 * por el controlador, de manera que las ejecuciones de las sentencias se haga de manera correcta.
 * 
 * Esto supone una inyeccion de SQL que puede generar errores y riesgos de integridad en la base de datos asi que deben
 * usarse con cautela.
 * */
public class DataBase {

	/**
	 * Objeto que maneja la conexion con el Driver JDBC.
	 * Usado por el generador de reportes y la base misma.
	 * */
	private Connection con = null;
	/**
	 * Se encargar de hacer todas las ejecuciones de sentencias
	 * SQL y devolver los resultados si es el caso.
	 * */
	private Statement stm = null;
	
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String DEFAULT_ALBUM_NAME = "unknown";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String DEFAULT_ARTIST_NAME = "unknown";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	private static final String SONGTABLE = "tsong";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String SONGTABLE_CID = "cID";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String SONGTABLE_CNAME = "cname";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String SONGTABLE_CCHORDS = "cchords";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String SONGTABLE_CRHYTHM = "crhythm";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String SONGTABLE_CBSCHORD = "cbasechord";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String SONGTABLE_CFVCHORD = "cfavchord";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	private static final String ALBUMTABLE = "talbum";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String ALBUMTABLE_DID = "dID";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String ALBUMTABLE_DNAME = "dname";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String ALBUMTABLE_DDATE = "ddate";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String ALBUMTABLE_DTRACKNUMER = "dtracknumber";
	
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	private static final String ARTISTTABLE = "tartist";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String ARTISTTABLE_AID = "aID";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String ARTISTTABLE_AALIAS = "aalias";
	/**
	 * Atrubuto de las tablas o valor por defecto.
	 * */
	public static final String ARTISTTABLE_AREALNAME = "arealname";

	/**
	 * Crea un objeto vacio, la base de datos debe inicializarce 
	 * usando el metodo connect();
	 * */
	public DataBase() {
		
	}
	/**
	 * Verifica la integridad de las tablas dentro de la base de datos.
	 * @return true Si el Statement y la Connection son creados, null 
	 * de otro modo.
	 * */
	public boolean connect(){
		try {
			//Cambiar el valor del nombre de la base de datos por PRODUCTIVIDAD si es necesario.
			con = DriverManager.getConnection("jdbc:sqlite:"+ StringValue.DATABASE_FILE_NAME_DEVELOP + ".db" );
			stm = con.createStatement();
			stm.setQueryTimeout(30);
		
		} catch (SQLException e) {
			System.out.print(e);
			return false;
		}finally {
			// TODO Implement good ending.
		}
		//Retorna verdadero si ambas instancias son no null.
		return (con != null && stm != null) ? true : false;
	}
	
	/**
	 * Verifica la integridad de las tablas dentro de la base de datos.
	 * @return true Si el esquema es correcto.
	 * */
	public boolean checkSchema() {
		//Activa las claves foraneas en SQLite
		String pragmaSetup = "PRAGMA foreign_keys = 1;";
		
		String tableArtist = "CREATE TABLE IF NOT EXISTS "+DataBase.ARTISTTABLE+"("+DataBase.ARTISTTABLE_AID+" 		 INTEGER NOT NULL PRIMARY KEY UNIQUE, "
															  	  				   +DataBase.ARTISTTABLE_AALIAS+"	 TEXT NOT NULL DEFAULT '"+DataBase.DEFAULT_ARTIST_NAME+"');";
		
		String tableAlbum  = "CREATE TABLE IF NOT EXISTS "+DataBase.ALBUMTABLE+"("+ DataBase.ALBUMTABLE_DID+" 	 	  INTEGER NOT NULL PRIMARY KEY UNIQUE, "
															 					  + DataBase.ALBUMTABLE_DNAME +" 	  TEXT NOT NULL DEFAULT '"+DataBase.DEFAULT_ALBUM_NAME+"', "
															 					  + DataBase.ARTISTTABLE_AID+" 		  INTEGER NOT NULL, "
															 					  + "FOREIGN KEY ("+DataBase.ARTISTTABLE_AID+") REFERENCES "+DataBase.ARTISTTABLE+"("+DataBase.ARTISTTABLE_AID+"));";
		
		String tableSong   = "CREATE TABLE IF NOT EXISTS "+DataBase.SONGTABLE+"("+DataBase.SONGTABLE_CID+"     INTEGER NOT NULL PRIMARY KEY UNIQUE, "
																				+ DataBase.SONGTABLE_CNAME+"   TEXT NOT NULL ,"
																				+ DataBase.SONGTABLE_CCHORDS+" TEXT NOT NULL, "
																				+ DataBase.SONGTABLE_CBSCHORD+" TEXT NOT NULL,"
																				+ DataBase.SONGTABLE_CFVCHORD+" TEXT NOT NULL,"
																				+ DataBase.SONGTABLE_CRHYTHM+" TEXT NOT NULL, "
																				+ DataBase.ALBUMTABLE_DID+"     INTEGER NOT NULL, "
																				+ "FOREIGN KEY ("+DataBase.ALBUMTABLE_DID+") REFERENCES "+DataBase.ALBUMTABLE+"("+DataBase.ALBUMTABLE_DID+"));";
		
		try {
			stm.executeUpdate(pragmaSetup);
			stm.executeUpdate(tableArtist);
			stm.executeUpdate(tableAlbum);
			stm.executeUpdate(tableSong);
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Obtiene todos los registros en la base de datos dentro de la tabla de
	 * canciones.
	 * @return Devuelve una lista de array de objetos que contiene.
	 * */
	public ArrayList<Song> getSongRecords(){
		String tmpStatement = "SELECT * FROM "+DataBase.SONGTABLE+";";
		ArrayList<Song> array = new ArrayList<Song>();
		try {
			ResultSet rs = stm.executeQuery(tmpStatement);
			while(rs.next()) {
				Song obj = new Song(rs.getString(DataBase.SONGTABLE_CNAME),
									rs.getString(DataBase.SONGTABLE_CCHORDS),
									rs.getString(DataBase.SONGTABLE_CRHYTHM),
									rs.getInt(DataBase.SONGTABLE_CID),
									rs.getInt(DataBase.ALBUMTABLE_DID));
				array.add(obj);
			}
			return array;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Obtiene todos los registros en la base de datos dentro de la tabla de
	 * albumes.
	 * @param artistName Nombre del artista que se va buscar.
	 * @return Devuelve una lista de array de objetos que contiene .
	 * */
	public ArrayList<Object[]> getAlbumRecords(String artistName){
		int artistID = this.checkArtist(artistName);
		String tmpStatement = "SELECT * FROM "+DataBase.ALBUMTABLE+" WHERE "+DataBase.ARTISTTABLE_AID+"='"+artistID+"';";
		ArrayList<Object[]> array = new ArrayList<Object[]>();
		try {
			ResultSet rs = stm.executeQuery(tmpStatement);
			while(rs.next()) {
				Object[] obj = {rs.getString(DataBase.ALBUMTABLE_DNAME),
								rs.getInt(DataBase.ALBUMTABLE_DID)};
				array.add(obj);
			}
			return array;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Obtiene todos los registros en la base de datos dentro de la tabla de
	 * artistas.
	 * @return Devuelve una lista de array de objetos que contiene .
	 * */
	public ArrayList<Object[]> getArtistRecords(){
		String tmpStatement = "SELECT * FROM "+DataBase.ARTISTTABLE+";";
		ArrayList<Object[]> array = new ArrayList<Object[]>();
		try {
			ResultSet rs = stm.executeQuery(tmpStatement);
			while(rs.next()) {
				Object[] obj = {rs.getString(DataBase.ARTISTTABLE_AALIAS),
								rs.getInt(DataBase.ARTISTTABLE_AID)};
				array.add(obj);
			}
			return array;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Verifica la existencia de un artista.
	 * @param name Nombre del Artista a verificar.
	 * @return -1 si no existe de lo contrario el dID
	 * */
	private int checkArtist(String name) {
		String tmpstm = "SELECT "+DataBase.ARTISTTABLE_AALIAS+","+DataBase.ARTISTTABLE_AID+" FROM "+DataBase.ARTISTTABLE+" WHERE "+DataBase.ARTISTTABLE_AALIAS+"='"+name+"';";
		try {
			ResultSet rs = stm.executeQuery(tmpstm);
			return rs.next() ? rs.getInt("aID"): -1;
		} catch (SQLException e) {
			return -1;
		}
	}
	
	/**
	 * Verifica la existencia de una cancion.
	 * @param name Nombre de la cancion a verificar.
	 * @param dID Identificador del Album.
	 * @return -1 si no existe de lo contrario el dID
	 * */
	private int checkSong(String name, int dID) {
		String tmpstm = "SELECT "+DataBase.SONGTABLE_CNAME+","+DataBase.SONGTABLE_CID+" FROM "+DataBase.SONGTABLE+" WHERE "+DataBase.SONGTABLE_CNAME+"='"+name+"' AND "+DataBase.ALBUMTABLE_DID+"='"+dID+"';";
		try {
			ResultSet rs = stm.executeQuery(tmpstm);
			return rs.next() ? rs.getInt("cID"): -1;
		} catch (SQLException e) {
			return -1;
		}
	}
	
	/**
	 * Verifica la existencia de un album.
	 * @param name Nombre del Album a verificar.
	 * @param aID Identificador del artista.
	 * @return -1 si no existe de lo contrario el dID
	 * */
	private int checkAlbum(String name, int aID) {
		String tmpstm = "SELECT "+DataBase.ALBUMTABLE_DNAME+","+DataBase.ALBUMTABLE_DID+" FROM "+DataBase.ALBUMTABLE+" WHERE "+DataBase.ALBUMTABLE_DNAME+"='"+name+"' AND "+DataBase.ARTISTTABLE_AID+"='"+aID+"';";
		try {
			ResultSet rs = stm.executeQuery(tmpstm);
			return rs.next() ? rs.getInt("dID"): -1;
		} catch (SQLException e) {
			return -1;
		}
	}

	/**
	 * Inserta un nuevo registro de tartist o crea uno por defecto.
	 * @param artistName Nombre del artista a insertar.
	 * @return int el ID del artista creado o ya existente.
	 * */
	public int insertArtist(String artistName) {
		//Si el nombre el vacio.
		if(artistName.compareTo(StringValue.EMPTY_STRING) == 0) {
			int auxID = this.checkArtist(DEFAULT_ARTIST_NAME);
			//Si ya existe el registro default devuelve su id.
			if(auxID != -1) {
				return auxID;
			}else {
				String tmpstm = "INSERT INTO "+DataBase.ARTISTTABLE+" DEFAULT VALUES";
				try {
					stm.executeUpdate(tmpstm);
					int r = this.checkArtist(DEFAULT_ARTIST_NAME);
					return r;
				} catch (Exception e) {
					e.printStackTrace();
					return -1;
				}
			}
		}else {
			int auxID = this.checkArtist(artistName);
			if(auxID != -1) {
				return auxID;
			}else {
				String tmpstm = "INSERT INTO "+DataBase.ARTISTTABLE+"("+DataBase.ARTISTTABLE_AALIAS+") VALUES('"+artistName+"');";
				try {
					stm.executeUpdate(tmpstm);
					int r = this.checkArtist(artistName);
					return r;
				} catch (SQLException e) {
					e.printStackTrace();
					return -1;
				}
			}
		}
		
	}
	
	/**
	 * Inserta un nuevo registro de talbum o devuelve el id del objeto.
	 * @param albumName Nombre del album a insertar.
	 * @param aID Este id del artista que es generado por insertArtist().
	 * @return int el ID del album insertado..
	 * */
	public int insertAlbum(String albumName, int aID) {
		//Si el nombre el vacio.
		if(albumName.compareTo(StringValue.EMPTY_STRING) == 0) {
			int auxID = this.checkAlbum(DEFAULT_ALBUM_NAME, aID);
			//Si ya existe el registro default devuelve su id.
			if(auxID != -1) {
				return auxID;
			}else {
				String tmpstm = "INSERT INTO "+DataBase.ALBUMTABLE+"("+DataBase.ARTISTTABLE_AID+") VALUES('"+aID+"')";
				try {
					stm.executeUpdate(tmpstm);
					int r = this.checkAlbum(DEFAULT_ALBUM_NAME, aID);
					return r;
				} catch (Exception e) {
					e.printStackTrace();
					return -1;
				}
			}
		}else {
			int auxID = this.checkAlbum(albumName, aID);
			if(auxID != -1) {
				return auxID;
			}else {
				String tmpstm = "INSERT INTO "+DataBase.ALBUMTABLE+"("+DataBase.ALBUMTABLE_DNAME+", "+DataBase.ARTISTTABLE_AID+") VALUES('"+albumName+"', '"+aID+"')";
				try {
					stm.executeUpdate(tmpstm);
					int r = this.checkAlbum(albumName, aID);
					return r;
				} catch (SQLException e) {
					e.printStackTrace();
					return -1;
				}
			}
		}
	}
	
	/**
	 * Inserta un nuevo registro de tsong o lo actualiza y 
	 * devuelve el id del objeto.
	 * 
	 * @param songName Nombre de la cancion
	 * @param songChords Letra y acordes
	 * @param songRhythm Ritmo.
	 * @param baseChord Acorde Base
	 * @param favChord Acorde Favorito.
	 * @param dID El id del album. Se genera con insertAlbum();
 	 * @return int El id de la cancion insertada.
	 * */
	public int insertSong(String songName, String songChords, String songRhythm, String baseChord, String favChord, int dID) {
		//Si el nombre el vacio.
			int auxID = this.checkSong(songName, dID);
			if(auxID != -1) {//Actualiza el item.
				String tmpstm = "UPDATE "+DataBase.SONGTABLE+" SET "+DataBase.SONGTABLE_CNAME+" = '"+songName+"', "
						  											+DataBase.SONGTABLE_CCHORDS+" = '"+songChords+"', "
						  											+DataBase.SONGTABLE_CRHYTHM+" = '"+songRhythm+"', "
						  											+DataBase.ALBUMTABLE_DID+" = '"+dID+"', "
						  											+DataBase.SONGTABLE_CFVCHORD+" = '"+favChord+"', "
						  											+DataBase.SONGTABLE_CBSCHORD+" = '"+baseChord+"' "+
						        "WHERE "+DataBase.SONGTABLE_CID+" = '"+auxID+"'";
				try {
					stm.executeUpdate(tmpstm);
					return auxID;
				} catch (SQLException e) {
					e.printStackTrace();
					return -1;
				}
			}else {//Crea uno nuevo si no existe el item.
				String tmpstm = "INSERT INTO "+DataBase.SONGTABLE+"("+DataBase.SONGTABLE_CNAME+", "
											  +DataBase.SONGTABLE_CCHORDS+", "
											  +DataBase.SONGTABLE_CRHYTHM+", "
											  +DataBase.ALBUMTABLE_DID+","
											  +DataBase.SONGTABLE_CFVCHORD+","
											  +DataBase.SONGTABLE_CBSCHORD+
											  ") VALUES('"+songName+"','"+songChords+"','"+songRhythm+"','"+dID+"','"+favChord+"','"+baseChord+"');";
				try {
					stm.executeUpdate(tmpstm);
					int r = this.checkSong(songName, dID);
					return r;
				} catch (SQLException e) {
					e.printStackTrace();
					return -1;
				}
			}
	}
	
	/**
	 * Devuelve un arreglo con objetos que contienen los campos pedidos en el 
	 * arreglo de campos.
	 * 
	 * Este metodo esta disenado para devolver los datos con respecto a las todas las
	 * canciones
	 * 
	 * @param fields Arreglo de campos de la clase DataBase, cualquiera que contenga
	 * el nombre TABLE
	 * @return ArrayList Lista de registros con los campos solicitados o NULL
	 * si hay un error SQL.
	 * */
	public ArrayList<Object[]> getCustomDataField(String[] fields){
		String tmpstm = "SELECT * FROM "+DataBase.SONGTABLE+
								" JOIN "+DataBase.ALBUMTABLE+" ON "+DataBase.SONGTABLE+"."+DataBase.ALBUMTABLE_DID+" = "+DataBase.ALBUMTABLE+"."+DataBase.ALBUMTABLE_DID+
								" JOIN "+DataBase.ARTISTTABLE+" ON "+DataBase.ALBUMTABLE+"."+DataBase.ARTISTTABLE_AID+" = "+DataBase.ARTISTTABLE+"."+DataBase.ARTISTTABLE_AID;

		ArrayList<Object[]> array = new ArrayList<Object[]>();
		try {
			ResultSet rs = stm.executeQuery(tmpstm);
			while(rs.next()) {
				Object[] obj = new Object[fields.length];
				for (int i = 0; i < fields.length; i++) {
					obj[i] = rs.getObject(fields[i]);
				}
				array.add(obj);
			}
			return array;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	/**
	 * Devuelve un arreglo con objetos que contienen todos los registros de todas
	 * las tablas de la base de datos. Los ultimos 3 registros son cID, dID, aID
	 * respectivamente.
	 *  
	 *  
	 * @return ArrayList Lista de registros, el primer arreglo contiene los nombres de los campos que 
	 * han sido devueltos. o NULL si hay un error SQL.
	 * */
	public ArrayList<Object[]> getAllRecords(){
		String tmpstm = "SELECT * FROM "+DataBase.SONGTABLE+
								" JOIN "+DataBase.ALBUMTABLE+" ON "+DataBase.SONGTABLE+"."+DataBase.ALBUMTABLE_DID+" = "+DataBase.ALBUMTABLE+"."+DataBase.ALBUMTABLE_DID+
								" JOIN "+DataBase.ARTISTTABLE+" ON "+DataBase.ALBUMTABLE+"."+DataBase.ARTISTTABLE_AID+" = "+DataBase.ARTISTTABLE+"."+DataBase.ARTISTTABLE_AID+
								" ORDER BY "+DataBase.SONGTABLE_CNAME;

		ArrayList<Object[]> array = new ArrayList<Object[]>();
		try {
			ResultSet rs = stm.executeQuery(tmpstm);
			String[] auxStr = {DataBase.SONGTABLE_CNAME,
							   DataBase.SONGTABLE_CRHYTHM,
							   DataBase.SONGTABLE_CCHORDS,
							   DataBase.ALBUMTABLE_DNAME,
							   DataBase.SONGTABLE_CBSCHORD,
							   DataBase.SONGTABLE_CFVCHORD,
							   DataBase.ARTISTTABLE_AALIAS,
							   DataBase.SONGTABLE_CID,
							   DataBase.ALBUMTABLE_DID,
							   DataBase.ARTISTTABLE_AID};
			array.add(auxStr);
			while(rs.next()) {
				Object[] obj = new Object[auxStr.length];
				int o = 0;
				for (@SuppressWarnings("unused") Object object : obj) {
					obj[o] = rs.getObject(auxStr[o]);
					o++;
				}
				array.add(obj);
			}
			return array;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}

	/**
	 * Devuelve un arreglo con objetos que contienen todos los registros de todas
	 * las tablas de la base de datos que coinciden con la cadena en nombre de cancion,
	 * nombre de album, nombre de artista y ritmo. Los ultimos 3 registros son cID, dID, aID
	 * respectivamente.
	 * 
	 * @param textToFind La cadena que se desea generar.
	 * @return ArrayList Lista de registros, el primer arreglo contiene los nombres de los campos que 
	 * han sido devueltos. o NULL si hay un error SQL.
	 * */
	public ArrayList<Object[]> getCoincidentRecord(String textToFind) {
		String tmpstm = "SELECT * FROM "+DataBase.SONGTABLE+
				" JOIN "+DataBase.ALBUMTABLE+" ON "+DataBase.SONGTABLE+"."+DataBase.ALBUMTABLE_DID+" = "+DataBase.ALBUMTABLE+"."+DataBase.ALBUMTABLE_DID+
				" JOIN "+DataBase.ARTISTTABLE+" ON "+DataBase.ALBUMTABLE+"."+DataBase.ARTISTTABLE_AID+" = "+DataBase.ARTISTTABLE+"."+DataBase.ARTISTTABLE_AID+
				" WHERE "+DataBase.SONGTABLE_CNAME+" LIKE '%"+textToFind+
				"%' OR "+DataBase.ALBUMTABLE_DNAME+" LIKE '%"+textToFind+
				"%' OR "+DataBase.ARTISTTABLE_AALIAS+" LIKE '%"+textToFind+
				"%' OR "+DataBase.SONGTABLE_CRHYTHM+" LIKE '%"+textToFind+"%'";

		ArrayList<Object[]> array = new ArrayList<Object[]>();
		try {
			ResultSet rs = stm.executeQuery(tmpstm);
			String[] auxStr = {DataBase.SONGTABLE_CNAME,
							   DataBase.SONGTABLE_CRHYTHM,
							   DataBase.SONGTABLE_CCHORDS,
							   DataBase.ALBUMTABLE_DNAME,
							   DataBase.SONGTABLE_CBSCHORD,
							   DataBase.SONGTABLE_CFVCHORD,
							   DataBase.ARTISTTABLE_AALIAS,
							   DataBase.SONGTABLE_CID,
							   DataBase.ALBUMTABLE_DID,
							   DataBase.ARTISTTABLE_AID};
			array.add(auxStr);
			while(rs.next()) {
				Object[] obj = new Object[auxStr.length];
				int o = 0;
				for (@SuppressWarnings("unused") Object object : obj) {
					obj[o] = rs.getObject(auxStr[o]);
					o++;
				}
				array.add(obj);
			}
			return array;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Elimina un registro de la cancion.
	 * @param songID el ID de la cancion que se quiere eliminar.
	 * @return true si todo sale bien.
	 * */
	public boolean deleteSong(int songID) {
		String stmTmp = "DELETE FROM "+DataBase.SONGTABLE+" WHERE "+DataBase.SONGTABLE_CID+"='"+songID+"'";
		try {
			stm.executeUpdate(stmTmp);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Inicia el reporte desde la plantilla, agrega los parametros y devuelve 
	 * el stream imprimible.
	 * @param cID el id de la cancion que se quere exportar.
	 * @return JasperPrint el stream con la impresion de reportes.
	 * */
	public JasperPrint generateReportSteam(int cID) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("songName", cID);
		try {
			//JasperReport report = (JasperReport) JRLoader.loadObject(new File("./CancioneroReport.jasper"));
			JasperReport report = JasperCompileManager.compileReport("./res/CancioneroReport.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, map, con);
			return print;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}	
	
}
