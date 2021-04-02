package com.amoralesch.vdp.spec;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.amoralesch.vdp.web.Application;

@Configuration
@ComponentScan
@Import({Application.class})
public class SpecConfig {
  private static final Logger log = getLogger(SpecConfig.class);

  @Bean
  @Description("Mock MVC for REST API testing")
  public MockMvc mockMvc(WebApplicationContext ctx)
  {
    log.trace("entering");

    return webAppContextSetup(ctx).build();
  }
}
