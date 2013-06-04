package gui.table.cell.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


/**
 * Der Attachmentlistener. Zeigt einen Checkbox in der Attachment Spalte
 *
 */
public class AttachmentCellRenderer implements TableCellRenderer {

	/**
	 * Der Attachmentcheckbox
	 */
	private JCheckBox checkBox;
	
	/**
	 * Die Hintergrundfarbe mit Focus
	 */
	private Color focusBackgroundColor;	
	
	/**
	 * Die Hintergrundfarbe ohne Focus
	 */
	private Color normalBackgroundColor;
	
	
	/**
	 * Initialisiert die Klasse
	 */
	public AttachmentCellRenderer() {
		this.checkBox = new JCheckBox();
		this.focusBackgroundColor = new Color(51,153,255);
		this.normalBackgroundColor = Color.WHITE;
	}

	
	/**
	 * getTableCellRendererComponent
	 */
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
