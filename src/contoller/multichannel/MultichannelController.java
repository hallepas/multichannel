package contoller.multichannel;

import gui.frame.MainFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import devices.Device;

public class MultichannelController {

    private int frameCounter = 0;

    public void start() {
        World world = new World();
        for (Device device : world.getDevices()) {
            frameCounter++;
            new MainFrameRunner(device).run();
        }

    }

    public class MainFrameRunner implements Runnable {
        private Device device;

        public MainFrameRunner(Device device) {
            this.device = device;
        }

        @Override
        public void run() {
            MainFrame mf = new MainFrame(device);
            mf.setVisible(true);

            mf.addWindowListener(new WindowAdapter() {

                public void windowClosing(WindowEvent e) {
                    frameCounter--;

                    if (frameCounter == 0) {
                        // Killt das Programm
                        System.exit(0);
                    }
                    ;
                }
            });

        }
    }

}
