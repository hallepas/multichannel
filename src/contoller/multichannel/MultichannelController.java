package contoller.multichannel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import devices.Device;
import gui.frame.MainFrame;

public class MultichannelController {

	public void start() {
		World world = new World();
		for (Device device : world.getDevices()) {
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

			mf.addWindowStateListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
				}
			});
		}
	}

}
