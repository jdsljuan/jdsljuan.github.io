package cancionero.control;

import cancionero.model.DataBase;
import cancionero.view.FrameWindow;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * El motor del la aplicacion se encarga de crear las instancias de la base de datos y
 * de la ventana principal. Gestiona todas las llamadas desde las vista haciendo peticiones
 * a la base de datos, si es necesario para resolverlas. Se encarga de ejecutar acciones de
 * actualizacion de las vistas de manera parcial, pues no maneja cambios de estilo o creacion
 * de elementos visuales mas alla de las mismas instancias.
 */
public class Engine {
	
	/**
	 * La instancia de la base de datos.
	 */
	private DataBase database = null;
	/**
	 * La instancia de la Ventana principal
	 */
	private FrameWindow frame;
	
	/**
	 * Representacion del Modelo dentro de la Vista.
	 */
	private TableData libraryPaneDataModel;
	
	/**
	 * Crea una instancia no visible de las ventanas usadas en el programa,
	 * una de la base de datos, una de la clase que mantiene el modelo dentro de
	 * la vista.
	 */
	public Engine() {
		database = new DataBase();
		//Unicio una tabla vacia.
		libraryPaneDataModel = new TableData();
		this.frame = new FrameWindow(this);
		this.run();
	}

	/**
	 * Inicia las conexiones con la Base de Datos, si es exitosa carga
	 * toda la biblioteca y luego la envia a la vista y la actualiza.
	 */
	private void run() {
		
		//Si la DATABASE se conecta y todas las tablas estan en su sitio, entonces inicia lo grafico.
		if(database.connect() && database.checkSchema()) {
			//Carga en tableDataMainView todas las canciones de la base de datos.
			this.actionLoadFullLibrary();
			//Setea la TableData data como modelo para la Tabla en la ventana principal.
			this.actionSetLibraryPaneTable();
			//Obtiene de manera correcta las vistas seleccionadas por el usuario y las muestra.
			this.requestViewModelUpdate();
			//Muestro la ventana principal.
			this.frame.setVisible(true);
		}
	}


	
	/**
	 * Carga todos los campos de la seleccionados desde la
	 * base de datos a las estructura que maneja el modelo.
	 * */
	private void actionLoadFullLibrary() {
		libraryPaneDataModel.setData(database.getAllRecords());		
	}
	
	/**
	 * Busca en la base de datos las concidencias con la cadena,
	 * carga el resultado en la estructura del modelo y luego
	 * actualizar la vista.
	 * 
	 * @param textToFind El texto que va a ser buscado.
	 * */
	private void actionLoadCoincidenceLibrary(String textToFind) {
		libraryPaneDataModel.setData(database.getCoincidentRecord(textToFind));
	}
	
	/**
	 * Carga la estructura del modelo (TableData) en la tabla.
	 * */
	private void actionSetLibraryPaneTable() {
		this.frame.getLibrayPane().setDataToTable(libraryPaneDataModel);
	}

	/**
	 * Ejecuta la accion de actualizar el modelo en la vista y hacer la peticion
	 * a la base de datos.
	 * Verifica si los datos en la base de datos han cambiado y si es asi actualiza
	 * la estrutura que maneja la vista.
	 * */
	public void requestViewModelUpdate() {
		boolean[] aux = frame.getLibrayPane().getCheckedFlags();
		libraryPaneDataModel.setColumnsToDisplay(aux[0],aux[1],aux[2],aux[3]);
		libraryPaneDataModel.fireTableStructureChanged();
	}

	/**
	 * Busca en la base de datos las concidencias con la cadena,
	 * carga el resultado en la estructura del modelo y luego
	 * actualizar la vista.
	 * @param textFromView El texto que se desea buscar y del cual
	 * se presentaran los resultados en la tabla.
	 * */
	public void requestViewLibrarySearch(String textFromView) {
		if(textFromView.isBlank() || textFromView.isEmpty()) {
			this.actionLoadFullLibrary();
			this.requestViewModelUpdate();
		}else {
			this.actionLoadCoincidenceLibrary(textFromView);
			this.requestViewModelUpdate();
		}
	}

	/**
	 * Busca todos los campos de la tabla Artistas en la base de datos
	 * y los pasa al combo box en el editor. (Este es un proceso lineal).
	 * */
	public void requestViewFillEditorArtistCombo() {
		frame.getEditorPane().getComboArtistName().removeAllItems();
		for (Object[] obj : database.getArtistRecords()) {
			frame.getEditorPane().getComboArtistName().addItem((String) obj[0]);
		}
		//Porque se va a seleccionar el premero y no queiro.
		frame.getEditorPane().getComboArtistName().insertItemAt(StringValue.EMPTY_STRING, 0);
		frame.getEditorPane().getComboArtistName().setSelectedIndex(0);
	}

	/**
	 * Busca todos los campos de la tabla Album segun id en la base de datos
	 * y los pasa al combo box en el editor. (Este es un proceso lineal).
	 * @param artistName el ID en texto del artista del cual se van a solicitar los campos.
	 * */
	//TODO Make this method boolean return. and Integer param depend ID
	public void requestViewFillEditorAlbumCombo(String artistName) {
		frame.getEditorPane().getComboAlbumName().removeAllItems();
		for (Object[] obj : database.getAlbumRecords(artistName)) {
			frame.getEditorPane().getComboAlbumName().addItem((String) obj[0]);
		}
	}
	
	/**
	 * Metodo encargado de guardar de guardar un registro.
	 * @param songName Nombre de la cancion
	 * @param songChords Letra y acordes
	 * @param songRhythm Ritmo.
	 * @param baseChord Acorde Base
	 * @param favChord Acorde Favorito.
	 * @param artistName Nombre del Artista.
	 * @param albumName Nombre del Album.
	 * @return true si se inserto la cancion exitosamente.
	 * */
	public boolean requestViewEditorSaveRecord(String songName, String songChords, String songRhythm, String baseChord,String favChord,String artistName,String albumName) {
		int aID = database.insertArtist(artistName);
		System.out.println(aID);
		if(aID != -1) {
			int dID = database.insertAlbum(albumName, aID);
			System.out.println(dID);
			if(dID != -1) {
				int cID = database.insertSong(songName, songChords,songRhythm,baseChord, favChord, dID);
				if(cID != -1) {
					this.actionLoadFullLibrary();
					this.requestViewFillEditorArtistCombo();
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Maneja el proceso de eliminar una cancion del modelo y actualizar la vista.
	 * @param cID el ID de la cancion que se desea eliminar.
	 * */
	public void requestViewViewerDeleteSong(int cID) {
		if(database.deleteSong(cID)) {
			this.actionLoadFullLibrary();
			this.requestViewModelUpdate();
			frame.setActivePanel(frame.getLibrayPane());
		}
	}
	
	/**
	 * Maneja el proceso de eliminar una cancion del modelo y actualizar la vista.
	 * @param cID el ID de la cancion que se desea eliminar.
	 * @return JasperPrint creado por la base de datos, que puede ser null.
	 * */
	public JasperPrint requestViewViewerGenerateReport(int cID) {
		return database.generateReportSteam(cID);
	}
}
