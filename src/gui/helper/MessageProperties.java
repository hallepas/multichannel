package gui.helper;

import java.awt.Font;
import java.text.SimpleDateFormat;

/**
 * Enthält alle Eigenschaften welche für ein Mail notwendig sein kann
 * 
 * @author Milijana
 * 
 */
public class MessageProperties {

    /**
     * Für eine einheitliche Schrift
     */
    public static Font MESSAGE_FONT = new Font("Tahoma", 0, 11);

    /**
     * Für eine einheitliche Datumdarstellung
     */
    public static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
            "dd.MM.yyyy");

    /**
     * Für eine einheitliche Zeitdarstellung
     */
    public static SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("H:mm");

    /**
     * Für eine einheitliche Datum- und Zeitdarstellung
     */
    public static SimpleDateFormat DATE_AND_TIME_FORMATTER = new SimpleDateFormat(
            "dd.MM.yyyy H:mm");

}