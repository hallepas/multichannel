package clients;


public class SMSUserAgent<Account> extends UserAgent{
	private Account mmsAccount;

	public Account getMmsAccount() {
		return mmsAccount;
	}

	public void setMmsAccount(Account mmsAccount) {
		this.mmsAccount = mmsAccount;
	} 
	
}
