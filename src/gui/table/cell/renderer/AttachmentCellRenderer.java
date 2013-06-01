package gui.table.cell.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class AttachmentCellRenderer implements TableCellRenderer {

	private JCheckBox checkBox;
	private Color focusBackgroundColor;
	private Color normalBackgroundColor;
	
	public AttachmentCellRenderer() {
		this.checkBox = new JCheckBox();
		//TODO Default focus blue color herausfinden
		this.focusBackgroundColor = Color.BLUE;
		this.normalBackgroundColor = Color.WHITE;
		prepare();
	}

	private void prepare() {
		this.focusBackgroundColor = Color.BLUE;
		this.normalBackgroundColor = Color.WHITE;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		boolean isCheck = Boolean.parseBoolean(value.toString());
		checkBox.setSelected(isCheck);
		
		if(isSelected){
			checkBox.setBackground(focusBackgroundColor);
		}else{
			checkBox.setBackground(normalBackgroundColor);
		}
		
		return checkBox;
	}

}
