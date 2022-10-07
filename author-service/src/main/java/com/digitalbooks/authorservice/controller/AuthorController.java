package com.digitalbooks.authorservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.authorservice.model.AuthorDetails;
import com.digitalbooks.authorservice.model.JwtResponse;
import com.digitalbooks.authorservice.service.AuthorService;

@RestController
@RequestMapping("/api/v1/digitalbooks/author")
@CrossOrigin
public class AuthorController {
	

	
	@Autowired
	AuthorService authorService;
	

	@PostMapping("/signup")
	public ResponseEntity<?> saveCredentials(@RequestBody AuthorDetails signUpRequest) {

		AuthorDetails author = new AuthorDetails();
	        author.setUsername(signUpRequest.getUsername());
	        author.setPassword(signUpRequest.getPassword());
	        author.setAuthorEmailId(signUpRequest.getAuthorEmailId());
	        authorService.save(author);
	        return ResponseEntity.ok(new JwtResponse("User registered successfully"));
	}
	
	
	
	@GetMapping()
	public List<AuthorDetails> getAuthorDetails() {
		return authorService.getAuthors();
	}

	@PostMapping("/login")
    public String authorLogin(@RequestBody AuthorDetails author)
    {
        String username=author.getUsername();
        System.out.println("username" +username);
        String password=author.getPassword();
        String s=authorService.validateUser(username,password);
        if(s.equalsIgnoreCase("success"))
        {
            return "Login Successfull";    
        }
        else if(s.equalsIgnoreCase("failure"))
        {
            return "Please enter correct password";
        }
        else
        {
            return "User is not available,Please register";
        }
        
    }
	
	@GetMapping("/getAuthor/{aId}")
	public Optional<AuthorDetails> getAuthorByID(@PathVariable int aId) {
		return authorService.getAuthorById(aId);
	}
	
	@GetMapping("/getAuthorByName/{authorName}")
	public Optional<AuthorDetails> getAuthorByName(@PathVariable String username){
		return authorService.getAuthorByName(username);
	}
	

}
