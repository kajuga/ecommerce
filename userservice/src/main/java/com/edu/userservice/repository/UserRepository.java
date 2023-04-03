package com.edu.userservice.repository;

import com.edu.userservice.model.User;
import com.edu.userservice.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user u " +
            "ORDER BY ISNULL(last_name), last_name, ISNULL(first_name), first_name " , nativeQuery = true)
    List<User> findAllByOrder();

    Long countByEmail(String email);

    Optional<List<User>> findByEmail(String email);

    List<User> findByIdIn(List<Long> usersIds);

    List<User> findAllByUserRole(UserRole userRole);

    @Query(value = "SELECT * FROM user where role_id in (SELECT id from user_role where role_name like 'EXTERNAL_%')", nativeQuery = true)
    List<User> findAllExternalUsers();

}