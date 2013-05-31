package gui.table.cell.editor;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import org.jdesktop.swingx.JXHyperlink;

public class AttachementCellEditor implements TableCellEditor{
	private JXHyperlink hLink = new JXHyperlink();

	@Override
	public void addCellEditorListener(CellEditorListener l) {
	}

	@Override
	public void cancelCellEditing() {
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return false;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return false;
	}

	@Override
	public boolean stopCellEditing() {
		return false;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		hLink.setText(value.toString());
		return hLink;
	}

}
