package gui.components;

import gui.dialog.MessageDialog;
import gui.font.MessageFont;
import gui.helper.GridBagManager;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;

import message.Attachment;
import message.Message;
import message.MessageType;
import message.MessageWithSubjectAndAttachment;

import org.jdesktop.swingx.JXHyperlink;

import table.model.MessageTableModel;
import clients.MessageClient;
import clients.useragents.PrintJobUserAgent;
import clients.useragents.UserAgent;
import devices.Computer;
import devices.Device;
import gui.listener.action.AttachementActionListener;
import gui.table.cell.renderer.AttachmentCellRenderer;

public class MessagesTab extends JComponent {

	enum MessageBoxState {
		DRAFTS, INBOX;
	}

	private static final long serialVersionUID = 1L;

	private MessageType messageType;
	private GridBagManager guiManager;
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
	private MessageClient messageClient;
	private MessageTableModel tableModel;
	private MessageBoxState boxState = MessageBoxState.INBOX;
	private BoxPorpertiesPanel boxPorpertiesPanel;
	private Device device;

	public MessagesTab(Device device, MessageClient messageClient, MessageType messageType) {
		this.messageClient = messageClient;
		this.messageType = messageType;
		this.messages = MessageClient.getOnlyType(messageClient.getMessagesFromInbox(), messageType);
		this.guiManager = new GridBagManager(this);
		this.messageTextField = new JTextPane();
		this.tableModel = new MessageTableModel(messages, messageType);
		this.messagesTable = new JTable(tableModel);
		this.createButton = new JButton(this.messageType.getTypeName() + " erstellen");
		this.deleteButton = new JButton(this.messageType.getTypeName() + " löschen");
		this.attachementButton = new JButton("Anhang speichern");
		this.printButton = new JButton(this.messageType.getTypeName() + " drucken");
		this.tabTitle = messageType.getTypeName();
		this.device = device;

		this.lbInbox = new JXHyperlink(new InboxActionListener());
		this.lbEntwürfe = new JXHyperlink(new DraftsActionListener());

		this.lbInbox.setText("Inbox");
		this.lbEntwürfe.setText("Entwürfe");
		this.boxPorpertiesPanel = new BoxPorpertiesPanel(messageType, messageClient, lbInbox, lbEntwürfe, createButton, deleteButton, printButton, attachementButton);
		this.messageClient.addObserver(this.tableModel.getUpdateListener());
		
		configureFrame();
	}

	private void configureFrame() {

		// Am Anfang ist der Inbox selektiert
		lbInbox.setForeground(Color.RED);
		lbEntwürfe.setForeground(Color.BLUE);

		// Um den Text schön darzustellen (Fett, Kursiv, Abbruch etc.)
		HTMLEditorKit eKit = new HTMLEditorKit();
		messageTextField.setEditable(false);
		messageTextField.setEditorKit(eKit);
		messagesTable.setAutoCreateRowSorter(true);
		boxPorpertiesPanel.setBorder(new TitledBorder("Eigenschaften"));

		attachementButton.addActionListener(new AttachementActionListener(messagesTable, messages));

		if (messageType.instance() instanceof MessageWithSubjectAndAttachment) {
			messagesTable.getColumnModel().getColumn(3).setPreferredWidth(20);
			messagesTable.getColumnModel().getColumn(3).setCellRenderer(new AttachmentCellRenderer());
		}

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

				if (m.getTo() != null) {
					toList = m.getTo().toString();
				}
				// TODO tolist schöner darstelllen
				messageTextField.setText("<html><b>Von:</b> " + m.getFrom() + "<br><b>An:</b>" + toList + subjectText + "<br><br><br>" + m.getMessage() + "</html>");
			}
		});

		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
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
		guiManager.setX(14).setY(0).setWidth(1).setScrollPanel().setComp(boxPorpertiesPanel);

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
			
			if (device instanceof Computer) {
				JOptionPane.showConfirmDialog(null, "Ihre Nachricht wurde gedruckt.", "Drucken", JOptionPane.PLAIN_MESSAGE);
				((Computer) device).printMessage(m);
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
