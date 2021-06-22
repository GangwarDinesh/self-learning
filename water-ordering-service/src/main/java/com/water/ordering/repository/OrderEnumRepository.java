package com.water.ordering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.water.ordering.entity.StatusEnum;

@Repository
public interface OrderEnumRepository extends JpaRepository<StatusEnum, Integer> {

}
