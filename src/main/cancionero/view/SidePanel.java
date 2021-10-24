package cancionero.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import cancionero.control.StringValue;

/**
 * SidePanel modela la barra lateral por medio de la cual el usuario sera 
 * capas de elegir distintos botones que ejecutaran acciones dentro del software.
 * */
@SuppressWarnings("serial")
public class SidePanel extends JPanel {
	
	private final int FINAL_W = 100;
	private final int FINAL_H = 600;
	private Dimension minSize = new Dimension(FINAL_W, FINAL_H);
	
	private JButton libraryButton, editorButton, viewerButton;

	/**
	 * Inicia un SidePanel agrega las modificaciones a los campos
	 * y los agrega al panel.
	 * 
	 * @param mainFrame Objeto que se supone oira los eventos de los 
	 * botones.
	 * */
	public SidePanel(ActionListener mainFrame) {
		super();
		this.setLayout(null);
		this.setSize(minSize);
		this.setMaximumSize(minSize);
		this.setMinimumSize(minSize);
		this.setBackground(ColorPalette.LIGHT);
		
		Border line = BorderFactory.createLineBorder(ColorPalette.LIGHT);
		
		libraryButton = new JButton(StringValue.EMOJI_BOOKS);
		libraryButton.setSize(100, 100);
		libraryButton.setForeground(ColorPalette.HEAVY);
		libraryButton.setLocation(0, 0);
		libraryButton.setBackground(ColorPalette.LIGHT);
		libraryButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		libraryButton.setBorder(line);
		libraryButton.setToolTipText(StringValue.SIDEBAR_BTNLIBRARY_TIP);
		libraryButton.addActionListener(mainFrame);
		
		editorButton = new JButton(StringValue.EMOJI_MEMO);
		editorButton.setSize(100, 100);
		editorButton.setForeground(ColorPalette.HEAVY);
		editorButton.setLocation(0, 100);
		editorButton.setBackground(ColorPalette.LIGHT);
		editorButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		editorButton.setBorder(line);
		editorButton.setToolTipText(StringValue.SIDEBAR_BTNEDITOR_TIP);
		editorButton.addActionListener(mainFrame);
		
		viewerButton = new JButton(StringValue.EMOJI_GUITAR);
		viewerButton.setSize(100, 100);
		viewerButton.setForeground(ColorPalette.HEAVY);
		viewerButton.setLocation(0, 200);
		viewerButton.setBackground(ColorPalette.LIGHT);
		viewerButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		viewerButton.setBorder(line);
		viewerButton.setToolTipText(StringValue.SIDEBAR_BTNVISOR_TIP);
		viewerButton.addActionListener(mainFrame);
		
		this.add(viewerButton);
		this.add(editorButton);
		this.add(libraryButton);
	}
	
	/**
	 * Ajusta el color de los botones para resaltar el que 
	 * se pasa como parametro.
	 * 
	 * @param btn Debe ser uno de los campos del SidePanel.
	 * */
	public void setActiveButton(JButton btn) {
		this.setNoActiveButton();
		if(btn.equals(libraryButton)) {
			libraryButton.setForeground(ColorPalette.BASE);
			libraryButton.setBackground(ColorPalette.NEUTRAL);	
		}else if(btn.equals(editorButton)) {
			editorButton.setForeground(ColorPalette.BASE);
			editorButton.setBackground(ColorPalette.NEUTRAL);
		}else if(btn.equals(viewerButton)) {
			viewerButton.setForeground(ColorPalette.BASE);
			viewerButton.setBackground(ColorPalette.NEUTRAL);
		}
	}
	
	/**
	 * Setea en todos los botones el color por defecto y
	 * no resalta ninguno.
	 * */
	public void setNoActiveButton() {
		libraryButton.setForeground(ColorPalette.HEAVY);
		libraryButton.setBackground(ColorPalette.LIGHT);
		editorButton.setForeground(ColorPalette.HEAVY);
		editorButton.setBackground(ColorPalette.LIGHT);
		viewerButton.setForeground(ColorPalette.HEAVY);
		viewerButton.setBackground(ColorPalette.LIGHT);
	}

	/**
	 * @return the libraryButton
	 */
	public JButton getLibraryButton() {
		return libraryButton;
	}

	/**
	 * @return the editorButton
	 */
	public JButton getEditorButton() {
		return editorButton;
	}

	/**
	 * @return the viewerButton
	 */
	public JButton getViewerButton() {
		return viewerButton;
	}
	
}
