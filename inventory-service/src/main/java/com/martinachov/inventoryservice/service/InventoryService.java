package com.martinachov.inventoryservice.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.martinachov.inventoryservice.dto.InventoryResponse;
import com.martinachov.inventoryservice.model.Inventory;
import com.martinachov.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        List<Inventory> listInventories = inventoryRepository.findBySkuCodeIn(skuCodes);
        return listInventories.stream()
                                .map(inventory -> InventoryResponse.builder()
                                    .skuCode(inventory.getSkuCode())
                                    .isInStock(inventory.getQuantity() > 0)
                                    .build())
                                .collect(Collectors.toList());
        
    }
}
