package ma.cabinetplus.exception;

/**
 * Thrown when an entity is not found in the database
 */
public class EntityNotFoundException extends AppException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
