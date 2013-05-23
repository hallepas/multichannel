package exceptions;

public class NoAccountException extends RuntimeException {
	public NoAccountException(String message){
		super(message);
	}
}
