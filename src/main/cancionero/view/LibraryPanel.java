/**
 * 
 */
package cancionero.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import cancionero.control.StringValue;
import cancionero.control.TableData;

/**
 * Panel que presenta una tabla donde se presentara la informacion de las
 * canciones.
 * @author Juan David Sanchez Leal.
 */
@SuppressWarnings("serial")
public class LibraryPanel extends JPanel {

	/**
	 * Ancho del Panel
	 */
	private final int FINAL_W = 700;
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
	 * Tamano del Emoji.
	 */
	private final int ICON_BTN_SIZE = 30;
	/**
	 * dataInstace nunca inicializado debe ser seteado.
	 * */
	private TableData dataInstance = null;

	private JTable tablePane;
	private JScrollPane scrollPane;
	private JTextField tfsearch;
	private JButton btnSearch;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JCheckBox chbSong, chbAlbum, chbArtist, chbRhythm;
	
	private boolean deleteMode = false;

	/**
	 * Crea y configura todos los campos excepto su tamano.
	 * El padre (Contenedor) de este objeto debe implementar 
	 * java.awt.event.MouseListener;
	 * 
	 * @param frame La instancia inyectada de la ventana principal.
	 * */
	public LibraryPanel(FrameWindow frame) {
		super();
		this.setLayout(null);
		this.setSize(minSize);
		this.setMaximumSize(minSize);
		this.setMinimumSize(minSize);
		this.setBackground(ColorPalette.BASE);
		this.setToolTipText(StringValue.LIBRARY_PANEL_TIP);
		
		Border line = BorderFactory.createLineBorder(ColorPalette.HEAVY);
		TitledBorder border = BorderFactory.createTitledBorder(line, StringValue.TABLE_TITLE_BORDER);
		border.setTitleColor(ColorPalette.HEAVY);
		
		this.tablePane = new JTable();
		//Manage the Event.
		tablePane.addMouseListener(frame);
		tablePane.getTableHeader().setReorderingAllowed(false);
		tablePane.getTableHeader().setResizingAllowed(false);
		tablePane.setSize(600, 400);
		tablePane.setBackground(ColorPalette.BASE);
		tablePane.setSelectionBackground(ColorPalette.NEUTRAL);
		tablePane.setSelectionForeground(ColorPalette.BASE);

		this.scrollPane = new JScrollPane(tablePane);
		scrollPane.setPreferredSize(new Dimension(600, 400));
		scrollPane.setSize(600, 400);
		scrollPane.setLocation(50, 150);
		scrollPane.setBorder(border);
		scrollPane.setBackground(ColorPalette.BASE);
		
		TitledBorder border2 = BorderFactory.createTitledBorder(line, StringValue.TABLE_SEARCH_TEXTFIELD_TEXT);
		border2.setTitleColor(ColorPalette.HEAVY);
		
		this.tfsearch = new JTextField();
		tfsearch.setBackground(ColorPalette.BASE);
		tfsearch.setForeground(Color.BLACK);
		tfsearch.setSelectedTextColor(null);
		tfsearch.setSize(275, 50);
		tfsearch.setLocation(50, 50);
		tfsearch.setBorder(border2);
		
		this.btnSearch = new JButton(StringValue.EMOJI_SEARCH);
		btnSearch.setBackground(ColorPalette.NEUTRAL);
		btnSearch.setForeground(ColorPalette.BASE);
		btnSearch.setSize(75, 50);
		btnSearch.setLocation(375, 50);
		btnSearch.setFont(new Font(Font.MONOSPACED, Font.PLAIN, ICON_BTN_SIZE));
		btnSearch.setBorderPainted(false);
		btnSearch.addActionListener(frame);
		btnSearch.setToolTipText(StringValue.TABLE_SEARCH_TEXTFIELD_TEXT);
		
		this.btnUpdate = new JButton(StringValue.EMOJI_UPDATE);
		btnUpdate.setBackground(ColorPalette.NEUTRAL);
		btnUpdate.setForeground(ColorPalette.BASE);
		btnUpdate.setSize(75, 50);
		btnUpdate.setLocation(475, 50);
		btnUpdate.setFont(new Font(Font.MONOSPACED, Font.PLAIN, ICON_BTN_SIZE));
		btnUpdate.setBorderPainted(false);
		btnUpdate.addActionListener(frame);
		btnUpdate.setToolTipText(StringValue.TABLE_UPDATE_TEXT);
		
		this.btnDelete = new JButton(StringValue.EMOJI_DELETE);
		btnDelete.setBackground(ColorPalette.NEUTRAL);
		btnDelete.setForeground(ColorPalette.BASE);
		btnDelete.setSize(75, 50);
		btnDelete.setLocation(575, 50);
		btnDelete.setFont(new Font(Font.MONOSPACED, Font.PLAIN, ICON_BTN_SIZE));
		btnDelete.setBorderPainted(false);
		btnDelete.addActionListener(frame);
		btnDelete.setToolTipText(StringValue.TABLE_DELETE_TEXT);
		
		this.chbSong = new JCheckBox(StringValue.EDITOR_SONGNAME_TEXT);
		chbSong.setForeground(ColorPalette.HEAVY);
		chbSong.setBackground(ColorPalette.BASE);
		chbSong.setSize(100,25);
		chbSong.setLocation(100,112);
		chbSong.setSelected(true);
		
		this.chbAlbum = new JCheckBox(StringValue.EDITOR_ALBUMNAME_TEXT);
		chbAlbum.setForeground(ColorPalette.HEAVY);
		chbAlbum.setBackground(ColorPalette.BASE);
		chbAlbum.setSize(100,25);
		chbAlbum.setLocation(250,112);
		chbAlbum.setSelected(true);
		
		this.chbArtist = new JCheckBox(StringValue.EDITOR_ARTISTNAME_TEXT);
		chbArtist.setForeground(ColorPalette.HEAVY);
		chbArtist.setBackground(ColorPalette.BASE);
		chbArtist.setSize(100,25);
		chbArtist.setLocation(400,112);
		chbArtist.setSelected(true);
		
		this.chbRhythm = new JCheckBox(StringValue.EDITOR_SONGRHYTHM_TEXT);
		chbRhythm.setForeground(ColorPalette.HEAVY);
		chbRhythm.setBackground(ColorPalette.BASE);
		chbRhythm.setSize(100,25);
		chbRhythm.setLocation(550,112);
		chbRhythm.setSelected(true);
		
		this.add(btnDelete);
		this.add(chbRhythm);
		this.add(chbArtist);
		this.add(chbAlbum);
		this.add(chbSong);
		this.add(scrollPane);
		this.add(tfsearch);
		this.add(btnSearch);
		this.add(btnUpdate);
	}
	
	/**
	 * Setea la TablaData.
	 * @param data La instancia del modelo que controlara la vista.
	 */
	public void setDataToTable(TableData data) {
		tablePane.setModel(data);
		dataInstance = data;
	}

	
	
	/**
	 * @return the dataInstance
	 */
	public TableData getDataInstance() {
		return dataInstance;
	}

	/**
	 * @return La tabla.
	 */
	public JTable getTablePane() {
		return tablePane;
	}

	/**
	 * @return the tfsearch
	 */
	public JTextField getTfsearch() {
		return tfsearch;
	}

	/**
	 * @return the btnUpdate
	 */
	public JButton getUpdateButton() {
		return this.btnUpdate;
	}

	/**
	 * @return the btnSearch
	 */
	public JButton getSearchButton() {
		return btnSearch;
	}

	/**
	 * Devuelve en el siguiente orden SONG, ALBUM, ARTIST, RHYTHM
	 * los valores de seleccion de los checkBox correspondientes.
	 * @return the checkedFlags
	 */
	public boolean[] getCheckedFlags() {
		boolean[] aux = {chbSong.isSelected(), chbAlbum.isSelected(), chbArtist.isSelected(), chbRhythm.isSelected()};
		return aux;
	}

	/**
	 * @return the deleteMode
	 */
	public boolean isDeleteMode() {
		return deleteMode;
	}

	/**
	 * Cambia el color del boton y setea el modo.
	 * @param deleteMode the deleteMode to set
	 */
	public void setDeleteMode(boolean deleteMode) {
		
		this.deleteMode = deleteMode;
		if(this.deleteMode) {
			Border line = BorderFactory.createLineBorder(Color.RED);
			TitledBorder border = BorderFactory.createTitledBorder(line, StringValue.TABLE_DELETEMODE_BORDER);
			border.setTitleColor(Color.RED);
			scrollPane.setBorder(border);
			btnDelete.setBackground(Color.RED);
			btnDelete.setForeground(ColorPalette.BASE);
		}else {
			Border line = BorderFactory.createLineBorder(ColorPalette.HEAVY);
			TitledBorder border = BorderFactory.createTitledBorder(line, StringValue.TABLE_TITLE_BORDER);
			border.setTitleColor(ColorPalette.HEAVY);
			scrollPane.setBorder(border);
			btnDelete.setBackground(ColorPalette.NEUTRAL);
			btnDelete.setForeground(ColorPalette.BASE);
		}
	}

	/**
	 * @return the btnDelete
	 */
	public JButton getDeleteButton() {
		return btnDelete;
	}
	
	
	
	
}
