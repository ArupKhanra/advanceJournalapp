package com.arupkhanra.advanceSpringbootFeaturesAZ.repository;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);
    @Transactional
    void deleteByUserName(String userName);
}
