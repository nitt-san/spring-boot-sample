package com.example.hrkm.springbootsample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class SampleController {
  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  public String hello() {
    return "hello.";
  }
}
