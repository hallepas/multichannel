package clients;


public class EmailUserAgent<Account> extends UserAgent{
	private Account emailAccount;

	public Account getEmailAccount() {
		return emailAccount;
	}

	public void setEmailAccount(Account emailAccount) {
		this.emailAccount = emailAccount;
	} 
}
