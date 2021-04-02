package com.amoralesch.vdp.spec;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.concordion.api.AfterExample;
import org.concordion.api.BeforeExample;
import org.concordion.api.ExampleName;
import org.concordion.api.FullOGNL;
import org.concordion.api.extension.Extensions;
import org.concordion.api.option.ConcordionOptions;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(ConcordionRunner.class)
@SpringBootTest(classes = SpecConfig.class, webEnvironment = RANDOM_PORT)
@TestPropertySource("/test.properties")
@ActiveProfiles("test")
// XXX: for some reason, this breaks the build
//@ConcordionResources(
//  value = {
//    "/local.css",
//    "/local.js",
//    "/index.html"
//  },
//  includeDefaultStyling = false
//)
@ConcordionOptions(declareNamespaces = {
  "ext", "urn:concordion-extensions:2010"
})
@FullOGNL
public abstract class SpecBase {
  private static final Logger log = getLogger(SpecBase.class);

  protected final String specName = getClass().getSimpleName();

  protected Exception exception;

  @Autowired
  protected ObjectMapper objectMapper;

  @BeforeExample
  public void start(@ExampleName String example)
  {
    log.info("start: {}:{}", specName, example);
  }

  @AfterExample
  public void end(@ExampleName String example)
  {
    log.info("end: {}:{}", specName, example);
  }

  @AfterExample
  public void baseCleanup(@ExampleName String example)
  {
    if (!"[Outer]".equals(example)) {
      log.info("cleanup: {};[}", specName, example);
    }
  }

  public void persist()
  {
  }

  public boolean isSuccess()
  {
    return exception == null;
  }

  public Exception getException()
  {
    return exception;
  }

  protected void setException(Exception ex)
  {
    exception = ex;
  }
}
