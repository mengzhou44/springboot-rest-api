package com.mengzhou.springbootrestapi.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    
    @GetMapping(path="/v1/person")
    public Person getPerson() {
         return new Person("Meng Zhou");
    }

     @GetMapping(path="/v2/person")
    public PersonV2 getPersonV2() {
         return new PersonV2(new Name("Meng", "Zhou"));
    }
}
