package com.ruth.rurucraftsecommerce.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return null;
    }

    @Override
    public List<Order> getAllOrdersByUserId(Integer id) {
        return null;
    }

    @Override
    public Order getOrderById(Integer id) {
        return null;
    }

    @Override
    public OrderDetail getOrderDetailById(Integer id) {
        return null;
    }

    @Override
    public OrderDetail getOrderDetailByIdAndUserId(Integer id, Integer userId) {
        return null;
    }

    @Override
    public Order getOrderByIdAndUserId(Integer id, Integer userId) {
        return null;
    }

    @Override
    public Order updateOrder(Integer id, Order order) {
        return null;
    }

    @Override
    public OrderDetail updateOrderDetail(Integer id, OrderDetail orderDetail) {
        return null;
    }

    @Override
    public boolean deleteOrder(Integer id) {
        return false;
    }

    @Override
    public boolean deleteOrderDetail(Integer id) {
        return false;
    }
}
