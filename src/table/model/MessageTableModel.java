package table.model;

import javax.swing.table.AbstractTableModel;

public class MessageTableModel extends AbstractTableModel{

	
	private static final long serialVersionUID = 1L;
	private static final String[] COLUMN_NAMES = {"Datum","Von", "Betreff", "Anhang"};
	
	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return "";
	}

	
	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}
	
}
