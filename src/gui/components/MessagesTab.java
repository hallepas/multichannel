package gui.components;

import gui.dialog.MessageDialog;
import gui.font.MessageFont;
import gui.helper.GridBagManager;
import gui.listener.action.AttachementActionListener;
import gui.table.cell.renderer.AttachmentCellRenderer;
import gui.table.cell.renderer.DateCellRenderer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.html.HTMLEditorKit;

import message.Attachment;
import message.Message;
import message.MessageType;
import message.MessageWithSubjectAndAttachment;

import org.jdesktop.swingx.JXHyperlink;

import table.model.MessageTableModel;
import clients.MessageClient;
import devices.Computer;
import devices.Device;

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
	private JButton replyButton;

	private String tabTitle;
	private List<Message> messages;
	private MessageClient messageClient;
	private MessageTableModel tableModel;
	private MessageBoxState boxState = MessageBoxState.INBOX;
	private BoxPorpertiesPanel boxPorpertiesPanel;
	private Device device;
	private AttachementActionListener attachementActionListener;

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
		this.replyButton = new JButton("Antworten");
		this.printButton = new JButton(this.messageType.getTypeName() + " drucken");
		this.tabTitle = messageType.getTypeName();
		this.device = device;

		this.lbInbox = new JXHyperlink(new InboxActionListener());
		this.lbEntwürfe = new JXHyperlink(new DraftsActionListener());

		this.lbInbox.setText("Inbox");
		this.lbEntwürfe.setText("Entwürfe");
		this.boxPorpertiesPanel = new BoxPorpertiesPanel(messageType, messageClient, lbInbox, lbEntwürfe, createButton, deleteButton, printButton, attachementButton, replyButton);
		this.messageClient.addObserver(new UpdateListener());
		this.attachementActionListener = new AttachementActionListener(messagesTable, messages);

		configureFrame();
	}

	private void configureFrame() {

		messagesTable.getColumnModel().getColumn(0).setCellRenderer(new DateCellRenderer());
		// Am Anfang ist der Inbox selektiert
		lbInbox.setForeground(Color.RED);
		lbEntwürfe.setForeground(Color.BLUE);

		// Um den Text schön darzustellen (Fett, Kursiv, Abbruch etc.)
		HTMLEditorKit eKit = new HTMLEditorKit();
		messageTextField.setEditable(false);
		messageTextField.setEditorKit(eKit);
		
		TableRowSorter<MessageTableModel> sorter = new TableRowSorter<MessageTableModel>();
		messagesTable.setRowSorter(sorter);
		sorter.setModel(tableModel);

		sorter.setComparator(0, new Comparator<Date>() {

			@Override
			public int compare(Date o1, Date o2) {
				return o1.compareTo(o2);
			}
		});
		
		sorter.sort();
		
		boxPorpertiesPanel.setBorder(new TitledBorder("Eigenschaften"));
		attachementButton.addActionListener(attachementActionListener);

		if (messageType.instance() instanceof MessageWithSubjectAndAttachment) {
			messagesTable.getColumnModel().getColumn(3).setPreferredWidth(20);
			messagesTable.getColumnModel().getColumn(3).setCellRenderer(new AttachmentCellRenderer());
		}

		messagesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				attachementActionListener.updateMessages(messages);

				// Nur Entwürfe darf man bearbeiten
				if (!boxState.equals(MessageBoxState.DRAFTS)) {
					return;
				}
				
				
				if (e.getClickCount() == 2&&messagesTable.getSelectedRow()>-1) {
					
					int selectedRow=	messagesTable.convertRowIndexToModel(messagesTable.getSelectedRow());

						Message message = messages.get(selectedRow);
						MessageDialog mf = new MessageDialog(message, messageType, messageClient, true);
						mf.setVisible(true);
						updateMessageBoxes();
						tableModel.refresh();
						messagesTable.repaint();
				}
			}
		});

		messagesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				if (messagesTable.getSelectedRow() == -1) {
					return;
				}

				int selectedRow=	messagesTable.convertRowIndexToModel(messagesTable.getSelectedRow());
				Message m = messages.get(selectedRow);
				String subjectText = "";

				if (m instanceof MessageWithSubjectAndAttachment) {
					subjectText = "<br<b>Betreff: </b>" + ((MessageWithSubjectAndAttachment) m).getSubject() + "";
				}

				String toList = "";

				if (m.getTo() != null) {
					toList = m.getTo().toString();
				}
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

		replyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (messagesTable.getSelectedRow() == -1) {
					return;
				}

				int selectedRow=	messagesTable.convertRowIndexToModel(messagesTable.getSelectedRow());
				Message message = messages.get(selectedRow);
				Message newMessage = messageClient.newMessage(messageType);
				newMessage.setDate(null);
				newMessage.setMessage(message.getMessage());

				if(message instanceof MessageWithSubjectAndAttachment){
					((MessageWithSubjectAndAttachment) newMessage).setSubject(	((MessageWithSubjectAndAttachment) message).getSubject());
					((MessageWithSubjectAndAttachment) newMessage).fillAttachements((ArrayList)((MessageWithSubjectAndAttachment) message).getAttachments());
				}
				
				ArrayList<String> to = new ArrayList<String>();
				to.add(message.getFrom());
				newMessage.setTo(to);

				MessageDialog mf = new MessageDialog(newMessage, messageType, messageClient, false);
				mf.setVisible(true);
			}
		});

		printButton.addActionListener(new PrintActionLIstener());
		deleteButton.addActionListener(new DeleteActionListener());
		messageTextField.setFont(MessageFont.MESSAGE_FONT);

		guiManager.setX(0).setY(0).setWidth(6).setScrollPanel().setComp(messagesTable);
		guiManager.setX(6).setY(0).setWidth(8).setWeightX(8).setScrollPanel().setComp(messageTextField);
		guiManager.setX(14).setY(0).setWidth(1).setComp(boxPorpertiesPanel);

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

				int selectedRow=	messagesTable.convertRowIndexToModel(i);
				Message m = messages.get(selectedRow);

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
		attachementActionListener.updateMessages(messages);
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

	public class UpdateListener implements Observer {
		@Override
		public void update(Observable o, Object arg) {
			updateInboxMessages();
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
			replyButton.setVisible(true);
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
			replyButton.setVisible(false);
		}

	}

}
