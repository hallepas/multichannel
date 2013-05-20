package table.model;

import gui.helper.MessageProperties;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import message.Message;
import message.SMSMessage;

public class MessageTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] COLUMN_NAMES = { "Datum", "Von", "Betreff", "Anhang" };
	private ArrayList<SMSMessage> messages;

	public MessageTableModel(ArrayList<SMSMessage> messages) {
		this.messages = messages;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return messages.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Message m = messages.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return MessageProperties.DATE_FORMATTER.format(m.getDate());
		case 1:
			return m.getFrom();
		case 2:
			return m.getSubject();
		case 3:
			// TODO getAttechement: oder hasAttachement
			return "true";
		default:
			return "";
		}

	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

}
