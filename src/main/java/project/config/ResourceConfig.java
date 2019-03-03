//package project.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * ResourceConfig
// * anhrajesht e thymeleaf i hamar, vor chanachi upload exac image-neri tex@ FileSystemum
// * <img th:src="@{'/uploads/' + ${imgname}}"/>
// */
//@Configuration
//@EnableWebMvc
//public class ResourceConfig implements WebMvcConfigurer {
//    @Override
//    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/itemImages/**").addResourceLocations("file:itemImages/");
//    }
//}
