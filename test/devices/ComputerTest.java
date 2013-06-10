package devices;

import static org.junit.Assert.*;

import message.EmailMessage;
import message.MessageType;
import message.PrintJobMessage;
import message.Status;

import org.junit.Before;
import org.junit.Test;

public class ComputerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCreateMessages() {
        Computer computer = new Computer("iMac");
        assertTrue("Computer kann Email senden",
                computer.newEmail() instanceof EmailMessage);
        assertTrue("Computer kann drucken",
                computer.newPrintJob() instanceof PrintJobMessage);
        assertNull("Computer kann keine SMS schicken",
                computer.newMessage(MessageType.SMS));
        assertNull("Computer kann keine MMS schicken",
                computer.newMessage(MessageType.MMS));
    }

    @Test
    public void testPrint() {
        Computer computer = new Computer("iMac");
        computer.connectPrinter(new Printer("Bubble Jet"));
        EmailMessage message = computer.newEmail();
        Status status = computer.printMessage(message);
        assertEquals("Nachricht gedruckt", status.getCode(), 200);
        assertTrue(status.getDescription().equals("printed"));
    }

}
