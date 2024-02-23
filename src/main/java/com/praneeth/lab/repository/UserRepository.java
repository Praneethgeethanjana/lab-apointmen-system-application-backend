package com.praneeth.lab.repository;

import com.praneeth.lab.entity.User;
import com.praneeth.lab.enums.common.ActiveStatus;
import com.praneeth.lab.enums.common.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT au FROM User au WHERE au.userName=?1 AND au.userRole='USER'")
    Optional<User> findActiveUserByUserNameOrEmail(String userName);

    @Query(value = "SELECT au FROM User au WHERE au.userName=?1 AND au.userRole=?2")
    Optional<User> findActiveAdminUserByUserNameOrEmail(String userName, UserRole userRole);

    @Query(value = "select user_unique_id from user order by created asc limit 1", nativeQuery = true)
    String findLatestUserUniqueId();

    Optional<User> findFirstByUserName(String email);


    Optional<User> findUserByIdAndUserName(Long id, String email);

    @Query(value = "SELECT u FROM User u WHERE u.userRole='USER' AND u.status = ?1")
    Page<User> getAllUserByStatus(ActiveStatus status, Pageable pageable);

    @Query(value = "SELECT u FROM User u WHERE u.status = ?1 AND u.userName LIKE %?2% OR u.userName LIKE %?2%")
    Page<User> getAllUserByStatusAndEmail(ActiveStatus status, String keyword, Pageable pageable);

    @Query(value = "SELECT au FROM User au WHERE au.userName=?1 AND au.id<>?2 ")
    Optional<User> getUserByUserNameNotInID(String userName, Long userId);
}
