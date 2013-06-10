package devices;

import static org.junit.Assert.*;

import message.MMSMessage;
import message.SMSMessage;

import org.junit.Before;
import org.junit.Test;

public class FeaturePhoneTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        FeaturePhone nokia = new FeaturePhone("Nokia 6110");
        assertTrue("FeaturePhone kann SMS schicken",
                nokia.newSMS() instanceof SMSMessage);
        assertTrue("FeaturePhone kann MMS schicken",
                nokia.newMMS() instanceof MMSMessage);
    }

}
