package com.ruth.rurucraftsecommerce.order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);
    OrderDetail createOrderDetail(OrderDetail orderDetail);
    List<Order> getAllOrders();
    List<OrderDetail> getAllOrderDetails();

    List<Order> getAllOrdersByUserId(Integer id);

    Order getOrderById(Integer id);
    OrderDetail getOrderDetailById(Integer id);
    OrderDetail getOrderDetailByIdAndUserId(Integer id,Integer userId);
    Order getOrderByIdAndUserId(Integer id,Integer userId);

    Order updateOrder(Integer id,Order order);
    OrderDetail updateOrderDetail(Integer id,OrderDetail orderDetail);

    boolean deleteOrder(Integer id);
    boolean deleteOrderDetail(Integer id);
}
