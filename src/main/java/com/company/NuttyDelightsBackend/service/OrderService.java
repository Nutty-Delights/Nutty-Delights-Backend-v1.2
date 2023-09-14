package com.company.NuttyDelightsBackend.service;

import com.company.NuttyDelightsBackend.exception.OrderException;
import com.company.NuttyDelightsBackend.model.Address;
import com.company.NuttyDelightsBackend.model.Order;
import com.company.NuttyDelightsBackend.model.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(User user, Address shippingAddress);

    public Order findOrderById(String orderId) throws OrderException;

    Order findOrderByOrderId(String orderId) throws OrderException;

    public List<Order> usersOrderHistory(String userId);

    public Order placedOrder(String orderId) throws OrderException;

    public Order confirmedOrder(String orderId)throws OrderException;

    public Order shippedOrder(String orderId) throws OrderException;

    public Order deliveredOrder(String orderId) throws OrderException;

    public Order cancledOrder(String orderId) throws OrderException;

    public List<Order>getAllOrders();

    public void deleteOrder(String orderId) throws OrderException;

}
