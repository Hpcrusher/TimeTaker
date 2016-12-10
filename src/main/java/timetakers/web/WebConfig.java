package timetakers.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import timetakers.web.viewresolver.JsonViewResolver;

/**
 * @author David Liebl
 */

@Configuration
public class WebConfig {

    @Bean
    public ViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }

}
