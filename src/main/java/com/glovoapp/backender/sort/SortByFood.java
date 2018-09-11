package com.glovoapp.backender.sort;

import java.util.Comparator;

import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Order;

public class SortByFood implements Sort {
	@Override
	public Comparator<Order> sort(Order order, Courier courier) {
		return Comparator.comparing(Order::getFood);
	}
}
