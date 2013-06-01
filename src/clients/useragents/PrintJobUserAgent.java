package clients.useragents;

import java.util.List;

import clients.ClientProxy;

import message.Message;
import message.Status;

import server.ServerSocket;

import devices.Printer;
import exceptions.NoAccountException;
import exceptions.NoDeviceException;

/**
 * Diese Klasse ist etwas spezieller. sendMessages führt direkt zu einem Printer
 * Socket. ReceiveMessages macht hier keinen Sinn.
 * 
 */

public class PrintJobUserAgent extends UserAgent  {
    private ServerSocket printer;

    @Override
    public Status sendMessage(Message message) {
	checkForAccount();
	return printer.put(message);
    }

    @Override  // Unless this is a scanner as well.
    public List<Message> receiveMessages() {
	return null;
    }

    public void checkForAccount() throws NoAccountException {
	if (printer == null) {
	    throw new NoDeviceException("No printer connected.");
	}
    }

    public ServerSocket getPrinter() {
	return printer;
    }

    public void setPrinter(ServerSocket printer) {
	this.printer = printer;
    }

    public Status connect(Printer printer) {
	this.printer = printer.getProxy();
	return new Status(200, "printer connected");
    }
    @Override
    public Status login(ClientProxy client){
        if(this.printer != null){
            return new Status(200, "Printer online.");
        } else {
            return new Status(500, "No printer connected");
        }
        
    }
    
}