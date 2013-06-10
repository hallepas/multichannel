package gui.components;

import gui.helper.GridBagManager;

import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import message.MessageType;
import message.MessageWithSubjectAndAttachment;

import org.jdesktop.swingx.JXHyperlink;

import clients.MessageClient;

/**
 * Enthält alle Komponente welche man die Messages und Mailboxen bearbeiten
 * kann.
 * 
 */
public class BoxPorpertiesPanel extends JPanel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * Verwaltet das Panel
     */
    private GridBagManager guiManagerPropertiesPanel;

    /**
     * 
     * @param messageType
     *            Der Messagetype
     * @param messageClient
     *            Der MessageClient
     * @param lbInbox
     *            Der MailInbox-HyperLink
     * @param lbEntwürfe
     *            Der Maildrafts-HyperLink
     * @param createButton
     *            Der Create-Button
     * @param deleteButton
     *            Der Delete-Button
     * @param printButton
     *            Der Drucken-Button
     * @param attachementButton
     *            Der Button um die Anhänge herunterzuladen
     * @param replyButton
     *            Der Antwort-Button. Beim Klicken kann man auf eine Nachricht
     *            antworten
     */
    public BoxPorpertiesPanel(MessageType messageType,
            MessageClient messageClient, JXHyperlink lbInbox,
            JXHyperlink lbEntwürfe, JButton createButton, JButton deleteButton,
            JButton printButton, JButton attachementButton, JButton replyButton) {
        this.guiManagerPropertiesPanel = new GridBagManager(this);

        createPropertiesPanel(messageType, messageClient, lbInbox, lbEntwürfe,
                createButton, deleteButton, printButton, attachementButton,
                replyButton);
    }

    /**
     * Baut das Panel zusammen
     * 
     * @param messageType
     *            Der Messagetype
     * @param messageClient
     *            Der MessageClient
     * @param lbInbox
     *            Der MailInbox-HyperLink
     * @param lbEntwürfe
     *            Der Maildrafts-HyperLink
     * @param createButton
     *            Der Create-Button
     * @param deleteButton
     *            Der Delete-Button
     * @param printButton
     *            Der Drucken-Button
     * @param attachementButton
     *            Der Button um die Anhänge herunterzuladen
     * @param replyButton
     *            Der Antwort-Button. Beim Klicken kann man auf eine Nachricht
     *            antworten
     */
    private void createPropertiesPanel(MessageType messageType,
            MessageClient messageClient, JXHyperlink lbInbox,
            JXHyperlink lbEntwürfe, JButton createButton, JButton deleteButton,
            JButton printButton, JButton attachementButton, JButton replyButton) {
        guiManagerPropertiesPanel.setX(0).setWidth(0).setHeight(1).setY(0)
                .setWeightX(0).setComp(lbInbox);
        guiManagerPropertiesPanel.setX(0).setWidth(0).setHeight(1).setY(1)
                .setWeightX(0).setComp(lbEntwürfe);
        guiManagerPropertiesPanel.setX(0).setWidth(0).setHeight(1).setY(2)
                .setWeightX(0).setFill(GridBagConstraints.HORIZONTAL)
                .setComp(createButton);
        guiManagerPropertiesPanel.setX(0).setWidth(0).setHeight(1).setY(3)
                .setWeightX(0).setFill(GridBagConstraints.HORIZONTAL)
                .setComp(deleteButton);

        if (messageClient.canPrint()) {
            guiManagerPropertiesPanel.setX(0).setWidth(0).setY(4).setHeight(1)
                    .setWeightX(0).setFill(GridBagConstraints.HORIZONTAL)
                    .setComp(printButton);
        }

        if (messageType.instance() instanceof MessageWithSubjectAndAttachment) {
            guiManagerPropertiesPanel.setX(0).setWidth(0).setY(5).setHeight(1)
                    .setWeightX(0).setFill(GridBagConstraints.HORIZONTAL)
                    .setComp(attachementButton);
        }

        guiManagerPropertiesPanel.setX(0).setY(6).setWidth(0).setHeight(1)
                .setWeightX(0).setFill(GridBagConstraints.HORIZONTAL)
                .setComp(replyButton);
        guiManagerPropertiesPanel.setX(0).setY(7).setWidth(0).setHeight(1)
                .setWeightX(0).setWeightX(0).setWeightY(20).setHeight(10)
                .setComp(new JLabel());
    }
}
