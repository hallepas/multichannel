package clients;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import message.*;
import exceptions.NoAccountException;

import org.junit.Test;

// http://www.vogella.com/articles/JUnit/article.html#juniteclipse


public class UserAgentTest {
	
	private List<UserAgent> createUAs() {
		List<UserAgent> userAgents = new ArrayList<UserAgent>();
		userAgents.add(new EmailUserAgent());
		return userAgents;
	}

	@Test
	public void testEmail() {
		EmailUserAgent userAgent = new EmailUserAgent();
		Message message = userAgent.newMessage();
		assertTrue("Email Message", message instanceof EmailMessage);
		
	}
	
	@Test
	public void testPrint() {
		PrintJobUserAgent userAgent = new PrintJobUserAgent();
		Message message = userAgent.newMessage();
		assertTrue("Print Job message", message instanceof PrintJobMessage);
		
	}
	
	/**
	 * Methoden sollen eine Exception werfen, wenn kein Account definiert ist.
	 */
	@Test
	public void testNoAccount() {
		List<UserAgent> userAgents = createUAs();
		for (UserAgent userAgent:userAgents) {
			userAgent.setAccount(null);
			try{
				userAgent.receiveMessages();
				fail("recieveMessage should raise exception if no account");
			} catch (NoAccountException e) {}
			try{
				userAgent.sendMessages();
				fail("sendMessages should raise exception if no account");
			} catch (NoAccountException e) {}
			try{
				userAgent.syncMessages();
				fail("syncMessages should raise exception if no account");
			} catch (NoAccountException e) {}
		}
		assertTrue("NoAccountException raised", true);
	}

}
