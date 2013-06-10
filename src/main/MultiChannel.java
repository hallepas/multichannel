package main;

import java.util.logging.LogManager;

import javax.swing.UIManager;

import contoller.multichannel.MultichannelController;

public class MultiChannel {

    /**
     * @param args
     */
    public static void main(String[] args) {

        System.setProperty("java.util.logging.config.file",
                "logging.properties");
        try {
            LogManager.getLogManager().readConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Look and Feel vom System übernehmen
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        MultichannelController mc = new MultichannelController();
        mc.start();
    }

}
