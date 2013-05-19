package clients;

public class EmailServer extends MessageServer {

	public EmailServer() {
		super();
	}

	@Override
	public String getDomainForAddress(String name) {
		return name.split("@")[0];
	}

	@Override
	protected ServerProxy findServerForDomain(String domain) {
		return null;
	}

}
