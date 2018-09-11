package com.glovoapp.backender.service;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import com.glovoapp.backender.exception.CourierNotFoundException;
import com.glovoapp.backender.exception.NumberOfSortsException;
import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Location;
import com.glovoapp.backender.model.Order;
import com.glovoapp.backender.model.Vehicle;
import com.glovoapp.backender.repository.CourierRepository;
import com.glovoapp.backender.repository.OrderRepository;

public class CourierServiceTest {
	@Mock
	private CourierRepository courierRepository;

	@Mock
	private OrderRepository orderRepository;

	private CourierService courierService;

	@Value("#{'${glovo.boxes.order}'.split(',')}")
	private List<String> glovoBoxes;

	@Value("#{'${glovo.boxes.sort}'.split(',')}")
	private List<String> sorts;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		courierService = spy(new CourierService(courierRepository, orderRepository));
		glovoBoxes = Arrays.asList("pizza", "cake", "flamingo");
		courierService.setGlovoBoxes(glovoBoxes);
		sorts = Arrays.asList("FOOD", "VIP", "DISTANCE");
		courierService.setSorts(sorts);
	}

	@Test
	public void testCourierOrders() throws CourierNotFoundException, NumberOfSortsException {
		Courier courier = Courier.builder().id("courier-1")
			.box(true)
			.name("Manolo Escobar")
			.vehicle(Vehicle.MOTORCYCLE)
			.location(new Location(41.3965463, 2.1963997)).build();
		Order order = Order.builder().id("order-1")
			.description("I want a pizza cut into very small slices")
			.food(true)
			.vip(false)
			.pickup(new Location(41.3965463, 2.1963997))
			.delivery(new Location(41.407834, 2.1675979)).build();
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		when(courierRepository.findById("courier-1")).thenReturn(courier);
		when(orderRepository.findAll()).thenReturn(orders);
		final List<Order> courierOrders = courierService.getCourierOrders("courier-1");
		Assertions.assertTrue(courierOrders.size() == 1);
	}


	@Test
	public void testCourierOrdersEmptyResponse() throws CourierNotFoundException, NumberOfSortsException {
		Courier courier = Courier.builder().id("courier-1")
			.box(true)
			.name("Manolo Escobar")
			.vehicle(Vehicle.BICYCLE)
			.location(new Location(41.3965463, 2.1963997)).build();
		Order order = Order.builder().id("order-1")
			.description("I want a pizza cut into very small slices")
			.food(true)
			.vip(false)
			.pickup(new Location(41.3965463, 2.2963997))
			.delivery(new Location(41.407834, 2.1675979)).build();
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		when(courierRepository.findById("courier-1")).thenReturn(courier);
		when(orderRepository.findAll()).thenReturn(orders);
		final List<Order> courierOrders = courierService.getCourierOrders("courier-1");
		Assertions.assertTrue(courierOrders.size() == 0);
	}
	@Test
	public void testCourierOrdersEmptyResponseNotBoxEquipe() throws CourierNotFoundException, NumberOfSortsException {
		Courier courier = Courier.builder().id("courier-1")
			.box(false)
			.name("Manolo Escobar")
			.vehicle(Vehicle.MOTORCYCLE)
			.location(new Location(41.3965463, 2.1963997)).build();
		Order order = Order.builder().id("order-1")
			.description("I want a pizza cut into very small slices")
			.food(true)
			.vip(false)
			.pickup(new Location(41.3965463, 2.1963997))
			.delivery(new Location(41.407834, 2.1675979)).build();
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		when(courierRepository.findById("courier-1")).thenReturn(courier);
		when(orderRepository.findAll()).thenReturn(orders);
		final List<Order> courierOrders = courierService.getCourierOrders("courier-1");
		Assertions.assertTrue(courierOrders.size() == 0);
	}

	@Test
	public void testCourierOrdersSortedDistance() throws CourierNotFoundException, NumberOfSortsException {
		Courier courier = Courier.builder().id("courier-1")
			.box(true)
			.name("Manolo Escobar")
			.vehicle(Vehicle.MOTORCYCLE)
			.location(new Location(41.3965463, 2.1963997)).build();
		Order order = Order.builder().id("order-1")
			.description("I want a pizza cut into very small slices")
			.food(true)
			.vip(false)
			.pickup(new Location(41.3965463, 2.2463997))
			.delivery(new Location(41.407834, 2.1675979)).build();

		Order order2 = Order.builder().id("order-1")
			.description("I want a pizza cut into very small slices")
			.food(true)
			.vip(false)
			.pickup(new Location(41.3965463, 2.1963997))
			.delivery(new Location(41.407834, 2.1675979)).build();
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		orders.add(order2);
		when(courierRepository.findById("courier-1")).thenReturn(courier);
		when(orderRepository.findAll()).thenReturn(orders);
		final List<Order> courierOrders = courierService.getCourierOrders("courier-1");
		Assertions.assertTrue(courierOrders.size() == 2);
		Assertions.assertTrue(courierOrders.get(0).equals(order2));
		Assertions.assertTrue(courierOrders.get(1).equals(order));
	}

	@Test
	public void testCourierOrdersSortedFood() throws CourierNotFoundException, NumberOfSortsException {
		Courier courier = Courier.builder().id("courier-1")
			.box(true)
			.name("Manolo Escobar")
			.vehicle(Vehicle.MOTORCYCLE)
			.location(new Location(41.3965463, 2.1963997)).build();
		Order order = Order.builder().id("order-1")
			.description("I want a pizza cut into very small slices")
			.food(false)
			.vip(false)
			.pickup(new Location(41.3965463, 2.2463997))
			.delivery(new Location(41.407834, 2.1675979)).build();

		Order order2 = Order.builder().id("order-1")
			.description("I want a pizza cut into very small slices")
			.food(true)
			.vip(false)
			.pickup(new Location(41.3965463, 2.2463997))
			.delivery(new Location(41.407834, 2.1675979)).build();
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		orders.add(order2);
		when(courierRepository.findById("courier-1")).thenReturn(courier);
		when(orderRepository.findAll()).thenReturn(orders);
		final List<Order> courierOrders = courierService.getCourierOrders("courier-1");
		Assertions.assertTrue(courierOrders.size() == 2);
		Assertions.assertTrue(courierOrders.get(0).equals(order2));
		Assertions.assertTrue(courierOrders.get(1).equals(order));
	}

	@Test
	public void testCourierOrdersSortedVip() throws CourierNotFoundException, NumberOfSortsException {
		Courier courier = Courier.builder().id("courier-1")
			.box(true)
			.name("Manolo Escobar")
			.vehicle(Vehicle.MOTORCYCLE)
			.location(new Location(41.3965463, 2.1963997)).build();
		Order order = Order.builder().id("order-1")
			.description("I want a pizza cut into very small slices")
			.food(true)
			.vip(false)
			.pickup(new Location(41.3965463, 2.2463997))
			.delivery(new Location(41.407834, 2.1675979)).build();

		Order order2 = Order.builder().id("order-1")
			.description("I want a pizza cut into very small slices")
			.food(true)
			.vip(true)
			.pickup(new Location(41.3965463, 2.2463997))
			.delivery(new Location(41.407834, 2.1675979)).build();
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		orders.add(order2);
		when(courierRepository.findById("courier-1")).thenReturn(courier);
		when(orderRepository.findAll()).thenReturn(orders);
		final List<Order> courierOrders = courierService.getCourierOrders("courier-1");
		Assertions.assertTrue(courierOrders.size() == 2);
		Assertions.assertTrue(courierOrders.get(0).equals(order2));
		Assertions.assertTrue(courierOrders.get(1).equals(order));
	}

}
