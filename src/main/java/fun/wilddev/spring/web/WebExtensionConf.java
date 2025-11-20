package fun.wilddev.spring.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Auto-configuration class
 */
@ComponentScan("fun.wilddev.spring.web")
@AutoConfiguration
public class WebExtensionConf {

    /**
     * Default constructor
     */
    public WebExtensionConf() {

    }
}
