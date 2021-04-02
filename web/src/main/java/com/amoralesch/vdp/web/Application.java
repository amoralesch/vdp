package com.amoralesch.vdp.web;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@SpringBootApplication
public class Application {
  private static final String STATIC = "/META-INF/resources/";

  private static final String INDEX = STATIC + "index.html";

  public static void main(String[] args)
  {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public WebMvcConfigurer webMvcConfigurer()
  {
    return new WebMvcConfigurer() {
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry)
      {
        registry.addResourceHandler("/**")
          .addResourceLocations("classpath:" + STATIC)
          .resourceChain(true)
          .addResolver(new PathResourceResolver() {
            @Override
            protected Resource getResource(String path,
                Resource location) throws IOException
            {
              Resource r = location.createRelative(path);

              return r.isReadable() ? r : new ClassPathResource(INDEX);
            }
          });
      }
    };
  }
}
