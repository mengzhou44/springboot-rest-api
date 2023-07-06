package com.mengzhou.springbootrestapi.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
 

@RestController
public class PostController {
   private UserRepository userRepository;
	
	private PostRepository postRepository;

	public PostController(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

    @GetMapping("/users/{id}/posts")
    public ResponseEntity<?> getPosts(@PathVariable Integer id) {
        Optional<User> optional = userRepository.findById(id);
		
		if(optional.isEmpty()) {
              String errorMessage = "User not found with ID: " + id;
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
			 
	    return ResponseEntity.ok(optional.get().getPosts());  

    }

    @PostMapping("/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
               String errorMessage = "User not found with ID: " + id;
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
			 
		
		post.setUser(user.get());
		
		postRepository.save(post);
		
		return ResponseEntity.created(null).build();

	}

   
}
