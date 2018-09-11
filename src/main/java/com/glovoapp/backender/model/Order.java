package com.glovoapp.backender.model;

import java.util.Objects;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Order {
	private String id;
	private String description;
	private Boolean food;
	private Boolean vip;
	private Location pickup;
	private Location delivery;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Order order = (Order) o;
		return Objects.equals(id, order.id) &&
			Objects.equals(description, order.description) &&
			Objects.equals(food, order.food) &&
			Objects.equals(vip, order.vip) &&
			Objects.equals(pickup, order.pickup) &&
			Objects.equals(delivery, order.delivery);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description, food, vip, pickup, delivery);
	}

	@Override
	public String toString() {
		return "Order{" +
			"id='" + id + '\'' +
			", description='" + description + '\'' +
			", food=" + food +
			", vip=" + vip +
			", pickup=" + pickup +
			", delivery=" + delivery +
			'}';
	}
}
