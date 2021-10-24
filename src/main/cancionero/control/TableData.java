package cancionero.control;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Esta clase se encarga de ordenar los datos que  se presentaran en la JTable 
 * que esta en la MainView.
 */
@SuppressWarnings("serial")
public class TableData extends AbstractTableModel {

	/**
	 * Estructura interna la estreuctura real que maneja los datos.
	 */
	private ArrayList<Object[]> data;
	/**
	 * Una copia que se mostrar de los datos, si esta 
	 * matriz no esta llena no se muestra nada en la pantalla de la tabla.
	 */
	private Object[][] dataToDisplay;
	
	/**
	 * Inicia el constructor del objeto padre.
	 */
	public TableData() {
		super();
		this.dataToDisplay = new Object[1][1];
	}
	
	//------------------------------------------
	
	@Override
	public String getColumnName(int index) {
		return (String) dataToDisplay[0][index];
	}

	@Override
	public int getColumnCount() {
		return (dataToDisplay[0].length <= 3) ? 0 : dataToDisplay[0].length-3;
	}

	@Override
	public int getRowCount() {
		return dataToDisplay.length-1;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return dataToDisplay[arg0+1][arg1];
	}
	
	//-----------------------------------------
	
	/**
	 * Usa data para crear dataToDisplay
	 * Este metodo controla el las paletas que se van a motrar y se mostran en el orden exacto
	 * de los parametros, tambien se encarga de agregar las respectivas cabeceras.
	 * @param song Nombre de la cancion
	 * @param album Nombre del album
	 * @param artist Nombre del Artista
	 * @param rhythm Ritmo de la Cancion
	 */
	public void setColumnsToDisplay(boolean song, boolean album, boolean artist, boolean rhythm) {
		if(dataToDisplay != null) {
			for (int i = 0; i < dataToDisplay[0].length; i++) {
				dataToDisplay[0][i] = "";
			}
			
		}
		int totalFlags = 0;
		if (song) {
			totalFlags++;
		}if (album) {
			totalFlags++;
		}if (artist) {
			totalFlags++;
		}if (rhythm) {
			totalFlags++;
		}
		
		this.dataToDisplay = new Object[data.size()][totalFlags+3];
		int row = 0;
		for (Object[] objects : data) {
			int columns = 0;
			if (song) {
				if(row != 0) {
					dataToDisplay[row][columns] = objects[0];
					columns++;
				}else {
					dataToDisplay[row][columns] = "Cancion";
					columns++;
				}
			}if (album) {
				if(row != 0) {
					dataToDisplay[row][columns] = objects[3];
					columns++;
				}else {
					dataToDisplay[row][columns] = "Album";
					columns++;
				}
			}if (artist) {
				if(row != 0) {
					dataToDisplay[row][columns] = objects[6];
					columns++;
				}else {
					dataToDisplay[row][columns] = "Artista";
					columns++;
				}
			}if (rhythm) {
				if(row != 0) {
					dataToDisplay[row][columns] = objects[1];
					columns++;
				}else {
					dataToDisplay[row][columns] = "Ritmo";
					columns++;
				}
			}
			
			dataToDisplay[row][columns++] = objects[7];
			dataToDisplay[row][columns++] = objects[8];
			dataToDisplay[row][columns++] = objects[9];

			row++;
		}
	}
	/**
	 * @return the data
	 */
	public ArrayList<Object[]> getData() {
		return data;
	}

	/**Despues de llamar este metodo llamar a setColumnsToDisplay.
	 * @param data the data to set
	 */
	public void setData(ArrayList<Object[]> data) {
		this.data = data;
	}
	
}
