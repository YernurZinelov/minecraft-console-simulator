package exceptions;

public class EntityDeadException extends RuntimeException {
    public EntityDeadException(String message) {
        super(message);
    }
}
