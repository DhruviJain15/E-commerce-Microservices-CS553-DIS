package com.rutgers.inventory.service.inventoryService.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.rutgers.inventory.service.inventoryService.dto.InventoryResponse;
import com.rutgers.inventory.service.inventoryService.model.Inventory;
import com.rutgers.inventory.service.inventoryService.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse>  isInStock(List<String> skuCode, List<Integer> quantity){

        List<InventoryResponse> inventoryResponses = new ArrayList<>();
        for(int i=0;i<skuCode.size();i++) {
            InventoryResponse inventoryResponse = new InventoryResponse();
            inventoryResponse.setSkuCode(skuCode.get(i));
            //inventoryResponse.setQuantity(quantity.get(i));
            if (inventoryRepository.findBySkuCode(skuCode.get(i)).isPresent()) {
                Inventory ir = inventoryRepository.findBySkuCode(skuCode.get(i)).orElseThrow(() -> new ResourceAccessException("Product Not Present"));

                if((ir.getQuantity()-quantity.get(i)) >=0) {
                    inventoryResponse.setInStock(true);
                }else{
                    inventoryResponse.setInStock(false);
                }
            } else {
                inventoryResponse.setInStock(false);
            }
            inventoryResponses.add(inventoryResponse);
        }
        return inventoryResponses;

//        List<InventoryResponse> inventoryResponses = new ArrayList<>();
//        for(int i=0;i<skuCode.size();i++) {
//            InventoryResponse inventoryResponse = new InventoryResponse();
//            inventoryResponse.setSkuCode(skuCode.get(i));
//            if (inventoryRepository.findBySkuCode(skuCode.get(i)).isPresent()) {
//                Inventory ir = inventoryRepository.findBySkuCode(skuCode.get(i)).orElseThrow(() -> new ResourceAccessException("Product Not Present"));
//                if(ir.getQuantity() > 0) {
//                    inventoryResponse.setInStock(true);
//                }else{
//                    inventoryResponse.setInStock(false);
//                }
//            } else {
//                inventoryResponse.setInStock(false);
//            }
//            inventoryResponses.add(inventoryResponse);
//        }
//        return inventoryResponses;

//         return inventoryRepository.findBySkuCodeIn(skuCode).stream()
//                 .map(inventory ->
//                     InventoryResponse.builder().skuCode(inventory.getSkuCode())
//                             .isInStock(inventory.getQuantity() > 0 )
//                             .build()
//                 ).toList();
    }

    public String  updateQuantity(List<String> skuCode, List<Integer> quantity){
        for(int i=0;i<skuCode.size();i++) {
            Inventory ir = inventoryRepository.findBySkuCode(skuCode.get(i)).orElseThrow(() -> new ResourceAccessException("Product Not Present"));
            Integer quantity_count = ir.getQuantity() - quantity.get(i);
            ir.setQuantity(quantity_count);
            inventoryRepository.save(ir);
        }
        return "Inventory Updated";

    }

    public String createInventory(String skuCode, Integer quantity){
        Inventory ir = new Inventory();
        //ir.setId(Long.valueOf(UUID.randomUUID().toString()));
        if (inventoryRepository.findBySkuCode(skuCode).isPresent()) {
            Inventory is = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new ResourceAccessException("Product Not Present"));
            Integer quantity_count = is.getQuantity() + quantity;
            is.setQuantity(quantity_count);
            inventoryRepository.save(is);
        }
        else {
            ir.setSkuCode(skuCode);
            ir.setQuantity(quantity);
            Inventory returnedInventory = inventoryRepository.save(ir);

        }
        return "Inventory Created";
    }
}
