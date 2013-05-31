package gui.components;

import gui.font.MessageFont;
import gui.helper.GridBagManager;
import gui.table.cell.editor.AttachementCellEditor;
import gui.table.cell.renderer.AttachementRendererCell;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;

import message.Message;
import message.MessageType;
import message.MessageWithSubjectAndAttachment;

import org.jdesktop.swingx.JXHyperlink;

import table.model.MessageTableModel;
import clients.MessageClient;
import clients.useragents.PrintJobUserAgent;
import clients.useragents.UserAgent;

public class MessagesTab extends JComponent {

	enum MessageBoxState {
		DRAFTS, INBOX;
	}

	private static final long serialVersionUID = 1L;

	private MessageType messageType;
	private GridBagManager guiManager;
	private GridBagManager guiManagerPropertiesPanel;
	private JTextPane messageTextField;
	private JTable messagesTable;

	private JXHyperlink lbInbox;
	private JXHyperlink lbEntwürfe;
	private JButton createButton;
	private JButton deleteButton;
	private JButton printButton;
	private JButton attachementButton;
	private String tabTitle;
	private List<Message> messages;
	private JPanel panelProperties;
	private MessageClient messageClient;
	private MessageTableModel tableModel;
	private MessageBoxState boxState = MessageBoxState.INBOX;

	public MessagesTab(MessageClient messageClient, MessageType messageType) {
		this.messageClient = messageClient;
		this.panelProperties = new JPanel();
		this.messageType = messageType;
		this.messages = MessageClient.getOnlyType(messageClient.getMessagesFromInbox(), messageType);
		this.guiManager = new GridBagManager(this);
		this.guiManagerPropertiesPanel = new GridBagManager(panelProperties);
		this.messageTextField = new JTextPane();
		this.tableModel = new MessageTableModel(messages, messageType);
		this.messagesTable = new JTable(tableModel);
		// TODO Spalte Anhang: Checkbox anzeigen
		this.createButton = new JButton(this.messageType.getTypeName() + " erstellen");
		this.deleteButton = new JButton(this.messageType.getTypeName() + " löschen");
		this.attachementButton = new JButton("Anhang speichern");
		this.printButton = new JButton(this.messageType.getTypeName() + " drucken");
		this.tabTitle = messageType.getTypeName();

		this.lbInbox = new JXHyperlink(new InboxActionListener());
		this.lbEntwürfe = new JXHyperlink(new DraftsActionListener());

		this.lbInbox.setText("Inbox");
		this.lbEntwürfe.setText("Entwürfe");

		configureFrame();
	}

	private void createPropertiesPanel() {
		guiManagerPropertiesPanel.setX(0).setY(0).setComp(lbInbox);
		guiManagerPropertiesPanel.setX(0).setY(1).setComp(lbEntwürfe);
		guiManagerPropertiesPanel.setX(0).setY(2).setFill(GridBagConstraints.HORIZONTAL).setComp(createButton);
		guiManagerPropertiesPanel.setX(0).setY(3).setFill(GridBagConstraints.HORIZONTAL).setComp(deleteButton);
		
		if (messageClient.canPrint()) {
			guiManagerPropertiesPanel.setX(0).setY(4).setFill(GridBagConstraints.HORIZONTAL).setComp(printButton);
		}
		//TODO 
		
//		attachementButton
		guiManagerPropertiesPanel.setX(0).setY(5).setWeightY(20).setHeight(10).setComp(new JLabel());
	}

	private void configureFrame() {

		createPropertiesPanel();

		//TODO überprüfen ob ees ine spalte anhang hat
		//TODO enable machen und Jfilehcooser anzeigen
		if (messagesTable.getColumnCount()>2) {
			messagesTable.getColumnModel().getColumn(3).setCellRenderer(new AttachementRendererCell());
			messagesTable.getColumnModel().getColumn(3).setCellEditor(new AttachementCellEditor());
		}

		// Am Anfang ist der Inbox selektiert
		lbInbox.setForeground(Color.RED);
		lbEntwürfe.setForeground(Color.BLUE);

		// Um den Text schön darzustellen (Fett, Kursiv, Abbruch etc.)
		HTMLEditorKit eKit = new HTMLEditorKit();
		messageTextField.setEditable(false);
		messageTextField.setEditorKit(eKit);
		messagesTable.setAutoCreateRowSorter(true);
		panelProperties.setBorder(new TitledBorder("Eigenschaften"));

		messagesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Nur Entwürfe darf man bearbeiten
				if (!boxState.equals(MessageBoxState.DRAFTS)) {
					return;
				}

				if (e.getClickCount() == 2) {
					int selectedRow = messagesTable.getSelectedRow();

					if (selectedRow > -1) {
						Message message = messages.get(selectedRow);
						MessageDialog mf = new MessageDialog(message, messageType, messageClient, true);
						mf.setVisible(true);
						updateMessageBoxes();
						tableModel.refresh();
						messagesTable.repaint();
					}
				}
			}
		});

		messagesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = messagesTable.getSelectedRow();

				if (selectedRow == -1) {
					return;
				}

				Message m = messages.get(selectedRow);
				String subjectText = "";

				if (m instanceof MessageWithSubjectAndAttachment) {
					subjectText = "<br<b>Betreff: </b>" + ((MessageWithSubjectAndAttachment) m).getSubject() + "";
				}

				String toList = "";

				if (m.getTo().toString() != null) {
					toList = m.getTo().toString();
				}
				// TODO tolist schöner darstelllen
				messageTextField.setText("<html><b>Von:</b> " + m.getFrom() + "<br><b>An:</b>" + toList + subjectText + "<br><br><br>" + m.getMessage() + "</html>");
			}
		});

		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO überprüfen ob hier der Aufruf
				// (messageClient.newMessage(messageType) eine gute Idee ist
				MessageDialog mf = new MessageDialog(messageClient.newMessage(messageType), messageType, messageClient, false);
				mf.setVisible(true);
				updateMessageBoxes();
				tableModel.refresh();
				messagesTable.repaint();
			}
		});

		printButton.addActionListener(new PrintActionLIstener());
		deleteButton.addActionListener(new DeleteActionListener());
		messageTextField.setFont(MessageFont.MESSAGE_FONT);

		guiManager.setX(0).setY(0).setWidth(6).setScrollPanel().setComp(messagesTable);
		guiManager.setX(6).setY(0).setWidth(8).setWeightX(15).setScrollPanel().setComp(messageTextField);
		guiManager.setX(14).setY(0).setWidth(1).setScrollPanel().setComp(panelProperties);

	}

	public String getTabTitle() {
		return tabTitle;
	}

	class DeleteActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int[] selectedRows = messagesTable.getSelectedRows();

			if (selectedRows.length < 1) {
				return;
			}

			int result = JOptionPane.showConfirmDialog(null, "Möchten Sie diese Nachricht löschen?", "Nachricht löschen", JOptionPane.YES_NO_OPTION);

			// 0 bedeuted ja -> löschen
			if (result != 0) {
				return;
			}

			for (int i : selectedRows) {
				Message m = messages.get(i);
				messageClient.deleteDraft(m);

				if (boxState.equals(MessageBoxState.DRAFTS)) {
					messageClient.deleteDraft(m);
				} else if (boxState.equals(MessageBoxState.INBOX)) {
					messageClient.deleteMessageFromInbox(m);
				}
			}

			if (boxState.equals(MessageBoxState.DRAFTS)) {
				updateDraftsMessages();
			} else if (boxState.equals(MessageBoxState.INBOX)) {
				updateInboxMessages();
			}
		}

	}

	public void updateMessageBoxes() {
		updateInboxMessages();
		updateDraftsMessages();
	}

	private void updateInboxMessages() {
		if (boxState.equals(MessageBoxState.INBOX)) {
			messages = MessageClient.getOnlyType(messageClient.getMessagesFromInbox(), messageType);
			tableModel.changeMessages(messages);
			messagesTable.repaint();
		}
	}

	private void updateDraftsMessages() {
		if (boxState.equals(MessageBoxState.DRAFTS)) {
			messages = MessageClient.getOnlyType(messageClient.getDrafts(), messageType);
			tableModel.changeMessages(messages);
			messagesTable.repaint();
		}
	}

	class PrintActionLIstener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int selectedRow = messagesTable.getSelectedRow();

			if (selectedRow == -1) {
				return;
			}

			Message m = messages.get(selectedRow);
			UserAgent ua = messageClient.getUserAgentFor(messageType);
			System.out.println("TODO Print");

			if (ua instanceof PrintJobUserAgent) {
				// TODO print
			}
		}

	}

	class InboxActionListener extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// Reihenfolge wichtig
			boxState = MessageBoxState.INBOX;
			updateInboxMessages();
			lbInbox.setForeground(Color.RED);
			lbEntwürfe.setForeground(Color.BLUE);
			boxState = MessageBoxState.INBOX;
		}

	}

	class DraftsActionListener extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// Reihenfolge wichtig
			boxState = MessageBoxState.DRAFTS;
			updateDraftsMessages();
			lbInbox.setForeground(Color.BLUE);
			lbEntwürfe.setForeground(Color.RED);
		}

	}

}
