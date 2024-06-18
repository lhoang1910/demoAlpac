package com.demo.demo.repository;

import com.demo.demo.dto.ContractStatusCheck;
import com.demo.demo.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<ContractEntity, String> {

    @Query(value = "SELECT COUNT(*) FROM ContractEntity WHERE customerCode = :customerCode")
    long countByCustomerCode(@Param("customerCode") String customerCode);

    @Query(value = "SELECT COUNT(*) FROM ContractEntity")
    long quantityOfContract();

    Optional<ContractEntity> findByIdAndIsDelete(String id, boolean b);

    List<ContractEntity> findByIsDeleteFalseAndContractStatusNot(int contractStatus);

    @Query("SELECT c FROM ContractEntity c WHERE c.startTime > CURRENT_TIMESTAMP AND c.contractStatus <> 1")
    List<ContractStatusCheck> findAllContractsCanBeValid();

    @Query("SELECT c FROM ContractEntity c WHERE c.expirationTime < CURRENT_TIMESTAMP AND c.contractStatus <> 3")
    List<ContractStatusCheck> findAllContractsCanBeExpire();

    Optional<Object> findByContractCode(String contractCode);
}
