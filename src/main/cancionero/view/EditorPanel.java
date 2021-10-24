/**
 * 
 */
package cancionero.view;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import cancionero.control.Chord;
import cancionero.control.StringValue;

/**
 * EditorPanel es una extencion de JPanel.
 * Con dimenciones estaticas.
 */
@SuppressWarnings("serial")
public class EditorPanel extends JPanel {

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
	
	private JTextField textSongName;
	private JComboBox<String> comboArtistName;
	private JComboBox<String> comboAlbumName;
	private JTextField textRhythm;
	private JComboBox<String> comboBaseChord;
	private JComboBox<String> comboFavChord;
	private JScrollPane scrollpane;
	private JTextArea textPaneChords;
	private JButton buttonSave;
	
	/**
	 * Inicia el panel y todos sus campos, los ubica y les da colores.
	 * @param frame la instancia inyectada de la ventana principal.
	 * */
	public EditorPanel(FrameWindow frame) {
		super();
		this.setLayout(null);
		this.setSize(minSize);
		this.setMaximumSize(minSize);
		this.setMinimumSize(minSize);
		this.setBackground(ColorPalette.BASE);
		this.setToolTipText(StringValue.EDITOR_PANEL_TIP);
		
		Border line = BorderFactory.createLineBorder(ColorPalette.HEAVY);
		
		TitledBorder borderSongName = BorderFactory.createTitledBorder(line, StringValue.EDITOR_SONGNAME_TEXT);
		borderSongName.setTitleColor(ColorPalette.HEAVY);
		textSongName = new JTextField();
		textSongName.setSize(250, 50);
		textSongName.setLocation(50, 50);
		textSongName.setBorder(borderSongName);
		textSongName.setBackground(ColorPalette.BASE);
		textSongName.setEditable(true);
		
		TitledBorder borderArtistName = BorderFactory.createTitledBorder(line, StringValue.EDITOR_ARTISTNAME_TEXT);
		borderArtistName.setTitleColor(ColorPalette.HEAVY);
		comboArtistName = new JComboBox<String>();
		comboArtistName.setSize(250, 50);
		comboArtistName.setLocation(50, 125);
		comboArtistName.setBorder(borderArtistName);
		comboArtistName.setBackground(ColorPalette.BASE);
		comboArtistName.setEditable(true);
		comboArtistName.setSelectedItem("");
		comboArtistName.addActionListener(frame);
		
		TitledBorder borderAlbumName = BorderFactory.createTitledBorder(line, StringValue.EDITOR_ALBUMNAME_TEXT);
		borderAlbumName.setTitleColor(ColorPalette.HEAVY);
		comboAlbumName = new JComboBox<String>();
		comboAlbumName.setSize(250, 50);
		comboAlbumName.setLocation(50, 200);
		comboAlbumName.setBorder(borderAlbumName);
		comboAlbumName.setBackground(ColorPalette.BASE);
		comboAlbumName.setEditable(true);
		comboAlbumName.setSelectedItem("");
		
		TitledBorder borderSongRhythm = BorderFactory.createTitledBorder(line, StringValue.EDITOR_SONGRHYTHM_TEXT);
		borderSongRhythm.setTitleColor(ColorPalette.HEAVY);
		textRhythm = new JTextField();
		textRhythm.setSize(250, 50);
		textRhythm.setLocation(50, 275);
		textRhythm.setBorder(borderSongRhythm);
		textRhythm.setBackground(ColorPalette.BASE);
		textRhythm.setEditable(true);

		TitledBorder borderSongBase = BorderFactory.createTitledBorder(line, StringValue.EDITOR_SONGBASEC_TEXT);
		borderSongBase.setTitleColor(ColorPalette.HEAVY);
		comboBaseChord = new JComboBox<String>(Chord.getAllChords());
		comboBaseChord.setSize(250, 50);
		comboBaseChord.setLocation(50, 350);
		comboBaseChord.setBorder(borderSongBase);
		comboBaseChord.setBackground(ColorPalette.BASE);
		comboBaseChord.setEditable(false);
		
		TitledBorder borderSongFav = BorderFactory.createTitledBorder(line, StringValue.EDITOR_SONGFAVC_TEXT);
		borderSongFav.setTitleColor(ColorPalette.HEAVY);
		comboFavChord = new JComboBox<String>(Chord.getAllChords());
		comboFavChord.setSize(250, 50);
		comboFavChord.setLocation(50, 425);
		comboFavChord.setBorder(borderSongFav);
		comboFavChord.setBackground(ColorPalette.BASE);
		comboFavChord.setEditable(false);
		
		buttonSave = new JButton(StringValue.EDITOR_BTNSAVE_TEXT);
		buttonSave.setSize(250, 50);
		buttonSave.setLocation(50, 500);
		buttonSave.setBackground(ColorPalette.NEUTRAL);
		buttonSave.setForeground(ColorPalette.BASE);
		buttonSave.setBorderPainted(false);
		buttonSave.addActionListener(frame);

		TitledBorder borderSongChord = BorderFactory.createTitledBorder(line, StringValue.EDITOR_SONGCHORD_TEXT);
		borderSongChord.setTitleColor(ColorPalette.HEAVY);
		textPaneChords = new JTextArea();
		textPaneChords.setSize(300, 500);
		textPaneChords.setBackground(ColorPalette.BASE);
		textPaneChords.setWrapStyleWord(true);
		textPaneChords.setLineWrap(true);
		scrollpane = new JScrollPane(textPaneChords);
		scrollpane.setBorder(borderSongChord);
		scrollpane.setSize(300, 500);
		scrollpane.setLocation(350, 50);
		scrollpane.setBackground(ColorPalette.BASE);
				
		this.add(scrollpane);
		this.add(buttonSave);
		this.add(comboFavChord);
		this.add(comboBaseChord);
		this.add(textRhythm);
		this.add(comboAlbumName);
		this.add(comboArtistName);
		this.add(textSongName);
	}

	/**
	 * Limpia todos los campos de texto en el Editor.
	 */
	public void clearTextFields() {
		textSongName.setText(StringValue.EMPTY_STRING);
		textRhythm.setText(StringValue.EMPTY_STRING);
		textPaneChords.setText(StringValue.EMPTY_STRING);
		comboBaseChord.setSelectedIndex(0);
		comboFavChord.setSelectedIndex(0);
	}

	/**
	 * @return true Si todos los campos necesarios
	 * estan diligenciados..
	 */
	public boolean isPrepared() {
		return (  textSongName.getText().isBlank()
				||textSongName.getText().isEmpty()
				||textRhythm.getText().isBlank()
				||textRhythm.getText().isEmpty()
				||textPaneChords.getText().isBlank()
				||textPaneChords.getText().isEmpty()
				? false:true);
	}
	
	/**
	 * Muestra un mensaje de Error avisando que los campos deben ser diligenciados.
	 */
	public void showMissedFieldsWarning() {
		JOptionPane.showMessageDialog(this, StringValue.EMPTY_FIELDS_ERROR_MESSAGE);
	}
	
	/**
	 * Muestra un mensaje de Exito indicando la insercion correcta de los datos.
	 */
	public void showSuccessDataInsert() {
		JOptionPane.showMessageDialog(this, StringValue.SAVE_SONG_SUCCESS_MESSAGE);
	}
	
	/**
	 * Muestra un mensaje de Error indicando la no insercion de los datos.
	 */
	public void showFailedDataInsert() {
		JOptionPane.showMessageDialog(this, StringValue.SAVE_SONG_ERROR_MESSAGE);
	}

	/**
	 * @return the textSongName
	 */
	public JTextField getTextSongName() {
		return textSongName;
	}

	/**
	 * @return the textRhythm
	 */
	public JTextField getTextRhythm() {
		return textRhythm;
	}

	/**
	 * @return the comboBaseChord
	 */
	public JComboBox<String> getComboBaseChord() {
		return comboBaseChord;
	}

	/**
	 * @return the comboFavChord
	 */
	public JComboBox<String> getComboFavChord() {
		return comboFavChord;
	}

	/**
	 * @return the textPaneChords
	 */
	public JTextArea getTextPaneChords() {
		return textPaneChords;
	}
	
	/**
	 * @return the comboArtistName
	 */
	public JComboBox<String> getComboArtistName() {
		return comboArtistName;
	}

	/**
	 * @return the comboAlbumName
	 */
	public JComboBox<String> getComboAlbumName() {
		return comboAlbumName;
	}

	/**
	 * @return the buttonSave
	 */
	public JButton getButtonSave() {
		return buttonSave;
	}
	
	/**
	 * Rellena todos los campos del Editor.
	 * 
	 * @param songName Nombre de la cancion.
	 * @param songChords Acordes de la cancion.
	 * @param songRhythm Ritmo de la cancion.
	 * @param artistName Nombre del Artista.
	 * @param albumName Nombre del Album.
	 * @param bc Tonalidad Base.
	 * @param fc Tonalidad Favorita.
	 */
	public void fillEditorWith(String songName,String songChords,String songRhythm,String artistName,String albumName,String bc,String fc) {
		textSongName.setText(songName);
		textPaneChords.setText(songChords);
		textRhythm.setText(songRhythm);
		comboArtistName.setSelectedItem(artistName);
		comboAlbumName.setSelectedItem(albumName);
		comboBaseChord.setSelectedItem(bc);
		comboFavChord.setSelectedItem(fc);
	}
	
}
