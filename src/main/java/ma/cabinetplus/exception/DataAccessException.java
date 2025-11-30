package ma.cabinetplus.exception;

/**
 * Levée quand les opérations de base de données échouent
 */
public class DataAccessException extends AppException {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(String message) {
        super(message);
    }
}
