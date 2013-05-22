package devices;

import static org.junit.Assert.*;

import message.EmailMessage;
import message.MMSMessage;

import message.SMSMessage;

import org.junit.Before;
import org.junit.Test;

public class SmartphoneTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Smartphone iphone = new Smartphone();
		assertTrue("Smartphone kann Email senden", iphone.newEmail() instanceof EmailMessage);
		assertTrue("Smartphone kann SMS schicken", iphone.newSMS() instanceof SMSMessage);
		assertTrue("Smartphone kann MMS schicken", iphone.newMMS() instanceof MMSMessage);
	}

}
