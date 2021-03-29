package com.amoralesch.vdp.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @RequestMapping("/api/hello")
  public String index()
  {
    return "Greetings from Spring Boot!";
  }
}
