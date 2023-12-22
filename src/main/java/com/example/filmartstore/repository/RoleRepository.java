package com.example.filmartstore.repository;

import com.example.filmartstore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select * from roles where name= :nameRole", nativeQuery = true)
    Role findRoleByName(@Param("nameRole") String roleName);

    @Query(value = "select r.* from accounts a join authentication b on a.id = b.id_account join roles r on b.id_role = r.id where a.email= :email", nativeQuery = true)
    List<Role> getAllRoleByEmail(@Param("email") String email);

    @Query(value = "select * from roles where name= 'ROLE_USER'", nativeQuery = true)
    Role getAllRoleByUser();
}
