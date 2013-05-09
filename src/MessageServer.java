import java.util.Map;


public class MessageServer {
	
	private Map<String, MessageClient> accountsOnline; 
	private Map<String, Object> accounts;
	
	public Map<String, MessageClient> getAccountsOnline() {
		return accountsOnline;
	}
	public void setAccountsOnline(Map<String, MessageClient> accountsOnline) {
		this.accountsOnline = accountsOnline;
	}
	public Map<String, Object> getAccounts() {
		return accounts;
	}
	public void setAccounts(Map<String, Object> accounts) {
		this.accounts = accounts;
	} 
}
