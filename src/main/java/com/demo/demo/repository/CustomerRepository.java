package com.demo.demo.repository;

import com.demo.demo.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    @Query(value = "SELECT count(*) FROM CustomerEntity")
    Long quantityOfCustomer();

    Optional<CustomerEntity> findByCustomerCode(String customerCode);

    List<CustomerEntity> findAllByIsDelete(boolean isDelete);

    Optional<CustomerEntity> findByIsDeleteAndId(boolean isDelete, String id);
}
