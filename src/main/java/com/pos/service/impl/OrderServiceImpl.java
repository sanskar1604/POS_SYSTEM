package com.pos.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pos.domain.OrderStatus;
import com.pos.domain.PaymentType;
import com.pos.mapper.OrderMapper;
import com.pos.model.Branch;
import com.pos.model.Order;
import com.pos.model.OrderItem;
import com.pos.model.Product;
import com.pos.model.User;
import com.pos.payload.dto.OrderDTO;
import com.pos.repository.OrderItemRepository;
import com.pos.repository.OrderRepository;
import com.pos.repository.ProductRepository;
import com.pos.service.OrderService;
import com.pos.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;
	private final UserService userService;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;

	@Override
	public OrderDTO createOrder(OrderDTO orderDto) throws Exception {
		User cashier = userService.getCurrentUser();
		
		Branch branch = cashier.getBranch();
		if(branch == null) {
			throw new Exception("Cashier's branch not found...");
		}
		
		Order order = new Order();
		order.setBranch(branch);
		order.setCashier(cashier);
		order.setCustomer(orderDto.getCustomer());
		order.setPaymentType(orderDto.getPaymentType());
		
		List<OrderItem> orderItems = orderDto.getItems().stream().map(
				itemDto -> {
					Product product = productRepository.findById(itemDto.getProductId())
							.orElseThrow(() -> new EntityNotFoundException("Product not found"));
					
					OrderItem orderItem = new OrderItem();
					orderItem.setOrder(order);
					orderItem.setProduct(product);
					orderItem.setQuantity(itemDto.getQuantity());
					orderItem.setPrice(product.getSellingPrice()*itemDto.getQuantity());
					
//					return orderItemRepository.save(orderItem);
					return orderItem;
				}
				).collect(Collectors.toList());
		
		double total = orderItems.stream().mapToDouble(OrderItem::getPrice).sum();
		order.setTotalAmount(total);
		order.setItems(orderItems);
		
		Order savedOrder = orderRepository.save(order);
		return OrderMapper.toDTO(savedOrder);
	}

	@Override
	public OrderDTO getOrderById(Long orderId) throws Exception {
		return OrderMapper.toDTO(orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found with id: " + orderId)));
	}

	@Override
	public List<OrderDTO> getOrderByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType,
			OrderStatus status) {
		List<Order> orders = orderRepository.findByBranchId(branchId);
		
		return orders.stream().filter(order -> customerId == null || (order.getCustomer() != null && order.getCustomer().getId().equals(customerId)))
				.filter(order -> cashierId == null || (order.getCashier() != null && order.getCashier().getId().equals(cashierId)))
				.filter(order -> paymentType == null || order.getPaymentType() == paymentType)
				.map(OrderMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<OrderDTO> getOrderByCashier(Long cashierId) {
		List<Order> orders = orderRepository.findByCashierId(cashierId);
		
		return orders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteOrder(Long orderId) throws Exception {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found with id: " + orderId));
		
		orderRepository.delete(order);
	}

	@Override
	public List<OrderDTO> getTodayOrdersByBranch(Long branchId) {
		LocalDate today = LocalDate.now();
		LocalDateTime start = today.atStartOfDay();
		LocalDateTime end = today.plusDays(1).atStartOfDay();
		
		return orderRepository.findByBranchIdAndCreatedAtBetween(branchId, start, end).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
		
		return orderRepository.findByCustomerId(customerId).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId) {
		return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(branchId).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
	}

}
