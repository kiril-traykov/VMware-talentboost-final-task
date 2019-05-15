package com.talentboost.api.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@CrossOrigin
public class Application {

    @Value("${resources.address}")
    String resourceFolderUrl;
    public  static final  String myServerAddress = "http://192.168.1.5:6789";

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Bean
    WebMvcConfigurer weBMvcConfigurer(){
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/images/**")
                        .addResourceLocations(resourceFolderUrl);

            }
        };
    }

    @EnableWebSecurity
    public class SpringWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
        }
    }

}
