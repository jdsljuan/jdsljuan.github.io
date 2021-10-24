package cancionero.view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import cancionero.control.StringValue;

/**
 * ViewerPanel es una extencion de JPanel.
 * Con dimenciones estaticas. que presentara la informacion
 * de las canciones guardadas en la base de datos.
 */
@SuppressWarnings("serial")
public class ViewerPanel extends JPanel {
	
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
	
	private int cIDSongOnView = -1;
	private int rowOnEdit = -1;
	
	private JLabel titleLabel, albumLabel, singerLabel;
	private JButton reportButton, editButton;
	private JScrollPane leftScroll, rightScroll;
	private JTextArea leftTextPane, rightTextPane;

	/**
	 * Inicia el panel y todos sus campos, los ubica y les da colores.
	 * @param frame la instancia inyectada de la ventana principal.
	 * */
	public ViewerPanel(FrameWindow frame) {
		super();
		this.setLayout(null);
		this.setSize(minSize);
		this.setMaximumSize(minSize);
		this.setMinimumSize(minSize);
		this.setBackground(ColorPalette.BASE);
		this.setToolTipText(StringValue.VIEWER_PANEL_TIP);
		
		titleLabel = new JLabel(StringValue.VIEWER_UNKNOWN_TEXT);
		titleLabel.setSize(450, 50);
		titleLabel.setForeground(ColorPalette.HEAVY);
		titleLabel.setLocation(50, 25);
		titleLabel.setBackground(ColorPalette.BASE);
		titleLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
		titleLabel.setToolTipText(StringValue.EDITOR_SONGNAME_TEXT);
		
		albumLabel = new JLabel(StringValue.VIEWER_UNKNOWN_TEXT);
		albumLabel.setSize(275, 25);
		albumLabel.setForeground(ColorPalette.NEUTRAL);
		albumLabel.setLocation(50, 75);
		albumLabel.setBackground(ColorPalette.BASE);
		albumLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		albumLabel.setToolTipText(StringValue.EDITOR_ALBUMNAME_TEXT);
		
		singerLabel = new JLabel(StringValue.VIEWER_UNKNOWN_TEXT);
		singerLabel.setSize(275, 25);
		singerLabel.setForeground(ColorPalette.NEUTRAL);
		singerLabel.setLocation(50, 100);
		singerLabel.setBackground(ColorPalette.BASE);
		singerLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
		singerLabel.setToolTipText(StringValue.EDITOR_ARTISTNAME_TEXT);
		
		editButton = new JButton(StringValue.EMOJI_EDIT);
		editButton.setSize(100, 45);
		editButton.setForeground(ColorPalette.BASE);
		editButton.setLocation(550, 25);
		editButton.setBackground(ColorPalette.NEUTRAL);
		editButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		editButton.setBorderPainted(false);
		editButton.setToolTipText(StringValue.VIEWER_BTNEDIT_TIP);
		editButton.addActionListener(frame);
		
		reportButton = new JButton(StringValue.EMOJI_PRINT);
		reportButton.setSize(100, 45);
		reportButton.setForeground(ColorPalette.BASE);
		reportButton.setLocation(550, 75);
		reportButton.setBackground(ColorPalette.NEUTRAL);
		reportButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		reportButton.setBorderPainted(false);
		reportButton.setToolTipText(StringValue.VIEWER_BTNREPORT_TIP);
		reportButton.addActionListener(frame);
		
		leftTextPane = new JTextArea();
		leftTextPane.setSize(275, 450);
		leftTextPane.setForeground(ColorPalette.BLACK);
		leftTextPane.setBackground(ColorPalette.BASE);
		leftTextPane.setEditable(false);
		leftTextPane.setWrapStyleWord(true);
		leftTextPane.setLineWrap(true);
		
		rightTextPane = new JTextArea();
		rightTextPane.setSize(275, 450);
		rightTextPane.setForeground(ColorPalette.BLACK);
		rightTextPane.setBackground(ColorPalette.BASE);
		rightTextPane.setEditable(true);
		rightTextPane.setWrapStyleWord(true);
		rightTextPane.setLineWrap(true);
		
		Border line = BorderFactory.createLineBorder(ColorPalette.HEAVY);
		TitledBorder borderChord = BorderFactory.createTitledBorder(line, StringValue.EDITOR_SONGCHORD_TEXT);
		borderChord.setTitleColor(ColorPalette.HEAVY);
		
		leftScroll= new JScrollPane(leftTextPane);
		leftScroll.setSize(275, 450);
		leftScroll.setForeground(ColorPalette.LIGHT);
		leftScroll.setLocation(50, 130);
		leftScroll.setBackground(ColorPalette.BASE);
		leftScroll.setBorder(borderChord);
		
		rightScroll= new JScrollPane(rightTextPane);
		rightScroll.setSize(275, 450);
		rightScroll.setForeground(ColorPalette.LIGHT);
		rightScroll.setLocation(375, 130);
		rightScroll.setBackground(ColorPalette.BASE);
		rightScroll.setBorder(borderChord);
		
		this.add(rightScroll);
		this.add(leftScroll);
		this.add(reportButton);
		this.add(editButton);
		this.add(singerLabel);
		this.add(albumLabel);
		this.add(titleLabel);
	}
	
	/**
	 * Se encarga de rellenar los campos del visor.
	 * @param songName Nombre de la cancion
	 * @param songChords Acorde
	 * @param songRhythm Ritmo
	 * @param artistName Nombre del artista
	 * @param albumName Nombre del album
	 * */
	public void fillViewerWith(String songName, String songChords, String songRhythm,String artistName,String albumName) {
		this.singerLabel.setText(artistName);
		this.albumLabel.setText(albumName);
		this.titleLabel.setText(songName);
		this.leftTextPane.setText(songChords);
		this.rightTextPane.setText(songChords);
	}

	/**
	 * @return the editButton
	 */
	public JButton getEditButton() {
		return editButton;
	}

	/**
	 * @return the reportButton
	 */
	public JButton getReportButton() {
		return reportButton;
	}

	/**
	 * @return the cIDSongOnView
	 */
	public int getcIDSongOnView() {
		return cIDSongOnView;
	}

	/**
	 * @param cIDSongOnView the cIDSongOnView to set
	 */
	public void setcIDSongOnView(int cIDSongOnView) {
		this.cIDSongOnView = cIDSongOnView;
	}
	
	/**
	 * Muestra un mensaje de Error avisando que se debe seleccionar una cancion.
	 */
	public void showNoSelectSongWarning() {
		JOptionPane.showMessageDialog(this, StringValue.NO_SELECTED_SONG_MESSAGE);
	}
	
	/**
	 * @return the rowOnEdit
	 */
	public int getRowOnEdit() {
		return rowOnEdit;
	}

	/**
	 * @param rowOnEdit the rowOnEdit to set
	 */
	public void setRowOnEdit(int rowOnEdit) {
		this.rowOnEdit = rowOnEdit;
	}

	/**
	 * Muestra un mensaje de Error avisando que se debe seleccionar una cancion.
	 */
	public void showNoSong() {
		this.singerLabel.setText(StringValue.VIEWER_UNKNOWN_TEXT);
		this.albumLabel.setText(StringValue.VIEWER_UNKNOWN_TEXT);
		this.titleLabel.setText(StringValue.VIEWER_UNKNOWN_TEXT);
		this.leftTextPane.setText("");
		this.rightTextPane.setText("");
		this.rowOnEdit = -1;
		this.cIDSongOnView = -1;
	}

}
