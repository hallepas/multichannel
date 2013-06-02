package contoller.multichannel;

import devices.Device;
import gui.frame.MainFrame;

public class MultichannelController {

    public void start() {
        World world = new World();
        for(Device device : world.getDevices()){
            new MainFrameRunner(device).run();
        }
        
    }
    
    public class MainFrameRunner implements Runnable {
        private Device device;
        
        public MainFrameRunner(Device device) {
            this.device = device;
        }
        
        @Override
        public void run(){
            new MainFrame(device).setVisible(true);
        }
    }

}
