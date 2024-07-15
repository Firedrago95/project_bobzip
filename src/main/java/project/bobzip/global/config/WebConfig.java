package project.bobzip.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.bobzip.global.interceptor.ApiLoginCheckInterceptor;
import project.bobzip.global.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/recipe/add", "/members/delete", "/recipe/delete/*", "/recipe/edit/*",
                        "/fridge", "/recipe/myFavorites");

        registry.addInterceptor(new ApiLoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/reply/*", "/fridge/all", "/fridge/add", "/recipe/like/*", "/recipe/cancelLike/*")
                .excludePathPatterns("/reply/all/*"); {

        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:C:/Users/ekore/file/");
    }
}
