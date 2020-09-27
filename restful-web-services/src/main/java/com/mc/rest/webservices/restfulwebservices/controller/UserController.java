package com.mc.rest.webservices.restfulwebservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mc.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.mc.rest.webservices.restfulwebservices.model.Account;
import com.mc.rest.webservices.restfulwebservices.model.Loan;
import com.mc.rest.webservices.restfulwebservices.model.User;
import com.mc.rest.webservices.restfulwebservices.service.AccountRepository;
import com.mc.rest.webservices.restfulwebservices.service.UserRepository;
import com.mc.rest.webservices.restfulwebservices.util.LoanServiceProxy;


@RefreshScope
@RestController
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private LoanServiceProxy loanServiceProxy;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	@PostMapping("/users/create")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUserById(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			throw new UserNotFoundException("user with "+id+" is not found");
		EntityModel<User> resource = EntityModel.of(user.get());
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		logger.info("User has been saved sucessfully");
		return resource.add(linkTo.withRel("all-users"));
	}	
	
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable int id){
		userRepository.deleteById(id);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUserById(@PathVariable int id, @RequestBody User user){
		User user1 = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user with "+id+" is not found"));
		user1.setDob(user.getDob());
		user1.setAccounts(user.getAccounts());
		user1.setAccountType(user.getAccountType());
		user1.setEmail(user.getEmail());
		user1.setName(user.getName());
		user1.setPan(user.getPan());
		user1.setPassword(user.getPassword());
		user1.setUserName(user.getUserName());
		final User updatedUser = userRepository.save(user1);
		return ResponseEntity.ok(updatedUser);
	}
	
	@GetMapping("/users/{id}/account")
	public List<Account> getAccount(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			throw new UserNotFoundException("user with "+id+" is not found");
		
		return user.get().getAccounts();
	}
	
	@PostMapping("/users/{id}/account")
	public ResponseEntity<User> createUser(@PathVariable int id, @RequestBody Account account) {
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent())
			throw new UserNotFoundException("user with "+id+" is not found");
		User user = userOptional.get();
		account.setUser(user);
		accountRepository.save(account);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/users/{id}/loans")
	public void getLoans(@PathVariable int id, @RequestBody Loan loan){
		Map<String, Integer> uriVariables = new HashMap<>();
		uriVariables.put("id", id);
		ResponseEntity<Loan> responseEntity = new RestTemplate().postForEntity("http://localhost:9091/loans/create/{id}", loan, Loan.class, uriVariables);
		HttpHeaders headers = responseEntity.getHeaders();
	}
	
	@GetMapping("/users/{id}/loan")
	public List<Loan> getLoans(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			throw new UserNotFoundException("user with "+id+" is not found");
		
		Map<String, Integer> uriVariables = new HashMap<>();
		uriVariables.put("id", id);
		ResponseEntity<Loan[]> loans = new RestTemplate().getForEntity("http://localhost:9091/loans/{id}", Loan[].class,  uriVariables);
		logger.info("{}", Arrays.asList(loans.getBody()));
		return Arrays.asList(loans.getBody());
	}
	
	@GetMapping("/users-feign/{id}/loan")
	public List<Loan> getLoansWithFeign(@PathVariable int id){
		logger.info("{}", loanServiceProxy.retrieveUserById(id));
		return loanServiceProxy.retrieveUserById(id);
	}
}
