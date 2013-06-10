package gui.dialog;

import gui.helper.GridBagManager;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import message.Message;

import org.jdesktop.swingx.JXDatePicker;

/**
 * Reminderdialog auf welchen man Zeit und Datum für den Reminder auswählen kann
 * 
 */
public class ReminderDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    /**
     * Verwaltet das GUI
     */
    private GridBagManager guiManager;

    /**
     * Fügt den Reminder hinzu
     */
    private JButton okButton;

    /**
     * Entfernt den Reminder
     */
    private JButton removeButton;

    /**
     * Feld um die Zeit zu schreiben
     */
    private JFormattedTextField timeField;

    /**
     * Der Zeitformat. Ungültige Werte werden nicht akzeptiert
     */
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    /**
     * Der Datepicker. Man kann ein Datum auswählen
     */
    private JXDatePicker datePicker;

    /**
     * Die betreffende Nachricht
     */
    private Message message;

    /**
     * Der Kalender. Wichtig für die Umwandlung
     */
    private Calendar calendar = Calendar.getInstance();

    /**
     * 
     * @param message
     *            Auf diese Nachricht wird den Reminder gesetzt
     */
    public ReminderDialog(Message message) {
        this.message = message;
        this.guiManager = new GridBagManager(this);
        this.timeField = new JFormattedTextField(timeFormat);
        this.okButton = new JButton("Ok");
        this.removeButton = new JButton("Kein Reminder");
        this.datePicker = new JXDatePicker();
        configure();
    }

    /**
     * Baut den Dialog zusammen
     */
    private void configure() {
        okButton.addActionListener(new DateButtonListener());

        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                message.setReminder(null);
                dispose();
            }
        });

        // Aktuelle Datum und Zeit ins Feld setzen
        datePicker.setDate(new Date(System.currentTimeMillis()));
        timeField.setValue(new Date(System.currentTimeMillis()));

        guiManager.setX(0).setY(0).setWidth(1)
                .setFill(GridBagConstraints.HORIZONTAL)
                .setComp(new JLabel("Datum"));
        guiManager.setX(1).setY(0).setWidth(1)
                .setFill(GridBagConstraints.HORIZONTAL).setComp(datePicker);

        guiManager.setX(0).setY(1).setWidth(1)
                .setFill(GridBagConstraints.HORIZONTAL)
                .setComp(new JLabel("Zeit"));
        guiManager.setX(1).setY(1).setWidth(1)
                .setFill(GridBagConstraints.HORIZONTAL).setComp(timeField);

        guiManager.setX(0).setY(2).setWidth(1)
                .setFill(GridBagConstraints.HORIZONTAL).setComp(okButton);
        guiManager.setX(1).setY(2).setWidth(1)
                .setFill(GridBagConstraints.HORIZONTAL).setComp(removeButton);

        setTitle("Reminder erstellen");
        pack();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    /**
     * Der Listener für den Datebutton. Baut die Zeit zusammen
     * 
     */
    class DateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String[] timeText = timeField.getText().split(":");

            int hour = Integer.parseInt(timeText[0]);
            int min = Integer.parseInt(timeText[1]);

            calendar.set(Calendar.YEAR, 1970);
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, hour + 1);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);

            long time = calendar.getTime().getTime();
            long date = datePicker.getDate().getTime();

            message.setReminder(new Date(time + date));
            dispose();
        }
    }

}