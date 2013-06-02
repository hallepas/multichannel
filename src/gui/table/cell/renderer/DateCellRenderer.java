package gui.table.cell.renderer;

import gui.helper.MessageProperties;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

public class DateCellRenderer implements TableCellRenderer {

	private JTextField field;
	private Color focusBackgroundColor;
	private Color normalBackgroundColor;

	public DateCellRenderer() {
		this.field = new JTextField();
		this.focusBackgroundColor = new Color(51, 153, 255);
		this.normalBackgroundColor = Color.WHITE;
	}

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