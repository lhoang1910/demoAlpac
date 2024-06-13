package com.demo.demo.repository;

import com.demo.demo.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    @Query(value = "SELECT count(*) FROM CustomerEntity")
    Long quantityOfCustomer();

}
