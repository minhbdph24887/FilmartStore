package com.example.filmartstore.repository;

import com.example.filmartstore.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "select * from accounts where email= :email", nativeQuery = true)
    Optional<Account> findAccountByEmail(@Param("email") String email);

    @Query(value = "select case when EXISTS ( select * from accounts where email = :email) " +
            "then cast(1 as bit) else cast(0 as bit) end", nativeQuery = true)
    boolean existsByEmail(@Param("email") String email);

    @Query(value = "select * from accounts where email= :email", nativeQuery = true)
    Account getUserByEmail(@Param("email") String email);
}
