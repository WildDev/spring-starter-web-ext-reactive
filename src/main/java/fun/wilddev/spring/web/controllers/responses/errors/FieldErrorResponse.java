package fun.wilddev.spring.web.controllers.responses.errors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

/**
 * Field error response schema
 */
@Setter
@Getter
@ToString
public class FieldErrorResponse {

    /**
     * Instantiates the class by {@code field} and {@code message}
     *
     * @param field - field reference
     * @param message - error text
     */
    public FieldErrorResponse(String field, String message) {

        this.field = field;
        this.message = message;
    }

    /**
     * Field reference
     */
    @JsonProperty
    private String field;

    /**
     * Error text
     */
    @JsonProperty
    private String message;
}
