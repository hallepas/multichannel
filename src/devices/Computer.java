package devices;
import clients.MessageClient;
import message.*;


/**
 * Ein Computer kann Emails verarbeiten und drucken.
 *
 */
public class Computer extends Device {
	/**
	 * Konstruktor weist Email und Print Funktion zu.
	 */
	public Computer() {
		super(new MessageClient(new MessageType[]{MessageType.EMAIL, MessageType.PRINT}));
	}
	
	public EmailMessage newEmail(){
		return (EmailMessage) newMessage(MessageType.EMAIL);
	}
	public PrintJobMessage newPrintJob(){
		return (PrintJobMessage) newMessage(MessageType.PRINT);
	}
	

}
