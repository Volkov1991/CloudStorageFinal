package netology.cloudstoragefinal.exception;

import netology.cloudstoragefinal.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    AtomicInteger errorResponseId = new AtomicInteger(1);

    @ExceptionHandler(ErrorGettingFileList.class)
    public ResponseEntity<ErrorResponse> errorFileListHandler(ErrorGettingFileList e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), errorResponseId.getAndIncrement()),
                INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorUploadFile.class)
    public ResponseEntity<ErrorResponse> errorUploadHandler(ErrorUploadFile e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), errorResponseId.getAndIncrement()),
                INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<ErrorResponse> inputDataHandler(ErrorInputData e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), errorResponseId.getAndIncrement()),
                BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedError.class)
    public ResponseEntity<ErrorResponse> unauthorizedErrorHandler(UnauthorizedError e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), errorResponseId.getAndIncrement()),
                UNAUTHORIZED);
    }

    @ExceptionHandler(ErrorBadCredentials.class)
    public ResponseEntity<ErrorResponse> badCredentials(UnauthorizedError e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage(), errorResponseId.getAndIncrement()),
                BAD_REQUEST);
    }
}
