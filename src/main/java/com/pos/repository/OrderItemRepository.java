package com.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
