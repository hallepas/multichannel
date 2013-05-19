package message;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessageTest {

	@Test
	public void test() {
		Message email = MessageType.EMAIL.instance();
		assertTrue(email instanceof EmailMessage);
		Message sms = MessageType.SMS.instance();
		assertTrue(sms instanceof SMSMessage);
		Message mms = MessageType.MMS.instance();
		assertTrue(mms instanceof MMSMessage);
		Message druck = MessageType.PRINT.instance();
		assertTrue(druck instanceof PrintJobMessage);
	}

}
