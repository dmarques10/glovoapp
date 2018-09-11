package com.glovoapp.backender.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.glovoapp.backender.model.Location;
import com.glovoapp.backender.model.Order;

class OrderRepositoryTest {
    @Test
    void findAll() {
        List<Order> orders = new OrderRepository().findAll();

        assertFalse(orders.isEmpty());

        Order firstOrder = orders.get(0);

        Order expected = Order.builder().id("order-1")
                .description("I want a pizza cut into very small slices")
                .food(true)
                .vip(false)
                .pickup(new Location(41.3965463, 2.1963997))
                .delivery(new Location(41.407834, 2.1675979)).build();

        assertEquals(expected, firstOrder);
    }
}
