package clients.credentials;

/**
 * Login Credentials für SMS und MMS Server
 *
 */
public class IMEI implements Credentials {
    private static final long serialVersionUID = 1L;
    private long imei;

    public IMEI(long imei) {
	this.imei = imei;
    }
    public long getImei() {
	return imei;
    }

    @Override 
    public boolean equals(Object o) {
	if ( ! o.getClass().equals(getClass()) )
	    return false;
	if ( o == this )
	    return true;
	IMEI that = (IMEI) o;
	return this.imei == that.getImei();
    }

    @Override
    public int hashCode(){
	return (int) imei % Integer.MAX_VALUE;
    }

}
