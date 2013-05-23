package main;

import javax.swing.UIManager;

import contoller.multichannel.MultichannelController;


public class MultiChannel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Look and Feel vom System übernehmen
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MultichannelController mc = new MultichannelController();
		mc.start();
	}

}
