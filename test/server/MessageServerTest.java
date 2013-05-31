package server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MessageServerTest {
    private static final TheInternet internet = TheInternet.goOnline();

    @Before
    public void setUp() throws Exception {
        internet.startOver();   
    }

    @Test
    public void testMobileMessageServer() {
        MessageServer swisscom = new MobileMessageServer("Swisscom", "4179");
        assertEquals("4179", swisscom.getDomainForAddress("41791234567"));
        assertEquals("4179", swisscom.getDomainForAddress("+41791234567"));
        assertEquals("4179", swisscom.getDomainForAddress("0791234567"));
    }
    
    public void testEmailServer(){
        MessageServer gmail = new EmailServer("GMail", "gmail.com");
        assertEquals("gmail.com", gmail.getDomainForAddress("sb@gmail.com"));
        assertEquals("yahoo.de", gmail.getDomainForAddress("sb@yahoo.de"));
    }

}
