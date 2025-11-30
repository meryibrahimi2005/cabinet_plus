package ma.cabinetplus.exception;

/**
 * Levée quand la validation métier échoue
 */
public class ValidationException extends AppException {
    public ValidationException(String message) {
        super(message);
    }
}
