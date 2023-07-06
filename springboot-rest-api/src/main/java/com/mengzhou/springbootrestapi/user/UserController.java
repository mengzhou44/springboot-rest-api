package com.mengzhou.springbootrestapi.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserController {
    private UserRepository repository; 

    public UserController(UserRepository repository) {
        this.repository = repository; 
    }

    @GetMapping("/users")
    public List<User> getUsers() {
         return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        Optional<User>  optional =  repository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get()); // Return user with 200 OK status
        } else {
            String errorMessage = "User not found with ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> User(@Valid @RequestBody User user) {
        User saved= repository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(saved.getId())
						.toUri();   
		
		return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Integer id) {
		repository.deleteById(id);
	}

}
