package com.sparta.delivery.repository;

import com.sparta.delivery.model.OrderDetail;
import com.sparta.delivery.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findOrderDetailByOrder(Order order);
}
