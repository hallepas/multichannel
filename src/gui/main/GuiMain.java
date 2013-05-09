package gui.main;

import gui.components.MessageBoxFrame;

import javax.swing.UIManager;

public class GuiMain {

	// TODO Diese Klasse ist nur zum testen der Gui Klassen. Package und Klasse
	// wieder löschen

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MessageBoxFrame mf = new MessageBoxFrame("Email");
		mf.setVisible(true);
	}

}
