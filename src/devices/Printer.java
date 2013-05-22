package devices; 

import java.util.List;

import message.Message;
import message.Status;

import clients.ServerProxy;

/**
 * Ein Drucker kann PrintJobMessages ausdrucken.
 *
 */
public class Printer {

	public Printer() { }
	
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
		public Status put(List<Message> messages) {
			for (Message message:messages) {
				print(message);
			}
			return new Status(200, "printed");
		}
		
	}
	
	
}
