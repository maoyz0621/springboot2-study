package com.myz.repository;

import com.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 集成jpa
 *
 * @author maoyz on 18-1-4.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Serializable> {

    List<UserEntity> findByAge(Integer age);
}
