package com.company.NuttyDelightsBackend.serviceimpl;

import com.company.NuttyDelightsBackend.model.OrderItem;
import com.company.NuttyDelightsBackend.repository.OrderItemRepository;
import com.company.NuttyDelightsBackend.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		
		return orderItemRepository.save(orderItem);
	}


}
