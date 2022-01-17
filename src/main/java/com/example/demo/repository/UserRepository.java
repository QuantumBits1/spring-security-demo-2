package com.example.demo.repository;

import com.example.demo.core.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByIdNum(String idNum);
    Collection<User> findAll();
    Collection<User> findAllByIdNum(String auth);
    Boolean existsById(String id);
}
