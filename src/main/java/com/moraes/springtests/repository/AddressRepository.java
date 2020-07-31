package com.moraes.springtests.repository;

import com.moraes.springtests.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
