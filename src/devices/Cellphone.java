package devices;

import java.util.Random;

import clients.MessageClient;

public abstract class Cellphone extends Device {
    private final long imei;

    public Cellphone(MessageClient client, String deviceName) {
        super(client, deviceName);
        // Sei mal zufällig generiert pro Device.
        this.imei = new Random().nextLong();
    }
    
    public long getImei() {
        return this.imei;
    }

}
