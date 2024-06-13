package com.demo.demo.repository;

import com.demo.demo.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<ContractEntity, String> {
}
