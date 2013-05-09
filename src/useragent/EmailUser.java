package useragent;

public class EmailUser<Account> extends UserAgent<Object>{
	private Account emailaccount;

	public Account getEmailaccount() {
		return emailaccount;
	}

	public void setEmailaccount(Account emailaccount) {
		this.emailaccount = emailaccount;
	} 
	
	
}
