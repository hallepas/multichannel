package server;

import message.Status;
import clients.credentials.Credentials;
import clients.credentials.IMEI;


public class MobileMessageServer extends MessageServer {

    public MobileMessageServer(String networkName, String networkCode){
        super(networkName, networkCode);
        internet.registerDomain(networkCode, new ServerInfo());
    }

    /**
     * Der Domain für ein mobiles Netzwerk ist die Kombination aus 
     * internationaler Vorwahl und nationaler Vorwahl. Z.B. 4179.
     */
    @Override
    protected String getDomainForAddress(String number) {
        if(number.length() == 11) {
            // internationale Nummer
            return number.substring(0,4);
        } 
        else if (number.length() == 9){
            // nationale Nummer
            return getDomain().substring(0, 2) + number.substring(0,2);
        } else {
            return "";
        }
    }


    public Status register(String name, Credentials credentials) {
        if (!(credentials instanceof IMEI)) {
            return new Status(403, "You can only connect using IMEI");
        }
        return super.register(name, credentials);
    }

    @Override
    protected ServerSocket getSocket(String name) {
        return new Socket(name);
    }

}
