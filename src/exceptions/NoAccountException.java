package exceptions;

public class NoAccountException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NoAccountException(String message){
	super(message);
    }
}
