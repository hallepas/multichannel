package gui.components;

import gui.font.MessageFont;
import gui.helper.GridBagManager;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

import org.jdesktop.swingx.JXHyperlink;

import message.Message;
import message.MessageType;
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
		
	public MessagesTab(List<Message> messages, MessageType messageType) {
		this.messages = messages;
		this.panelProperties = new  JPanel();
		this.messageType = messageType;
		this.guiManager = new GridBagManager(this);
		this.guiManagerPropertiesPanel = new GridBagManager(panelProperties);
		this.messageTextField = new JTextPane();
		this.messagesTable = new JTable(new MessageTableModel(messages, messageType));
//TODO Spalte Anhang: Checkbox anzeigen
		this.createButton = new JButton(this.messageType.getTypeName() + " erstellen");
		this.deleteButton = new JButton(this.messageType.getTypeName() + " löschen");
		this.printButton = new JButton(this.messageType.getTypeName() +" drucken");
		this.tabTitle = messageType.getTypeName();
		
		this.lbInbox = new JXHyperlink(null);
		this.lbEntwürfe = new JXHyperlink(null);
		
		this.lbInbox.setName("Inbox");
		this.lbEntwürfe.setName("Entwürfe");
		
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
	}
	
	
	private void configureFrame() {

		createPropertiesPanel();
		
		messagesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Message m = messages.get(messagesTable.getSelectedRow());
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
		guiManager.setX(6).setY(0).setWidth(6).setWeightX(10).setScrollPanel().setComp(messageTextField);
		guiManager.setX(12).setY(0).setWidth(1).setScrollPanel().setComp(panelProperties);

	}

	public String getTabTitle(){
		return tabTitle;
	}
	
}
