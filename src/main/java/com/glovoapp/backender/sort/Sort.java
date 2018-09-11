package com.glovoapp.backender.sort;

import java.util.Comparator;

import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Order;

public interface Sort {
	Comparator<Order> sort(Order order, Courier courier);
}
