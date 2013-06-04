package gui.table.cell.renderer;

import gui.helper.MessageProperties;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

/**
 * Der Attachmentlistener. Zeigt einen Field in der Datum Spalte
 *
 */
public class DateCellRenderer implements TableCellRenderer {

	/**
	 * Das Datumfield
	 */
	private JTextField field;
	
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
	public DateCellRenderer() {
		this.field = new JTextField();
		this.focusBackgroundColor = new Color(51, 153, 255);
		this.normalBackgroundColor = Color.WHITE;
	}

	/**
	 * getTableCellRendererComponent
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		if (isSelected) {
			field.setBackground(focusBackgroundColor);
		} else {
			field.setBackground(normalBackgroundColor);
		}

		if (value.toString().equals("-")) {
			field.setText("-");
		} else {
			field.setText(MessageProperties.DATE_FORMATTER.format((Date) value));
		}
		return field;

	}
}