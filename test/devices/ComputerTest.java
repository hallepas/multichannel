package devices;

import static org.junit.Assert.*;

import message.EmailMessage;
import message.MessageType;
import message.PrintJobMessage;

import org.junit.Before;
import org.junit.Test;

public class ComputerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Computer computer = new Computer();
		assertTrue("Computer kann Email senden", computer.newEmail() instanceof EmailMessage);
		assertTrue("Computer kann drucken", computer.newPrintJob() instanceof PrintJobMessage);
		assertNull("Computer kann keine SMS schicken", computer.newMessage(MessageType.SMS));
		assertNull("Computer kann keine MMS schicken", computer.newMessage(MessageType.MMS));
	}

}
