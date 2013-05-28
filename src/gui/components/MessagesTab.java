package gui.components;

import gui.font.MessageFont;
import gui.helper.GridBagManager;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;

import message.Message;
import message.MessageType;

import org.jdesktop.swingx.JXHyperlink;

import clients.MessageClient;

import table.model.MessageTableModel;

public class MessagesTab extends JComponent{

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
	private String tabTitle;
	private List<Message> messages;
	private JPanel panelProperties;
	private MessageClient messageClient;
	private MessageTableModel tableModel;
		
	public MessagesTab(MessageClient messageClient, MessageType messageType) {
		this.messageClient = messageClient;
		this.panelProperties = new  JPanel();
		this.messageType = messageType;
		this.messages =MessageClient.getOnlyType(messageClient.getMessagesFromInbox(), messageType);
		this.guiManager = new GridBagManager(this);
		this.guiManagerPropertiesPanel = new GridBagManager(panelProperties);
		this.messageTextField = new JTextPane();
		this.tableModel = new MessageTableModel(messages, messageType);
		this.messagesTable = new JTable(tableModel);
//TODO Spalte Anhang: Checkbox anzeigen
		this.createButton = new JButton(this.messageType.getTypeName() + " erstellen");
		this.deleteButton = new JButton(this.messageType.getTypeName() + " löschen");
		this.printButton = new JButton(this.messageType.getTypeName() +" drucken");
		this.tabTitle = messageType.getTypeName();
		
		this.lbInbox = new JXHyperlink(new InboxActionListener());
		this.lbEntwürfe = new JXHyperlink(new EntwuerfeActionListener());
		
		this.lbInbox.setText("Inbox");
		this.lbEntwürfe.setText("Entwürfe");
		
		// Um den Text schön darzustellen (Fett, Kursiv, Abbruch etc.)
		HTMLEditorKit eKit = new HTMLEditorKit();
		messageTextField.setEditable(false);
		messageTextField.setEditorKit(eKit);
		messagesTable.setAutoCreateRowSorter(true);
		panelProperties.setBorder(new TitledBorder("Eigenschaften"));
		
		configureFrame();
	}

	private void createPropertiesPanel(){
		guiManagerPropertiesPanel.setX(0).setY(0).setComp(lbInbox);
		guiManagerPropertiesPanel.setX(0).setY(1).setComp(lbEntwürfe);
		guiManagerPropertiesPanel.setX(0).setY(2).setFill(GridBagConstraints.HORIZONTAL).setComp(createButton);
		guiManagerPropertiesPanel.setX(0).setY(3).setFill(GridBagConstraints.HORIZONTAL).setComp(deleteButton);
		guiManagerPropertiesPanel.setX(0).setY(4).setFill(GridBagConstraints.HORIZONTAL).setComp(printButton);
		guiManagerPropertiesPanel.setX(0).setY(5).setWeightY(20).setHeight(10).setComp(new JLabel());
	}
	
	
	private void configureFrame() {

		createPropertiesPanel();
		
		messagesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = messagesTable.getSelectedRow();
				
				if(messagesTable.getSelectedRow()==-1){
					return;
				}
				
				Message m = messages.get(selectedRow);
				//TODO überprüfen ob Betreff vorhanden ist
				messageTextField.setText("<html><b>Von:</b> " + m.getFrom() + "<br><b>Betreff:</b>"  + "<br><br><br>" + m.getMessage() + "</html>");
			}
		});

		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MessageDialog mf = new MessageDialog(messageType);
				mf.setVisible(true);
			}
		});

		
		messageTextField.setFont(MessageFont.MESSAGE_FONT);

		guiManager.setX(0).setY(0).setWidth(6).setScrollPanel().setComp(messagesTable);
		guiManager.setX(6).setY(0).setWidth(8).setWeightX(15).setScrollPanel().setComp(messageTextField);
		guiManager.setX(14).setY(0).setWidth(1).setScrollPanel().setComp(panelProperties);

	}

	public String getTabTitle(){
		return tabTitle;
	}
	
	class InboxActionListener extends AbstractAction{

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			messages =MessageClient.getOnlyType(messageClient.getMessagesFromInbox(), messageType);
			tableModel.changeMessages(messages);
			messagesTable.repaint();
		}
		
	}
	

	class EntwuerfeActionListener extends AbstractAction{

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			messages =MessageClient.getOnlyType(messageClient.getDrafts(), messageType);
			tableModel.changeMessages(messages);
			messagesTable.repaint();
		}
		
	}
	
}
