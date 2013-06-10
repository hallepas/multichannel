package table.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import message.Message;
import message.MessageType;
import message.MessageWithSubjectAndAttachment;

/**
 * Das Tabellemodel für die Nachrichten
 * 
 */
public class MessageTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    /**
     * Alle Spaltennamen
     */
    private Vector<String> columnNames;

    /**
     * Alle Nachrichten
     */
    private ArrayList<Message> messages;

    /**
     * Der MessageType
     */
    private MessageType mt;

    /**
     * Initialisiert die Klasse
     * 
     * @param messages
     *            Die Nachrichten
     * @param messageType
     *            Der MessageType
     */
    public MessageTableModel(List<Message> messages, MessageType messageType) {
        this.messages = (ArrayList<Message>) messages;
        this.mt = messageType;
        fillColumnNames();
    }

    /**
     * Gibt die Anzahl Spalten zurück
     */
    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    /**
     * Füllt die Spaltennamen
     */
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

    /**
     * Gibt die Anzahl Zeilen zurück
     */
    @Override
    public int getRowCount() {
        return messages.size();
    }

    /**
     * Gibt den Wert zurück
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Message m = messages.get(rowIndex);

        switch (columnIndex) {
        case 0:
            if (m.getDate() != null) {
                return m.getDate();
            } else {
                return "-";
            }
        case 1:
            return m.getFrom();
        case 2:
            if (m instanceof MessageWithSubjectAndAttachment) {
                return ((MessageWithSubjectAndAttachment) m).getSubject();
            }
        case 3:
            if (m instanceof MessageWithSubjectAndAttachment) {
                return ((MessageWithSubjectAndAttachment) m).hasAttachment();
            }
        default:
            return "";
        }

    }

    /**
     * Ersetzt die alten Nachrichten und refresht die Tabelle
     * 
     * @param messages
     *            Ersetzt die alten Nachrichten
     */
    public void changeMessages(List<Message> messages) {
        this.messages = (ArrayList<Message>) messages;
        fireTableDataChanged();
    }

    /**
     * Refresht die Tabelle
     */
    public void refresh() {
        fireTableDataChanged();
    }

    /**
     * Gibt den Spaltennamen zurück
     */
    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

}
