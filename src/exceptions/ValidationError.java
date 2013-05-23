package exceptions;

public class ValidationError extends Exception {
	public ValidationError(String message) {
		super(message);
	}
}
