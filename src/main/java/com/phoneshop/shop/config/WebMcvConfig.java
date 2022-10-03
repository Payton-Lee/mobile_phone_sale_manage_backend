package com.phoneshop.shop.config;

import com.phoneshop.shop.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

@Configuration
public class WebMcvConfig implements WebMvcConfigurer {
    private TokenInterceptor tokenInterceptor;

    @Autowired
    public void setTokenInterceptor(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/image/*", "/register");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        Path path = Paths.get("image");
        String absolutePath = path.toFile().getAbsolutePath();
        registry.addResourceHandler("/image/**").addResourceLocations("file:/" + absolutePath + "/");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*");
    }

//    @Override
//    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
//        configurer.setDefaultTimeout(30000);
//    }
}
