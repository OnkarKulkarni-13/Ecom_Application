package com.ecom_application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecom_application.dto.UserResponse;
import com.ecom_application.entity.User;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.exception.UserNotFoundException;

@Service
public interface UserService {
	public List<UserResponse> getAllUser();

	public void createUser(User user);

	public UserResponse findUserById(Integer userId) throws UserNotFoundException;

	public void deleteByUserId(Integer userId) throws UserNotFoundException;

	public void createMultipleUsers(List<User> users);

	public void updateUser(Integer userId, User user) throws UserNotFoundException;

	public List<UserResponse> findByUserName(String userName) throws UserNotFoundException;

	public void assignAddressToUser(Integer userId, Integer addressId) throws ResourceNotFoundException;
	

}
