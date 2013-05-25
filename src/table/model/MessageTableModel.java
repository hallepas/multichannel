package table.model;

import gui.helper.MessageProperties;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import message.Message;
import message.MessageType;
import message.MessageWithSubjectAndAttachment;
import message.SMSMessage;

public class MessageTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Vector<String> columnNames;
	private ArrayList<Message> messages;
	private MessageType mt;

	public MessageTableModel(MessageType mt, ArrayList<Message> messages) {
		this.messages = messages;
		this.mt = mt;
		fillColumnNames();
	}

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	private void fillColumnNames() {
		if (mt.instance() instanceof MessageWithSubjectAndAttachment) {
			columnNames = new Vector<String>();
			columnNames.add("Datum");
			columnNames.add("Von");
			columnNames.add("Betreff");
			columnNames.add("Anhang");
		} else {
			columnNames = new Vector<String>();
			columnNames.add("Datum");
			columnNames.add("Von");
		}
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
			if (m instanceof MessageWithSubjectAndAttachment) {
				 return ((MessageWithSubjectAndAttachment) m).getSubject();
			}
		case 3:
			if (m instanceof MessageWithSubjectAndAttachment) {
				 return ((MessageWithSubjectAndAttachment) m).hasAttachments();
			}
		default:
			return "";
		}

	}

	@Override
	public String getColumnName(int column) {
		return columnNames.get(column);
	}

}
