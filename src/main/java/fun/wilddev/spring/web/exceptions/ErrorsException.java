package fun.wilddev.spring.web.exceptions;

import fun.wilddev.spring.core.utils.ArrayUtils;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebInputException;

import org.springframework.validation.*;

/**
 * Reactive approach to work with {@link Errors}
 */
public class ErrorsException extends ServerWebInputException implements Errors {

    /**
     * The delegate
     */
    private final Errors delegate;

    /**
     * Private constructor
     *
     * @param delegate - the delegate
     */
    private ErrorsException(Errors delegate) {

        super("");
        this.delegate = delegate;
    }

    /**
     * Factory method
     *
     * @param target - target object
     * @return builder instance
     */
    public static ErrorsExceptionBuilder of(@NonNull Object target) {
        return new ErrorsExceptionBuilder(target);
    }

    /**
     * Builder implementation
     */
    public static class ErrorsExceptionBuilder {

        /**
         * Target object
         */
        private final Object target;

        /**
         * Referenced field
         */
        private String field;

        /**
         * Error code
         */
        private String errorCode;

        /**
         * Error args
         */
        private Object[] errorArgs;

        /**
         * Default message
         */
        private String defaultMessage;

        /**
         * Context action
         */
        private Action action;

        /**
         * Private constructor
         *
         * @param target - target object
         */
        private ErrorsExceptionBuilder(Object target) {

            this.target = target;
            this.defaultMessage = "";
        }

        /**
         * Context action enum
         */
        private enum Action {

            REJECT_DEFAULT,
            REJECT_VALUE
        }

        /**
         * Reject based on a field validation
         *
         * @return builder reference
         */
        public ErrorsExceptionBuilder rejectValue() {

            this.action = Action.REJECT_VALUE;
            return this;
        }

        /**
         * Reject based on an object validation
         *
         * @return builder reference
         */
        public ErrorsExceptionBuilder reject() {

            this.action = Action.REJECT_DEFAULT;
            return this;
        }

        /**
         * Set field
         *
         * @param field - field
         * @return builder reference
         */
        public ErrorsExceptionBuilder field(String field) {

            this.field = field;
            return this;
        }

        /**
         * Set error code
         *
         * @param errorCode - error code
         * @return builder reference
         */
        public ErrorsExceptionBuilder errorCode(String errorCode) {

            this.errorCode = errorCode;
            return this;
        }

        /**
         * Set error args
         *
         * @param errorArgs - error args
         * @return builder reference
         */
        public ErrorsExceptionBuilder errorArgs(Object[] errorArgs) {

            this.errorArgs = errorArgs;
            return this;
        }

        /**
         * Set default message
         *
         * @param defaultMessage - default message
         * @return builder reference
         */
        public ErrorsExceptionBuilder defaultMessage(String defaultMessage) {

            this.defaultMessage = defaultMessage;
            return this;
        }

        /**
         * Instantiates the object
         *
         * @return constructed object
         */
        public ErrorsException build() {

            Errors errors = new BeanPropertyBindingResult(target, "target");

            if (!StringUtils.hasText(errorCode))
                throw new IllegalArgumentException("errorCode is not set");

            if (this.action == null)
                throw new IllegalArgumentException("action is not set: use ErrorsExceptionBuilder#reject or ErrorsExceptionBuilder#rejectValue");

            switch (this.action) {
                case REJECT_DEFAULT -> {

                    if (StringUtils.hasText(defaultMessage)) {

                        if (ArrayUtils.isEmpty(errorArgs))
                            errors.reject(errorCode, defaultMessage);
                        else
                            errors.reject(errorCode, errorArgs, defaultMessage);
                    } else
                        errors.reject(errorCode);
                }
                case REJECT_VALUE -> {

                    if (!StringUtils.hasText(field))
                        throw new IllegalArgumentException("field is not set");

                    if (ArrayUtils.isEmpty(errorArgs))
                        errors.rejectValue(field, errorCode, defaultMessage);
                    else
                        errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
                }
            }

            return new ErrorsException(errors);
        }
    }

    /**
     * Get object name
     *
     * @return object name
     */
    @NonNull
    @Override
    public String getObjectName() {
        return this.delegate.getObjectName();
    }

    /**
     * Reject based on a field
     *
     * @param field the field name (may be {@code null} or empty String)
     * @param errorCode error code, interpretable as a message key
     * @param errorArgs error arguments, for argument binding via MessageFormat
     * (can be {@code null})
     * @param defaultMessage fallback default message
     */
    @Override
    public void rejectValue(String field, @NonNull String errorCode, Object[] errorArgs, String defaultMessage) {
        this.delegate.rejectValue(field, errorCode, errorArgs, defaultMessage);
    }

    /**
     * Reject based on an object
     *
     * @param errorCode error code, interpretable as a message key
     * @param errorArgs error arguments, for argument binding via MessageFormat
     * (can be {@code null})
     * @param defaultMessage fallback default message
     */
    @Override
    public void reject(@NonNull String errorCode, Object[] errorArgs, String defaultMessage) {
        this.delegate.reject(errorCode, errorArgs, defaultMessage);
    }

    /**
     * Get global errors
     *
     * @return global errors
     */
    @NonNull
    @Override
    public List<ObjectError> getGlobalErrors() {
        return this.delegate.getGlobalErrors();
    }

    /**
     * Get field errors
     *
     * @return field errors
     */
    @NonNull
    @Override
    public List<FieldError> getFieldErrors() {
        return this.delegate.getFieldErrors();
    }

    /**
     * Get field value
     *
     * @param field the field name
     * @return field value
     */
    @Override
    public Object getFieldValue(@NonNull String field) {
        return this.delegate.getFieldValue(field);
    }

    /**
     * String object's representation
     *
     * @return the representation
     */
    @NonNull
    @Override
    public String toString() {
        return "ErrorsException{" +
                "delegate=" + delegate +
                '}';
    }
}
