package com.base.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.base.app.entity.GenericEntity;

@Repository
public interface GenericEntityRepository extends JpaRepository<GenericEntity, Long> {

}
