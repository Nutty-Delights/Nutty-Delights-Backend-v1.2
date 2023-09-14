package com.company.NuttyDelightsBackend.controller.Orders;

import com.company.NuttyDelightsBackend.exception.UserException;
import com.company.NuttyDelightsBackend.model.Address;
import com.company.NuttyDelightsBackend.exception.OrderException;
import com.company.NuttyDelightsBackend.model.Order;
import com.company.NuttyDelightsBackend.model.User;
import com.company.NuttyDelightsBackend.service.OrderService;
import com.company.NuttyDelightsBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;



    @PostMapping("/")
    public ResponseEntity<Order> createOrderHandler(@RequestBody Address shippingAddress,
                                                    @RequestHeader("Authorization")String jwt) throws UserException {

        User user=userService.findUserProfileByJwtToken(jwt);
        Order order =orderService.createOrder(user, shippingAddress);

        return new ResponseEntity<Order>(order, HttpStatus.OK);

    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistoryHandler(@RequestHeader("Authorization")
                                                                String jwt) throws OrderException, UserException{

        User user=userService.findUserProfileByJwtToken(jwt);
        List<Order> orders=orderService.usersOrderHistory(user.getUserId());
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity< Order> findOrderHandler(@PathVariable String orderId, @RequestHeader("Authorization")
    String jwt) throws OrderException, UserException{

        User user=userService.findUserProfileByJwtToken(jwt);
        Order orders=orderService.findOrderById(orderId);
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }
    @GetMapping("/user/{orderId}")
    public ResponseEntity< Order> findUserOrderHandler(@PathVariable String orderId, @RequestHeader("Authorization")
    String jwt) throws OrderException, UserException{

        User user=userService.findUserProfileByJwtToken(jwt);
        Order orders=orderService.findOrderByOrderId(orderId);
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

}