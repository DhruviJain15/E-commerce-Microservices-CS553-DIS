package com.rutgers.inventory.service.inventoryService.controller;

import com.rutgers.inventory.service.inventoryService.dto.InventoryResponse;
import com.rutgers.inventory.service.inventoryService.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> skuCode,@RequestParam List<Integer>quantity){
        List<InventoryResponse> result = inventoryService.isInStock(skuCode, quantity);
        return ResponseEntity.ok(result);
    }

    @PatchMapping
    public ResponseEntity<String>  updateQuantity(@RequestParam List<String> skuCode,@RequestParam List<Integer> quantity){
        String message = inventoryService.updateQuantity(skuCode, quantity);
        return ResponseEntity.ok(message);
    }

    @PostMapping
    public ResponseEntity<String>  createInventory(@RequestParam String skuCode,@RequestParam Integer quantity){
        String inventoryMessage = inventoryService.createInventory(skuCode, quantity);
        return ResponseEntity.ok(inventoryMessage);
    }
}
