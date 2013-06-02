package gui.dialog;

import javax.swing.JOptionPane;

public class ErrorDialogManager {
	
	public static void showErrorDialog(String message){
    	JOptionPane.showConfirmDialog(null, message, "Fehler", JOptionPane.PLAIN_MESSAGE);
	}

}
