package com.ecom_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom_application.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
