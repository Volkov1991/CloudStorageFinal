package netology.cloudstoragefinal.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorMessage {
    ERROR_INPUT_DATA("Error input data"),
    ERROR_UNAUTHORIZED("Unauthorized error"),
    ERROR_DELETE_FILE("Error delete file"),
    ERROR_UPLOAD_FILE("Error upload file"),
    ERROR_FILE_LIST("Error getting file list"),
    ERROR_BAD_CREDENTIALS("Bad credentials");
    private final String message;

    @Override
    public String toString() {
        return this.message;
    }
}
