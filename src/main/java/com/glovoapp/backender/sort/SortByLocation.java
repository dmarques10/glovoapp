package com.glovoapp.backender.sort;

import java.util.Comparator;

import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Order;
import com.glovoapp.backender.utils.DistanceCalculator;

public class SortByLocation implements Sort {

	public Comparator<Order> sort(Order order, Courier courier) {
		return Comparator.comparingDouble((Order o) -> DistanceCalculator.calculateDistance(courier.getLocation(), o.getPickup()));
	}
}
