package MessageServer;

public class PrintServer extends MessageServer {
	Hostname<String> hostname = new Hostname<String>();
	ArrayList<PrintQueue> queues = new ArrayList<PrintQueue>();
	
	public void selectQueue() {
		// eine Queue wird gewählt
	}
	public void startPrintJob () {
		// Der Druck wird gestartet
	}

}
