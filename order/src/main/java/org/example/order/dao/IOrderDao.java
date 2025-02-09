package org.example.order.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.order.entity.OrderEntity;
import org.example.order.entity.OrderItemEntity;
import org.example.order.proto.OrderItem;

import java.util.List;

@Mapper
public interface IOrderDao {
    int placeOrder(OrderEntity order);

    void insertOrderItem(@Param("orderId") int orderId, @Param("orderItem") OrderItem orderItem);

    List<OrderEntity> listOrder(@Param("userId") int userId);

    List<OrderItemEntity> listOrderItem(@Param("orderId") int orderId);

    Integer checkOrderPaid(@Param("userId") int userId, @Param("orderId") int orderId);

    void markOrderPaid(@Param("userId") int userId, @Param("orderId") int orderId);
}
