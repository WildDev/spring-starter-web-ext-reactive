package fun.wilddev.spring.web.mappers;

import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.*;

import org.springframework.util.*;

/**
 * Mapper bean for {@link org.springframework.util.MultiValueMap} instantiation
 */
@Component
public class MultiValueMapper {

    /**
     * Default constructor
     */
    public MultiValueMapper() {

    }

    /**
     * Maps {@link java.util.Map} to a single level {@link org.springframework.util.MultiValueMap}
     *
     * @param map - source map
     * @return mapped instance of {@link org.springframework.util.MultiValueMap}
     *
     * @param <K> - key type
     * @param <V> - value type
     */
    public <K, V> MultiValueMap<K, V> map(@NonNull Map<K, V> map) {
        return new LinkedMultiValueMap<>(map.entrySet().stream().collect(Collectors
                .toMap(Map.Entry::getKey, e -> Collections.singletonList(e.getValue()))));
    }
}
