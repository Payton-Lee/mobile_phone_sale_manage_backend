package com.phoneshop.shop.config;

import com.phoneshop.shop.interceptor.TokenInterceptor;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMcvConfig implements WebMvcConfigurer {
    private TokenInterceptor tokenInterceptor;

    @Autowired
    public void setTokenInterceptor(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns("/v1/login", "/v1/register");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        Path path = Paths.get("image");
        String absolutePath = path.toFile().getAbsolutePath();
        registry.addResourceHandler("/image/**").addResourceLocations("file:/" + absolutePath + "/");
    }
}
