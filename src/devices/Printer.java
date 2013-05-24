package devices; 

import java.util.List;

import message.Message;
import message.Status;

import clients.ServerProxy;

/**
 * Ein Drucker kann PrintJobMessages ausdrucken.
 * Er kann aber keine Nachrichten speichern, um sie in einer Liste anzuzeigen.
 *
 */
public class Printer {

	public Printer() { 
		super();
	}
	
	public void print(Message message){
		System.out.println(message);
	}
	public ServerProxy getProxy(){
		return new PrinterProxy();
	}
	
	public class PrinterProxy implements ServerProxy {

		@Override
		public List<Message> poll() {
			return null;
		}

		@Override
		public Status put(Message message) {
			print(message);
			return new Status(200, "printed");
		}

		@Override
		public String getServerName() {
			return "Printer@localhost";
		}

		@Override
		public Status logout() {
			return new Status(501, "Disconnect at the computer.");
		}
		
	}
	
	
}
