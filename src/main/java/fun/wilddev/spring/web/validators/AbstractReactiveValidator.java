package fun.wilddev.spring.web.validators;

import java.util.function.Function;

import org.springframework.lang.NonNull;
import reactor.util.function.Tuple2;

import reactor.core.publisher.*;

/**
 * An abstraction for reactive validator implementations
 */
public abstract class AbstractReactiveValidator implements ReactiveValidator {

    /**
     * Applies the validation rules
     *
     * @param tuple - cursor tuple
     * @return context pipeline's descriptor
     *
     * @param <T> - the type of {@code target}
     */
    protected final <T, R> Mono<R> applyStages(Tuple2<T, Function<T, ? extends Mono<R>>> tuple) {
        return tuple.getT2().apply(tuple.getT1());
    }

    /**
     * Validates the {@code target}
     *
     * @param target      - target object
     * @param targetClass - asserted class of the target
     * @param stages      - validation stage rules
     * @return the reactive descriptor
     *
     * @param <T> - the type of {@code target}
     */
    @SafeVarargs
    protected final <T, R> Mono<Void> validate(@NonNull Object target, @NonNull Class<T> targetClass,
                                               @NonNull Function<T, ? extends Mono<R>> ...stages) {

        return Flux.zip(Mono.just(target).map(targetClass::cast), Flux.fromArray(stages))
                .flatMapSequential(this::applyStages).then();
    }
}
