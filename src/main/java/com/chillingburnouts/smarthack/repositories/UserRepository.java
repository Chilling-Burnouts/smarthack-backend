package com.chillingburnouts.smarthack.repositories;

import com.chillingburnouts.smarthack.entities.Company;
import com.chillingburnouts.smarthack.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select u FROM User where u.name = :name")
    User findByName(String name);
}
