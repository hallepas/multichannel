package devices; 

import java.util.List;

import server.ServerSocket;

import message.Message;
import message.Status;


/**
 * Ein Drucker kann PrintJobMessages ausdrucken.
 * Er kann aber keine Nachrichten speichern, um sie in einer Liste anzuzeigen.
 *
 */
public class Printer {
    private final String deviceName;

    public Printer(String deviceName) { 
	super();
	this.deviceName = deviceName;
    }

    public String getDeviceName(){
	return deviceName;
    }

    public String toString(){
	return this.getClass().getSimpleName() + " " + this.deviceName;
    }

    public void print(Message message){
	System.out.println(message);
    }
    public ServerSocket getProxy(){
	return new PrinterProxy();
    }

    public class PrinterProxy implements ServerSocket {

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
