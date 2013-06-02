package contoller.multichannel;

import devices.Device;
import gui.frame.MainFrame;

public class MultichannelController {

    public void start() {
        World world = new World();
        for(Device device : world.getDevices()){
            new MainFrame(device).setVisible(true);
        }
        
    }

}
