package fun.wilddev.spring.web.pages;

import java.util.List;
import java.util.function.Function;

import org.springframework.lang.NonNull;

import lombok.*;

/**
 * Pagination object
 *
 * @param <T> - content type
 */
@Setter
@Getter
@ToString
public class PageObject<T> {

    /**
     * Content
     */
    private List<T> content;

    /**
     * Calculated pages count
     */
    private long pages;

    /**
     * Private copying constructor
     *
     * @param content - source content
     * @param pages - source pages
     */
    private PageObject(@NonNull List<T> content, long pages) {

        this.content = content;
        this.pages = pages;
    }

    /**
     * Generic constructor
     *
     * @param content - content
     * @param pageSize - the pages size
     */
    public PageObject(@NonNull List<T> content, int pageSize) {

        this.content = content;
        this.pages = (long) Math.ceil((double) content.size() / pageSize);
    }

    /**
     * Remapping copying method
     *
     * @param source - source object
     * @param remapper - remapper function
     * @return the target object
     *
     * @param <T> - source content type
     * @param <Y> - target content type
     */
    public static <T, Y> PageObject<Y> remap(@NonNull PageObject<T> source, @NonNull Function<T, Y> remapper) {
        return new PageObject<>(source.getContent().stream().map(remapper).toList(), source.getPages());
    }
}
