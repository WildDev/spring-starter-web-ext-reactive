package fun.wilddev.spring.web.controllers.responses.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.*;

/**
 * Error response schema
 */
@Setter
@Getter
@ToString
public class ErrorResponse {

    /**
     * Instantiates the class by {@code globalError} and {@code fieldErrors}
     *
     * @param globalError - global error text
     * @param fieldErrors - field errors schema array
     */
    public ErrorResponse(String globalError, List<FieldErrorResponse> fieldErrors) {

        this.globalError = globalError;
        this.fieldErrors = fieldErrors;
    }

    /**
     * Global error text
     */
    @JsonProperty
    private String globalError;

    /**
     * Field errors schema array
     */
    @JsonProperty
    private List<FieldErrorResponse> fieldErrors;
}
