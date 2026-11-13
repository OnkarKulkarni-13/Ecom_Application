package com.ecom_application.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecom_application.config.UserRole;
import com.ecom_application.dto.UserResponse;
import com.ecom_application.entity.Address;
import com.ecom_application.entity.User;
import com.ecom_application.exception.ResourceNotFoundException;
import com.ecom_application.exception.UserNotFoundException;
import com.ecom_application.repository.AddressRepository;
import com.ecom_application.repository.UserRepository;
import com.ecom_application.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepo;

	AddressRepository addressRepo;

	AddressServiceImpl addressServiceImpl;

	public UserServiceImpl(UserRepository userRepo, AddressRepository addressRepo,
			AddressServiceImpl addressServiceImpl) {
		super();
		this.userRepo = userRepo;
		this.addressRepo = addressRepo;
		this.addressServiceImpl = addressServiceImpl;
	}

	@Override
	public List<UserResponse> getAllUser() {
		List<User> listUsers = userRepo.findAll();

		List<UserResponse> userResponseList = new ArrayList<>();
		for (User user : listUsers) {
			userResponseList.add(mapUserToUserResponse(user));
		}

		return userResponseList;

	}

	@Override
	public void createUser(User user) {
		if (user.getRole() == null) {
			user.setRole(UserRole.CUSTOMER);
		}

		if (user.getAddress() != null) {

			user.getAddress().setUser(user);

		}
		userRepo.save(user);

		return;

	}

	@Override
	public UserResponse findUserById(Integer userId) throws UserNotFoundException {
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User", "UserId", userId));

		return mapUserToUserResponse(user);

	}

	@Override
	public void deleteByUserId(Integer userId) throws UserNotFoundException {
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User", "UserId", userId));

		userRepo.deleteById(userId);

		return;

	}

	@Override
	public void createMultipleUsers(List<User> users) {

		userRepo.saveAll(users);

		return;

	}

	@Override
	public void updateUser(Integer userId, User user) throws UserNotFoundException {
		User dbUser = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User", "UserId", userId));
		dbUser.setFirstName(user.getFirstName());
		dbUser.setLastName(user.getLastName());

		userRepo.save(dbUser);

		return;

	}

	@Override
	public List<UserResponse> findByUserName(String userName) throws UserNotFoundException {
		List<User> byUserNameContaining = userRepo.findByFirstNameContaining(userName);

		if (byUserNameContaining.isEmpty()) {
			throw new UserNotFoundException("User", "UserName", userName);

		}

		List<UserResponse> userResponseList = new ArrayList<>();

		for (User user : byUserNameContaining) {
			userResponseList.add(mapUserToUserResponse(user));

		}
		return userResponseList;

	}

	public void assignAddressToUser(Integer userId, Integer addressId) throws ResourceNotFoundException {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));

		Address address = addressRepo.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "AddressId", addressId));

		address.setUser(user);

		addressRepo.save(address);
	}

	private UserResponse mapUserToUserResponse(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setCreatedAt(user.getCreatedAt());
		userResponse.setEmail(user.getEmail());
		userResponse.setFirstName(user.getFirstName());
		userResponse.setLastName(user.getLastName());
		userResponse.setPhone(user.getPhone());
		userResponse.setRole(user.getRole());
		userResponse.setUpdatedAt(user.getUpdatedAt());
		userResponse.setId(user.getId());
		if (user.getAddress() != null) {
			userResponse.setAddress(addressServiceImpl.mapAddressToAddressResponse(user.getAddress()));
		}

		return userResponse;

	}

}
