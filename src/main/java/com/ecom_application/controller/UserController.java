package com.ecom_application.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom_application.dto.ResponseDTO;
import com.ecom_application.dto.UserResponse;
import com.ecom_application.entity.User;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.exception.UserNotFoundException;
import com.ecom_application.service.UserService;

@RestController
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/user")
	public ResponseEntity<List<UserResponse>> getAllUser() {

		List<UserResponse> listUsers = userService.getAllUser();
		return new ResponseEntity<List<UserResponse>>(listUsers, HttpStatus.OK);

	}

	@PostMapping("/createUser")
	public ResponseEntity<ResponseDTO> createUser(@RequestBody User user) {
		userService.createUser(user);

		return new ResponseEntity<ResponseDTO>(new ResponseDTO("User Created Successfully", new Date(), true),
				HttpStatus.CREATED);

	}

	@PostMapping("/createMultipleUsers")
	public ResponseEntity<ResponseDTO> createMultipleUsers(@RequestBody List<User> users) {
		userService.createMultipleUsers(users);

		return new ResponseEntity<ResponseDTO>(new ResponseDTO("Multiple Users Created Successfully", new Date(), true),
				HttpStatus.CREATED);
	}

	@PutMapping("/updateUser/{id}")
	public ResponseEntity<ResponseDTO> updateUser(@PathVariable("id") Integer userId, @RequestBody User user)
			throws UserNotFoundException {
		userService.updateUser(userId, user);

		return new ResponseEntity<>(
				new ResponseDTO("User with userId : " + userId + " updated successfully", new Date(), true),
				HttpStatus.CREATED);

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Integer userId) throws UserNotFoundException {
		UserResponse userById = userService.findUserById(userId);

		return new ResponseEntity<>(userById, HttpStatus.OK);

	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<ResponseDTO> deleteUserById(@PathVariable("id") Integer userId) throws UserNotFoundException {
		userService.deleteByUserId(userId);

		return new ResponseEntity<ResponseDTO>(
				new ResponseDTO("User" + userId + " Deleted successfully", new Date(), true), HttpStatus.OK);
	}

	@GetMapping("/findUser/{userName}")
	public ResponseEntity<List<UserResponse>> findUserByUserName(@PathVariable("userName") String userName)
			throws UserNotFoundException {
		List<UserResponse> listUsers = userService.findByUserName(userName);

		return new ResponseEntity<>(listUsers, HttpStatus.OK);

	}

	@PutMapping("/user/{userId}/address/{addressId}")
	public ResponseEntity<ResponseDTO> assignAddress(@PathVariable Integer userId, @PathVariable Integer addressId)
			throws ResourceNotFoundException {

		userService.assignAddressToUser(userId, addressId);

		return new ResponseEntity<>(new ResponseDTO(
				"User with userId : " + userId + " updated successfully in Address with addressId : " + addressId,
				new Date(), true), HttpStatus.CREATED);
	}

}
