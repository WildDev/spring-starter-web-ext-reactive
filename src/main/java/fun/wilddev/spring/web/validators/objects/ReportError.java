package fun.wilddev.spring.web.validators.objects;

import fun.wilddev.spring.web.exceptions.ErrorsException;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

/**
 * Convenience class for errors reporting purposes
 */
public class ReportError {

    /**
     * Object to be validated
     */
    private final Object target;

    /**
     * Private constructor
     *
     * @param target - object to be validated
     */
    private ReportError(Object target) {
        this.target = target;
    }

    /**
     * Factory method
     *
     * @param target - object to be validated
     * @return {@link ReportError} instance
     */
    public static ReportError of(@NonNull Object target) {
        return new ReportError(target);
    }

    /**
     * Validates a field
     *
     * @param field - the field
     * @param errorCode - error code
     * @param defaultMessage - default message
     * @return context pipeline's descriptor
     *
     * @param <R> - context pipeline's type
     */
    public <R> Mono<R> rejectField(String field, @NonNull String errorCode, String defaultMessage) {
        return Mono.error(ErrorsException.of(target).rejectValue().field(field)
                .errorCode(errorCode).defaultMessage(defaultMessage).build());
    }

    /**
     * Validates an object
     *
     * @param errorCode - error code
     * @param defaultMessage - default message
     * @return context pipeline's descriptor
     *
     * @param <R> - context pipeline's type
     */
    public <R> Mono<R> reject(@NonNull String errorCode, String defaultMessage) {
        return Mono.error(ErrorsException.of(target).reject()
                .errorCode(errorCode).defaultMessage(defaultMessage).build());
    }
}
