package com.mengzhou.springbootrestapi.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HelloWorldController {
     
    @GetMapping(path="/hello")
     public String getHelloString(HttpServletRequest request) {
          String name = request.getParameter("name");
          if (name == null) {
             return "Hello World 123!";
          }
          return "Hello "+ name; 
         
     }

     @GetMapping(path="/hello-bean")
     public HelloWorldBean getHelloaBean() {
          return new HelloWorldBean("Hello World!");
     }

     @GetMapping(path="/hello/{name}")
     public String sayHello(@PathVariable String name) {
          return "Hello "+name;
     }

     
}
