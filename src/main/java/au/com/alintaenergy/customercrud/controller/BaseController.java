package au.com.alintaenergy.customercrud.controller;

import au.com.alintaenergy.customercrud.constant.ErrorConstants;
import au.com.alintaenergy.customercrud.exception.ApiException;
import au.com.alintaenergy.customercrud.model.ErrorResponse;
import au.com.alintaenergy.customercrud.model.ValidationErrorResponse;
import au.com.alintaenergy.customercrud.model.ValidationViolation;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;
import java.util.List;

public class BaseController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();

        errorResponse.setCode(ErrorConstants.ERROR_CODE_VALIDATION_VIOLATIONS);
        errorResponse.setMessage(ErrorConstants.ERROR_MESSAGE_VALIDATION_VIOLATIONS);
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorResponse.addViolation(new ValidationViolation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return errorResponse;
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidFormatException(InvalidFormatException exception) throws InvalidFormatException {
        if (exception.getCause() instanceof DateTimeParseException dtpException) {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse();
            errorResponse.setCode(ErrorConstants.ERROR_CODE_VALIDATION_VIOLATIONS);
            errorResponse.setMessage(ErrorConstants.ERROR_MESSAGE_VALIDATION_VIOLATIONS);
            errorResponse.addViolation(new ValidationViolation(convertPathReferenceListToString(exception.getPath()), dtpException.getLocalizedMessage()));

            return errorResponse;
        }
        // Not handling any other InvalidFormatException exceptions
        throw exception;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        if (exception.getCause() instanceof NumberFormatException) {
            return new ErrorResponse(ErrorConstants.ERROR_CODE_BAD_REQUEST, exception.getLocalizedMessage());
        }
        // Not handling any other MethodArgumentTypeMismatchException exceptions
        throw exception;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(buildErrorResponseFromApiException(exception));
    }

    private ErrorResponse buildErrorResponseFromApiException(ApiException apiException) {
        return new ErrorResponse(apiException.getCode(), apiException.getMessage());
    }

    private String convertPathReferenceListToString(List<JsonMappingException.Reference> path) {
        StringBuilder fieldSb = new StringBuilder();
        for (JsonMappingException.Reference ref : path) {
            if (ref.getFieldName() != null) {
                if (!fieldSb.isEmpty()) {
                    fieldSb.append(".");
                }
                fieldSb.append(ref.getFieldName());
            } else if (ref.getIndex() >= 0) {
                fieldSb.append("[").append(ref.getIndex()).append("]");
            }
        }
        return fieldSb.toString();
    }
}
