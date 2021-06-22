package com.water.ordering.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.hibernate.secure.spi.IntegrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.water.ordering.entity.Order;
import com.water.ordering.repository.WaterOrderingRepository;
import com.water.ordering.utils.CommonUtils;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	@Autowired
	private WaterOrderingRepository waterOrderingRepository;
	
	@Autowired
	private CommonUtils commonUtils;
	
	@Scheduled(fixedRate = 5000)
	public void runJob() {
		ExecutorService executors = Executors.newFixedThreadPool(2);
		
		Runnable r1 = ()->startWaterSupply(waterOrderingRepository);
		Runnable r2 = ()->stopWaterSupply(waterOrderingRepository);
		executors.execute(r1);
		executors.execute(r2);
		executors.shutdown();
		try {
			executors.awaitTermination(10000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			throw new IntegrationException("Intrupted");
		}
		log.info("All Threads execution completed.....");
		
	}
	
	public void startWaterSupply(WaterOrderingRepository waterOrderingRepository) {
		String dateStr = null; 
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateStr = df.format(new Date());
			Date currentTimeStamp = df.parse(dateStr);
			List<Order> ordersList = waterOrderingRepository.findByStartDateTime(currentTimeStamp);
			log.info("Orders List : {}",ordersList);
			ordersList.stream().forEach(order->{
				order.setStatus(2);
				waterOrderingRepository.save(order);
				log.info("Water delivery to farm {0} started. {}", order.getFarmId());
			});
		} catch (Exception e) {
			log.error("Exception in startWaterSupply {}", e.getMessage());
		}
	}
	
	public void stopWaterSupply(WaterOrderingRepository waterOrderingRepository) {
		String dateStr = null; 
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateStr = df.format(new Date());
			Date currentTimeStamp = df.parse(dateStr);
			List<Order> activeOrdersList = waterOrderingRepository.findByStatus();
			activeOrdersList = activeOrdersList.stream().filter(order->commonUtils.compareDuration(currentTimeStamp, order)).collect(Collectors.toList());
			activeOrdersList.forEach(ord->{
				ord.setStatus(3);
				waterOrderingRepository.save(ord);
				log.info("Water delivery to farm stopped.{}", ord.getFarmId());
			});
			
			List<Order> ordersList = waterOrderingRepository.findByStartDateTime(currentTimeStamp);
			log.info("Active orders : {}", ordersList);
			ordersList.stream().forEach(order->{
				order.setStatus(2);
				waterOrderingRepository.save(order);
				log.info("Water delivery to farm started. {}", order.getFarmId());
			});
		} catch (Exception e) {
			log.error("Exception in startWaterSupply {}", e.getMessage());
		}
	}
}