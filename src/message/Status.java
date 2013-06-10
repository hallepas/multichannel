package message;

/**
 * Der Status dient zur Meldung, ob ein Vorgang erfolgreich war oder nicht. Er
 * bezieht sich immer nur auf den direkt nachfolgenden Schritt.
 * 
 */
public class Status {
    private final int code;
    private final String description;

    public Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "" + code + " " + description;
    }
}
