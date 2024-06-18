package com.demo.demo.repository;

import com.demo.demo.dto.ContractStatusCheck;
import com.demo.demo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT count(*) FROM UserEntity")
    Long quantityOfUser();

    @Query("SELECT u FROM UserEntity u " +
            "WHERE (:role IS NULL OR u.role = :role) " +
            "AND (:deleted IS NULL OR u.deleted = :deleted) " +
            "AND (COALESCE(:keyword, '') = '' OR " +
            "LOWER(u.id) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.userCode) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<UserEntity> findAllFiltered(Integer role, Boolean deleted, String keyword, Pageable pageable);



}
