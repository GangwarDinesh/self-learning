package com.water.ordering.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.water.ordering.entity.Order;

@Repository
public interface WaterOrderingRepository extends JpaRepository<Order, Integer> {

	@Query("select ord from Order ord where ord.farmId=:farmId")
	public List<Order> findByFarmId(@Param("farmId") String farmId);
	
	@Query("select ord from Order ord where ord.orderId=:orderId and ord.status in (1,2)")
	public Order findByOrderIdAndStatus(@Param("orderId") String orderId);
	
	@Query("select ord from Order ord where ord.startDateTime<=:currentDateTime and ord.status=1")
	public List<Order> findByStartDateTime(@Param("currentDateTime") Date currentDateTime);
	
	@Query("select ord from Order ord where ord.status=2")
	public List<Order> findByStatus();
	
}
