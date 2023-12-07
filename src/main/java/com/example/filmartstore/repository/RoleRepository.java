package com.example.filmartstore.repository;

import com.example.filmartstore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select * from roles where name= :nameRole", nativeQuery = true)
    Role findRoleByName(@Param("nameRole") String roleName);
}
