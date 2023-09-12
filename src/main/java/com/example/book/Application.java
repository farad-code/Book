package com.example.book;

import com.example.book.configuration.AppConfig;
import jakarta.ws.rs.ApplicationPath;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@ApplicationPath("/api")
public class Application extends jakarta.ws.rs.core.Application {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);


}