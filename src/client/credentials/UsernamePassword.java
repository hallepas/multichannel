package client.credentials;

import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Login Credentials für Email Server.
 * Das Passwort wird hier als SHA-1 Hash gespeichert.
 *
 */

public class UsernamePassword implements Credentials {
	private final String username;
	private final String password;
	
	public UsernamePassword(String username, String password) {
		this.username = username;
		this.password = DigestUtils.sha1Hex(password);
	}
	public String getUsername() {
		return username;
	}
	public String getPasswordHash() {
		return password;
	}
	
	
	@Override
	public boolean equals( Object o ) {
		if ( ! o.getClass().equals(getClass()) )
			return false;
		if ( o == this )
			return true;

		UsernamePassword that = (UsernamePassword) o;
		return this.username.equals(that.getUsername()) &&
			  this.password.equals(that.getPasswordHash());
	}
	@Override
	public int hashCode() {
		return this.username.hashCode()+this.password.hashCode();
	}
	
}
