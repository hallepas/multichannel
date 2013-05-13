package clients;


public class MMSUserAgent<Account> extends UserAgent{
	private Account mmsAccount;

	public Account getMmsAccount() {
		return mmsAccount;
	}

	public void setMmsAccount(Account mmsAccount) {
		this.mmsAccount = mmsAccount;
	} 
}
