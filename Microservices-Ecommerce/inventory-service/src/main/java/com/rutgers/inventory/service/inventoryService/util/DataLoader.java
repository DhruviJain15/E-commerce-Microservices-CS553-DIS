package com.rutgers.inventory.service.inventoryService.util;

import com.rutgers.inventory.service.inventoryService.model.Inventory;
import com.rutgers.inventory.service.inventoryService.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;
    @Override
    public void run(String... args) throws Exception {
        Inventory inventory = new Inventory();
        inventory.setSkuCode("I_Phone_13_red");
        inventory.setQuantity(1000000);

        Inventory inventory1 = new Inventory();
        inventory1.setSkuCode("I_Phone_14_red");
        inventory1.setQuantity(1000000);

        Inventory inventory2 = new Inventory();
        inventory2.setSkuCode("I_Phone_12_red");
        inventory2.setQuantity(0);

        inventoryRepository.save(inventory);
        inventoryRepository.save(inventory1);
        inventoryRepository.save(inventory2);
    }
}
