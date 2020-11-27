package com.jeanmamelo.repository;

import com.jeanmamelo.model.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    List<UserEntity> findAll(Pageable pageable);
}
