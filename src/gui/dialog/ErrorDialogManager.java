package gui.dialog;

import javax.swing.JOptionPane;


/**
 * Zeigt Fehler in einem Dialog
 *
 */
public class ErrorDialogManager {
	
	/**
	 * @param message  Zeigt den Message in einem JOptionPane
	 */
	public static void showErrorDialog(String message){
    	JOptionPane.showConfirmDialog(null, message, "Fehler", JOptionPane.PLAIN_MESSAGE);
	}

}
