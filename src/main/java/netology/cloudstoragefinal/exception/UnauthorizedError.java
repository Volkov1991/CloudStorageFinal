package netology.cloudstoragefinal.exception;

public class UnauthorizedError extends RuntimeException {
    public UnauthorizedError(String message) {
        super(message);
    }
}
