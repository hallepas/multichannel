package exceptions;

/**
 * Diese Exception wird geworfen, wenn man versucht, zu drucken, obwohl kein
 * Drucker angeschlossen ist.
 */
public class NoDeviceException extends NoAccountException {

    private static final long serialVersionUID = 1L;

    public NoDeviceException(String message) {
        super(message);
    }
}
