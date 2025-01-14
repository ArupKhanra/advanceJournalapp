package com.arupkhanra.advanceSpringbootFeaturesAZ.repository;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);

    void deleteByUserName(String userName);

    // JPQL query to fetch Users with non-null email and TRUE sentiment analysis
    @Query("SELECT u FROM User u WHERE u.email IS NOT NULL AND TRIM(u.email) != '' AND u.sentimentAnalisis = 'TRUE'")
    List<User> getUserForSA();

}
