package com.example.finaltry.repository;

import com.example.finaltry.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryImpl extends JpaRepository<User, Long> {

    User findByActivationCode(String code);

    User findByEmail(String email);

    User findById(int id);

    List<User> findByUsername(String name);

    List<User> findBySurname(String surname);
}
