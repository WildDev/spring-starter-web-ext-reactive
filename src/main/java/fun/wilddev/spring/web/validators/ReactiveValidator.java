package fun.wilddev.spring.web.validators;

import org.springframework.lang.NonNull;

import reactor.core.publisher.Mono;

/**
 * Reactive clone of {@link org.springframework.validation.Validator}
 */
public interface ReactiveValidator {

    /**
     * Validates the {@code target}
     *
     * @param target - target object
     * @return the reactive descriptor
     */
    Mono<Void> validate(@NonNull Object target);
}
