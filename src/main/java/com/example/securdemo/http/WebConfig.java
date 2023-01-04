package com.example.securdemo.http;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = WebConfig.class)
public class WebConfig implements WebMvcConfigurer {

//    @Bean
//    public ViewResolver getInternalResourceViewResolver() {
//        return new InternalResourceViewResolver();
//    }

}
