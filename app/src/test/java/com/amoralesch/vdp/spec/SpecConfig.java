package com.amoralesch.vdp.spec;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.amoralesch.vdp.web.Application;

@Configuration
@ComponentScan
@Import({Application.class})
public class SpecConfig {
  private static final Logger log = getLogger(SpecConfig.class);
}
