### Description

Web extension starter for reactive [Spring](https://spring.io) apps.

[![Java CI with Maven](https://github.com/WildDev/spring-starter-web-ext-reactive/actions/workflows/maven.yml/badge.svg)](https://github.com/WildDev/spring-starter-web-ext-reactive/actions/workflows/maven.yml)

### What it carries

* `fun.wilddev.spring.web.controllers.AbstractReactiveController` - an abstraction to deal with the server responses and errors assembling more easily.
* `fun.wilddev.spring.web.validators.ReactiveValidator` - reactive clone of `org.springframework.validation.Validator`
* `fun.wilddev.spring.web.validators.AbstractReactiveValidator` - an abstraction to implement business validation logic
* `fun.wilddev.spring.web.mappers.MultiValueMapper` - a mapping bean to instantiate Spring's `MultiValueMap`. It may be used to assemble http headers.
* `fun.wilddev.spring.web.controllers.responses.errors.ErrorResponse` - a basic error schema which is well-suited for most of the apps.

### Validation API

Here's an example, how the `AbstractReactiveValidator` could be used in a real application:

```java

// The validator itself:
@AllArgsConstructor
@Component
public class ExampleValidator extends AbstractReactiveValidator {
    
    private final StoryService storyService;
    
    private final ImageService imageService;

    @Transactional
    @Override
    public Mono<Void> validate(@NonNull Object target) {
        return validate(target, SetImageRequest.class, 
                request -> storyService.findById(request.getStoryId()).switchIfEmpty(
                        ReportError.of(request).rejectField("storyId", "story.not.found", "Story not found")),
                request -> imageService.findById(request.getImageId()).switchIfEmpty(
                        ReportError.of(request).rejectField("imageId", "image.not.found", "Image not found")));
    }
}

// Might be handled next in a pipeline:
request.flatMap(req -> exampleValidator.validate(req).thenReturn(req)).onErrorResume(ErrorsException.class, ex -> ... )

// Or globally like this:
@ExceptionHandler({
        WebExchangeBindException.class,
        ErrorsException.class
})
public Mono<ResponseEntity<Object>> onBadRequest(Errors errors) {
    return badRequest(errors);
}

```

### Get started

Build requirements:
* latest JDK and Maven

Also available in Maven central:

```xml
<dependency>
    <groupId>fun.wilddev.lib</groupId>
    <artifactId>spring-starter-web-ext-reactive</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
