package devices;

import java.util.Random;

import message.MMSMessage;
import message.MessageType;
import message.SMSMessage;

import clients.MessageClient;

public abstract class Cellphone extends Device {
    // Die imei Nummer wird zur Authentifizierung am Server verwendet.
    private final long imei;

    public Cellphone(MessageClient client, String deviceName) {
        super(client, deviceName);
        // Sei mal zufällig generiert pro Device.
        this.imei = new Random().nextLong();
    }

    public long getImei() {
        return this.imei;
    }

    public SMSMessage newSMS() {
        return (SMSMessage) newMessage(MessageType.SMS);
    }

    public MMSMessage newMMS() {
        return (MMSMessage) newMessage(MessageType.MMS);
    }

}
