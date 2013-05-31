package server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import message.Status;
import clients.credentials.Credentials;
import clients.credentials.IMEI;


public class MobileMessageServer extends MessageServer {
    // Wir beschränken uns mal auf zehnstellige Telefonnummern.
    private static final Pattern INTERNATIONAL = Pattern.compile("^\\+?([1-9]\\d{3})\\d{7}$");
    private static final Pattern NATIONAL = Pattern.compile("^0([1-9]\\d)\\d{7}$");

    public MobileMessageServer(String networkName, String networkCode){
        super(networkName, networkCode);
        internet.registerDomain(networkCode, new ServerInfo());
    }

    /**
     * Der Domain für ein mobiles Netzwerk ist die Kombination aus 
     * internationaler Vorwahl und nationaler Vorwahl. Z.B. 4179.
     */
    @Override
    String getDomainForAddress(String number) {
        Matcher m = INTERNATIONAL.matcher(number);
        if(m.matches()){
            return m.group(1);
        }
        m = NATIONAL.matcher(number);
        if(m.matches()){
            return getDomain().substring(0, 2) + m.group(1);
        }
        return "";
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
