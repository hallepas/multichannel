package clients.credentials;

/**
 * Login Credentials für SMS und MMS Server
 *
 */
public class IMEI implements Credentials {
    private static final long serialVersionUID = 1L;
    private final long imei;

    public IMEI(long imei) {
	this.imei = imei;
    }
    public long getImei() {
	return imei;
    }

    @Override 
    public boolean equals(Object o) {
	if ( !(o.getClass().equals(this.getClass())))  return false;
	if ( o == this )   return true;
	return this.imei == ((IMEI) o).getImei();
    }

    @Override
    public int hashCode(){
	return (int) imei % Integer.MAX_VALUE;
    }

}
