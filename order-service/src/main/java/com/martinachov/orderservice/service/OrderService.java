package com.martinachov.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.martinachov.orderservice.dto.InventoryResponse;
import com.martinachov.orderservice.dto.OrderLineItemDto;
import com.martinachov.orderservice.dto.OrderRequest;
import com.martinachov.orderservice.model.Order;
import com.martinachov.orderservice.model.OrderLineItem;
import com.martinachov.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBulder;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItem::getSkuCode)
                .collect(Collectors.toList());

        //Call Inventory Service, and place order if product is in stock
        InventoryResponse[] inventoryResponses = webClientBulder.build().get()
                                    .uri("http://inventory-service/api/inventory/", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                                    .retrieve()
                                    .bodyToMono(InventoryResponse[].class)
                                    .block();

        if(allProductsInStock(inventoryResponses)){
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock!");
        }
    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemsDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemsDto.getPrice());
        orderLineItem.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItem;
    }

    private Boolean allProductsInStock(InventoryResponse[] inventoryResponsArray) {
        return Arrays.stream(inventoryResponsArray).allMatch(InventoryResponse::isInStock);
    }
}