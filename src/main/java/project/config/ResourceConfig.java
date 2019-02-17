package project.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ResourceConfig
 * anhrajesht e thymeleaf i hamar, vor chanachi upload exac image-neri tex@ FileSystemum
 * <img th:src="@{'/uploads/' + ${imgname}}"/>
 */
public class ResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/itemImages/**").addResourceLocations("file:itemImages/");
    }
}
