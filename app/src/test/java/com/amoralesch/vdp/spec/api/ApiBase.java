package com.amoralesch.vdp.spec.api;

import static org.concordion.api.MultiValueResult.multiValueResult;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

import java.net.URI;
import java.util.HashMap;
import java.util.function.Function;

import org.concordion.api.AfterExample;
import org.concordion.api.ExampleName;
import org.concordion.api.MultiValueResult;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.amoralesch.vdp.spec.SpecBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

public abstract class ApiBase extends SpecBase {
  private static final Logger log = getLogger(ApiBase.class);

  @Autowired
  protected MockMvc mvc;

  @AfterExample
  public void apiCleanup(@ExampleName String example)
  {
    if (!"[Outer]".equals(example)) {
      log.info("cleanup: {}:{}", specName, example);
    }
  }

  public String encode(Object obj) throws JsonProcessingException
  {
    if (obj == null)
      return null;

    return objectMapper.writerWithDefaultPrettyPrinter()
      .writeValueAsString(obj);
  }

  public <T> T safe(Action<T> s)
  {
    try {
      return s.get();
    } catch (Exception ex) {
      setException(ex);

      return null;
    }
  }

  protected <T> MultiValueResult http(String method, String uri,
    Object in, Function<byte[], T> map) throws Exception
  {
    MockHttpServletRequestBuilder req = request(method, URI.create(uri))
      .accept("application/json");

    if (in != null)
      req.contentType("application/json")
        .characterEncoding("utF-8")
        .content(objectMapper.writeValueAsBytes(in));

    MvcResult result = mvc.perform(req).andReturn();
    MockHttpServletResponse response = result.getResponse();
    int status = response.getStatus();
    byte[] out = response.getContentAsByteArray();
    Object body = null;

    if (out != null && out.length > 0) {
      if (status >= 400)
        body = objectMapper.readValue(out,
          new TypeReference<HashMap<String, Object>>() {});
      else if (map != null)
        body = map.apply(out);
      else
        body = encode(objectMapper.readValue(out, Object.class));
    }

    return multiValueResult()
      .with("status", status)
      .with("contentType", response.getContentType())
      .with("location", response.getHeader("Location"))
      .with("wwwAuthenticate", response.getHeaderValues("WWW-Authenticate"))
      .with("body", body);
  }

  protected interface Action<T> {
    T get() throws Exception;
  }
}
