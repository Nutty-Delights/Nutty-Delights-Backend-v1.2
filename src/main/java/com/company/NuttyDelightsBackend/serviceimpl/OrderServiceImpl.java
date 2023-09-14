package com.company.NuttyDelightsBackend.serviceimpl;


import com.company.NuttyDelightsBackend.domain.OrderStatus;
import com.company.NuttyDelightsBackend.domain.PaymentStatus;
import com.company.NuttyDelightsBackend.exception.OrderException;
import com.company.NuttyDelightsBackend.model.*;
import com.company.NuttyDelightsBackend.repository.AddressRepository;
import com.company.NuttyDelightsBackend.repository.OrderItemRepository;
import com.company.NuttyDelightsBackend.repository.OrderRepository;
import com.company.NuttyDelightsBackend.repository.UserRepository;
import com.company.NuttyDelightsBackend.service.CartService;
import com.company.NuttyDelightsBackend.service.OrderItemService;
import com.company.NuttyDelightsBackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CartService cartService;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public Order createOrder(User user, Address shippAddress) {
		
		shippAddress.setUserId(user.getUserId());
		Address address= addressRepository.save(shippAddress);
		user.getAddress().add(address);
		userRepository.save(user);
		
		Cart cart=cartService.findUserCart(user.getUserId());
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem item: cart.getCartItems()) {
			OrderItem orderItem=new OrderItem();
			
			orderItem.setPrice(item.getCartItemPrice());
			orderItem.setProduct(item.getCartItemProduct());
			orderItem.setQuantity(item.getCartItemQuantity());
			orderItem.setVariant(item.getCartItemVariant());
			orderItem.setUserId(item.getCartItemUserId());
			OrderItem createdOrderItem=orderItemRepository.save(orderItem);
			
			orderItems.add(createdOrderItem);
		}
		
		
		Order createdOrder=new Order();
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getCartTotalPrice());
		createdOrder.setTotalItems(cart.getCartTotalItems());
		
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now().plusHours(5).plusMinutes(30));
		createdOrder.setOrderStatus(OrderStatus.PENDING);
		createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
		createdOrder.setCreatedAt(LocalDateTime.now().plusHours(5).plusMinutes(30));
		
		Order savedOrder=orderRepository.save(createdOrder);
		
		for(OrderItem item:orderItems) {
			item.setOrder(savedOrder.getId());
			orderItemRepository.save(item);
		}
		
		return savedOrder;
		
	}

	@Override
	public Order placedOrder(String orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.PLACED);
		order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
		return order;
	}

	@Override
	public Order confirmedOrder(String orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		
		
		return orderRepository.save(order);
	}

	@Override
	public Order shippedOrder(String orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.SHIPPED);
		return orderRepository.save(order);
	}

	@Override
	public Order deliveredOrder(String orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.DELIVERED);
		order.setDeliveryDate(LocalDateTime.now().plusHours(5).plusMinutes(30));
		return orderRepository.save(order);
	}

	@Override
	public Order cancledOrder(String orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus(OrderStatus.CANCELLED);
		return orderRepository.save(order);
	}

	@Override
	public Order findOrderById(String orderId) throws OrderException {
		Optional<Order> opt=orderRepository.findById(orderId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("order not exist with id "+orderId);
	}
	@Override
	public Order findOrderByOrderId(String orderId) throws OrderException {
		Optional<Order> opt=orderRepository.findByOrderId(orderId);

		if(opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("order not exist with id "+orderId);
	}

	@Override
	public List<Order> usersOrderHistory(String userId) {
//		List<Order> orders=orderRepository.getUsersOrders(userId);
		List<Order> orders = orderRepository.findAll();
		List<Order> userOrders = new ArrayList<>();

		for(int i = orders.size()-1 ; i >= 0 ; i--){
			if(orders.get(i).getUser().getUserId().equals(userId) &&  !orders.get(i).getOrderStatus().equals(OrderStatus.PENDING))
			{
				userOrders.add(orders.get(i));
			}
		}

//		Collections.reverse(Arrays.asList(userOrders));
		return userOrders;
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		Collections.reverse(Arrays.asList(orders));

		return orders;
	}

	@Override
	public void deleteOrder(String orderId) throws OrderException {
		Order order =findOrderById(orderId);
		
		orderRepository.deleteById(orderId);
		
	}

}
