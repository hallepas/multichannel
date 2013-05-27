package clients;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import message.*;

import org.junit.Before;
import org.junit.Test;

import devices.Printer;

public class ClientTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testMessageFilter() {
        EmailMessage email = new EmailMessage();
        email.setSubject("Test email");
        MMSMessage mms = new MMSMessage();
        mms.setSubject("Test mms");
        List<Message> messages = new ArrayList<Message>();
        messages.add(email);
        messages.add(mms);
        assertEquals(messages.size(), 2);
        List<Message> filtered = MessageClient.getOnlyType(messages, MessageType.EMAIL);
        assertTrue(filtered.contains(email));
        assertFalse(filtered.contains(mms));
    }

    @Test
    public void testMessageCreation() {
        MessageClient client = new MessageClient(new MessageType[]{MessageType.EMAIL, MessageType.PRINT});
        Message message = client.newMessage(MessageType.EMAIL);
        assertTrue("Email Message", message instanceof EmailMessage);
        message = client.newMessage(MessageType.PRINT);
        assertTrue("Print Job message", message instanceof PrintJobMessage);
    }

}
