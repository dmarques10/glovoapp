package com.glovoapp.backender.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Order;
import com.glovoapp.backender.model.Vehicle;
import com.glovoapp.backender.repository.CourierRepository;
import com.glovoapp.backender.repository.OrderRepository;
import com.glovoapp.backender.utils.DistanceCalculator;
import com.glovoapp.backender.sort.SortEnum;

@Service
public class CourierService {

	private final CourierRepository courierRepository;
	private final OrderRepository orderRepository;

	@Value("#{'${glovo.boxes.order}'.split(',')}")
	private List<String> glovoBoxes;
	@Value("#{'${glovo.boxes.sort}'.split(',')}")
	private List<String> sorts;

	public CourierService(CourierRepository courierRepository, OrderRepository orderRepository) {
		this.courierRepository = courierRepository;
		this.orderRepository = orderRepository;
	}

	public List<Order> getCourierOrders(String courierId) {
		Courier courier = courierRepository.findById(courierId);
		return orderRepository.findAll()
			.stream()
			.filter(order -> DistanceCalculator.calculateDistance(order.getPickup(), courier.getLocation()) <= 5 ||
				Vehicle.MOTORCYCLE.equals(courier.getVehicle()) || Vehicle.ELECTRIC_SCOOTER.equals(courier.getVehicle()))
			.filter(order -> !glovoBoxes.stream().anyMatch(s -> order.getDescription().toLowerCase().contains(s.toLowerCase())) || courier.getBox())
			.sorted((o1, o2) -> SortEnum.valueOf(sorts.get(0)).getSort().sort(o2, courier)
				.thenComparing(SortEnum.valueOf(sorts.get(1)).getSort().sort(o2, courier))
				.thenComparing(SortEnum.valueOf(sorts.get(2)).getSort().sort(o2, courier)).compare(o1, o2))
			.collect(Collectors.toList());
	}

}
