package com.glovoapp.backender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glovoapp.backender.exception.CourierNotFoundException;
import com.glovoapp.backender.exception.NumberOfSortsException;
import com.glovoapp.backender.model.OrderVM;
import com.glovoapp.backender.repository.CourierRepository;
import com.glovoapp.backender.repository.OrderRepository;
import com.glovoapp.backender.service.CourierService;

@Controller
@ComponentScan("com.glovoapp.backender")
@EnableAutoConfiguration
@Log
class API {
	private final String welcomeMessage;
	private final OrderRepository orderRepository;
	private final CourierRepository courierRepository;
	private final CourierService courierService;
	@Value("#{'${glovo.boxes.order}'.split(',')}")
	private List<String> glovoBoxes;

	@Autowired
	API(@Value("${backender.welcome_message}") String welcomeMessage, OrderRepository orderRepository, CourierRepository courierRepository, CourierService courierService) {
		this.welcomeMessage = welcomeMessage;
		this.orderRepository = orderRepository;
		this.courierRepository = courierRepository;
		this.courierService = courierService;
	}

	@RequestMapping("/")
	@ResponseBody
	public String root() {
		return welcomeMessage;
	}

	@RequestMapping("/orders")
	@ResponseBody
	public List<OrderVM> orders() {
		return orderRepository.findAll()
			.stream()
			.map(order -> new OrderVM(order.getId(), order.getDescription()))
			.collect(Collectors.toList());
	}

	@RequestMapping("/orders/{courierId}")
	@ResponseBody
	public List<OrderVM> orders(@PathVariable("courierId") String courierId) {
		try {
			return courierService.getCourierOrders(courierId)
				.stream()
				.map(order -> new OrderVM(order.getId(), order.getDescription()))
				.collect(Collectors.toList());
		} catch (NumberOfSortsException e) {
			log.severe(e.getMessage());
			return new ArrayList<>();
		} catch (CourierNotFoundException e) {
			log.info(e.getMessage());
			return new ArrayList<>();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(API.class);
	}
}
