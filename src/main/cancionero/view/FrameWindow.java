/**
 * 
 */
package cancionero.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cancionero.control.Engine;
import cancionero.control.StringValue;
import net.sf.jasperreports.view.JasperViewer;

/**
 * La ventana pricipal de programa, implementa y es escucha de todos
 * los paneles que se instancian en ella, por esta es que se comunica
 * la vista al controlador.
 *
 * Con respecto a ActionPerformance:
 * En este metodo ocurre la mayoria de la interaccion con el controlador,
 * escucha todos los eventos Action en los paneles. Se debe tener cuidado 
 * de no activar los eventos de otros componentes, con respecto a la persistencia
 * de los datos.
 * 
 * Si la accion es muy larga dimite la accion a otro metodo.
 * 
 */
@SuppressWarnings("serial")
public class FrameWindow extends JFrame implements ActionListener, MouseListener{
	
	/**
	 * Ancho del Panel
	 */
	private final int FINAL_W = 800;
	/**
	 * Alto del Panel
	 */
	private final int FINAL_H = 600;
	/**
	 * Inicializado con FINAL_H y FINAL_W, tamano
	 * que va a tener el panel en Dimension.
	 */
	private Dimension minSize = new Dimension(FINAL_W, FINAL_H);
	
	/**
	 * Instancia del Panel Libreria.
	 */
	private LibraryPanel libraryPane;
	/**
	 * Instancia del Panel Editor.
	 */
	private EditorPanel editorPane;
	/**
	 * Instancia del Panel Visor.
	 */
	private ViewerPanel viewerPane;
	/**
	 * Instancia del Panel Lateral.
	 */
	private SidePanel sidePane;
	
	/**
	 * Instancia del Controlador.
	 */
	private Engine controller;
	
	/**
	 * Inicializado con una instancia del Engine,
	 * inicia todos los campos los agrega a este panel
	 * y setea como escucha de todas los paneles instanciados
	 * esta ventana.
	 * 
	 * @param control Instancia del objeto que instancia este objeto. 
	 */
	public FrameWindow(Engine control) {
		super();
		
		this.controller = control;
		
		libraryPane = new LibraryPanel(this);
		editorPane = new EditorPanel(this);
		viewerPane = new ViewerPanel(this);
		sidePane = new SidePanel(this);
		
		sidePane.setLocation(0, 0);
		libraryPane.setLocation(100,0);
		editorPane.setLocation(100,0);
		viewerPane.setLocation(100, 0);
		
		this.setNoActivePanel();
		
		this.add(sidePane);
		this.add(editorPane);
		this.add(viewerPane);
		this.add(libraryPane);
		
		
		//TODO Hacer un panel para presentar bien esto.
		JLabel label = new JLabel(StringValue.FRAME_INTRO_TEXT);
		label.setSize(450, 50);
		label.setForeground(ColorPalette.HEAVY);
		label.setLocation(300, 450);
		label.setBackground(ColorPalette.BASE);
		label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		
		JLabel label2 = new JLabel("Cimarron");
		label2.setSize(450, 50);
		label2.setForeground(ColorPalette.HEAVY);
		label2.setLocation(290, 420);
		label2.setBackground(ColorPalette.BASE);
		label2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
		
		this.add(label);
		this.add(label2);
		
		this.setTitle(StringValue.PROYECT_TITLE);
		this.setLayout(null);
		this.setResizable(false);
		this.getContentPane().setBackground(ColorPalette.BASE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(minSize);
		this.setMaximumSize(minSize);
		this.setMinimumSize(minSize);
		this.pack();
	}
	
	/**
	 * Hace no visibles todos los paneles excepto el SidePanel.
	 */
	public void setNoActivePanel() {
		sidePane.setNoActiveButton();
		sidePane.setVisible(true);
		libraryPane.setVisible(false);
		editorPane.setVisible(false);
		viewerPane.setVisible(false);
	}
	
	/**
	 * Hace visible el panel que es pasado por parametro.
	 * @param pane JPanel panel a visualizar.
	 */
	public void setActivePanel(JPanel pane) {
		libraryPane.setVisible(false);
		editorPane.setVisible(false);
		viewerPane.setVisible(false);
		
		if(pane.equals(libraryPane)) {
			sidePane.setActiveButton(sidePane.getLibraryButton());
			libraryPane.setVisible(true);
			this.setTitle(StringValue.PROYECT_TITLE + " - "+StringValue.TITLE_LIBRARY_TEXT);
		}else if(pane.equals(editorPane)) {
			sidePane.setActiveButton(sidePane.getEditorButton());
			editorPane.setVisible(true);
			this.setTitle(StringValue.PROYECT_TITLE + " - "+StringValue.TITLE_EDITOR_TEXT);
		}else if(pane.equals(viewerPane)) {
			sidePane.setActiveButton(sidePane.getViewerButton());
			viewerPane.setVisible(true);
			this.setTitle(StringValue.PROYECT_TITLE + " - "+StringValue.TITLE_VIEWER_TEXT);
		}
	}

	/**
	 * @return the librayPane
	 */
	public LibraryPanel getLibrayPane() {
		return libraryPane;
	}
	
	/**
	 * @return the editorPane
	 */
	public EditorPanel getEditorPane() {
		return editorPane;
	}

	/**
	 * Este metodo se encarga de pasar al controlador las acciones respectivas
	 * a la ejecucion del modelo y tambien maneja la logica interna de la vista.
	 * 
	 * @param event El evento.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		//Boton Libreria del Panel Lateral
		if(event.getSource().equals(sidePane.getLibraryButton())) {
			controller.requestViewModelUpdate();
			this.setActivePanel(libraryPane);
		}
		//Boton Editor del Panel Lateral
		else if(event.getSource().equals(sidePane.getEditorButton())) {
			controller.requestViewFillEditorArtistCombo();
			this.setActivePanel(editorPane);
		}
		//Boton Visor del Panel Lateral
		else if(event.getSource().equals(sidePane.getViewerButton())) {
			if(viewerPane.getRowOnEdit() != -1) {
				this.setActivePanel(viewerPane);
			}else {
				viewerPane.showNoSelectSongWarning();
			}
		}
		//Boton Actualizar del panel Libreria.
		else if(event.getSource().equals(libraryPane.getUpdateButton())) {
			controller.requestViewModelUpdate();
		}
		//Boton Buscar del Panel Libreria
		else if(event.getSource().equals(libraryPane.getSearchButton())) {
			controller.requestViewLibrarySearch(libraryPane.getTfsearch().getText());
		}
		//Combo Box Artista es Seleccionado.
		else if(event.getSource().equals(editorPane.getComboArtistName())) {
			if(editorPane.getComboArtistName().getSelectedItem() != null) {
				controller.requestViewFillEditorAlbumCombo(editorPane.getComboArtistName().getSelectedItem().toString());
			}
		}
		//Boton Guardar Cancion del Editor.
		else if(event.getSource().equals(editorPane.getButtonSave())) {
			this.manageEditorSaveRecord();
		}
		//Boton Editar Cancion del Visualizador.
		else if(event.getSource().equals(viewerPane.getEditButton())) {
			this.manageViewerEditSong();
		}
		//Boton Generar Reporte Cancion del Visualizador.
		else if(event.getSource().equals(viewerPane.getReportButton())) {
			try {
				//controller.requestViewViewerGenerateReport(viewerPane.getcIDSongOnView());
				//JasperExportManager.exportReportToPdfFile(controller.requestViewViewerGenerateReport(viewerPane.getcIDSongOnView()), "song.pdf");
				JasperViewer view = new JasperViewer(controller.requestViewViewerGenerateReport(viewerPane.getcIDSongOnView()), false);
				view.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				view.setVisible(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Boton Modo Eliminar Cancion de libraria.
		else if(event.getSource().equals(libraryPane.getDeleteButton())) {
			libraryPane.setDeleteMode(!libraryPane.isDeleteMode());
		}
	}
	
	/**
	 * Maneja la accion del boton borrar cancion en el visualizador
	 * */
	private void manageLibraryDeleteSong() {
		Object[] rtnObj = libraryPane.getDataInstance().getData().get(libraryPane.getTablePane().getSelectedRow()+1);
		controller.requestViewViewerDeleteSong(Integer.parseInt(rtnObj[rtnObj.length-3].toString()));
		this.setActivePanel(libraryPane);
		viewerPane.showNoSong();
	}

	/**
	 * Maneja la respuesta del boton editar cancion en el visualizador
	 * y setea el id de la cancion actual en el visualidor.
	 * */
	private void manageViewerEditSong() {
		int cID = viewerPane.getcIDSongOnView();
		Object[] rtnObj = null;
		if(cID != -1) {
			//Trae la fila del ID que se esta mostrando.
			for (Object[] irow : libraryPane.getDataInstance().getData()) {
				if(irow[irow.length-3].toString().compareTo(""+cID) == 0) {
					rtnObj = irow;
					break;
				}
			}
			
			this.setActivePanel(editorPane);
			editorPane.fillEditorWith(
					rtnObj[0].toString(), 
					rtnObj[2].toString(),
					rtnObj[1].toString(),
					rtnObj[6].toString(),
					rtnObj[3].toString(),
					rtnObj[4].toString(), 
					rtnObj[5].toString());
			viewerPane.showNoSong();
		}else {
			viewerPane.showNoSelectSongWarning();
		}
	}
	
	/**
	 * Metodo al que se le encarga el llamado al controlador y la verificacion de
	 * los campos en la vista (Formularios) esten llenos, es llamado por el manejador de
	 * eventos.
	 * */
	private void manageEditorSaveRecord() {
		String aux0 = "";
		String aux1 = "";
		if(editorPane.isPrepared()) {
			if(editorPane.getComboArtistName().getSelectedItem() == null) {
				 aux0 = "";
			}else {
				 aux0 = editorPane.getComboArtistName().getSelectedItem().toString();
			}
			
			if(editorPane.getComboAlbumName().getSelectedItem() == null) {
				aux1 = "";
			}else {
				aux1 = editorPane.getComboAlbumName().getSelectedItem().toString();
			}
			
			boolean r = controller.requestViewEditorSaveRecord(
					editorPane.getTextSongName().getText(),
					editorPane.getTextPaneChords().getText(), 
					editorPane.getTextRhythm().getText(),
					editorPane.getComboBaseChord().getSelectedItem().toString(), 
					editorPane.getComboFavChord().getSelectedItem().toString(),
					aux0,
					aux1);
			if(r) {
				editorPane.showSuccessDataInsert();
				this.setActivePanel(libraryPane);
				controller.requestViewModelUpdate();
				editorPane.clearTextFields();
			}else {
				editorPane.showFailedDataInsert();
			}
		}else {
			editorPane.showMissedFieldsWarning();
		}
	}
	
	/**
	 * Se encarga de cargar los datos de la columna seleccionada en el visor
	 * y setea la informacion para disponer su edicion o eliminacion.
	 * */
	private void manageLibraryLoadSongOnViewer() {
		Object[] rtnObj = libraryPane.getDataInstance().getData().get(libraryPane.getTablePane().getSelectedRow()+1);
		viewerPane.fillViewerWith(
				rtnObj[0].toString(), 
				rtnObj[2].toString(), 
				rtnObj[1].toString(), 
				rtnObj[6].toString(), 
				rtnObj[3].toString());
		viewerPane.setcIDSongOnView((int) rtnObj[rtnObj.length-3]);
		//Unused
		viewerPane.setRowOnEdit(libraryPane.getTablePane().getSelectedRow()+1);
		this.setActivePanel(viewerPane);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//Tabla es Clickeada.
		if(e.getSource().equals(libraryPane.getTablePane())) {
			//Si esta en modo eliminar o no.
			if(libraryPane.isDeleteMode()) {
				this.manageLibraryDeleteSong();
			}else {
				this.manageLibraryLoadSongOnViewer();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
