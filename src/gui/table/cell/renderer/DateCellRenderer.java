package gui.table.cell.renderer;

import gui.helper.MessageProperties;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

public class DateCellRenderer implements TableCellRenderer {

	private JLabel field;
	private Color focusBackgroundColor;
	private Color normalBackgroundColor;

	public DateCellRenderer() {
		this.field = new JLabel();
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

		field.setText(MessageProperties.DATE_FORMATTER.format((Date)value));
		return field;
	}

}
