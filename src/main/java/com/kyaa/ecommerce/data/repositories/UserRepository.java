package com.kyaa.ecommerce.data.repositories;
import com.kyaa.ecommerce.data.models.User;
import com.kyaa.ecommerce.enums.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    @Transactional
    @Modifying
    @Query("update User user set user.role = ?2 where user.username = ?1")
    void updateUserRole(String username, Role role);
}
