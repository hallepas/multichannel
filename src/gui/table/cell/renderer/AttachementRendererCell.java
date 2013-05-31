package gui.table.cell.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXHyperlink;

public class AttachementRendererCell implements TableCellRenderer{
	
	private JXHyperlink hLink = new JXHyperlink(null);
	

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		System.out.println(value+"-"+row+"-"+column);
		hLink.setText((String) value);
		return hLink;
	}

}
