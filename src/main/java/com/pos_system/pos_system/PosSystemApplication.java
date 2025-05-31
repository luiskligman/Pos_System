package com.pos_system.pos_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import com.pos_system.pos_system.model.*;
import com.pos_system.pos_system.repository.*;


@SpringBootApplication
@EntityScan("com.pos_system.pos_system.model")
public class PosSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosSystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner testData (
			ProductRepository productRepository,
			StoreRepository storeRepository,
			InventoryRepository inventoryRepository
	) {
		return args -> {
			// Create and save store
			Store store = new Store("store1", "United States");
			storeRepository.save(store);

			// Create and save product
			Product product = new Product();
			product.setDesc1("Test Product");
			product.setPrice(new java.math.BigDecimal("19.99"));
			productRepository.save(product);

			// Create and save inventory record
			Inventory inventory = new Inventory(product, store);
			inventoryRepository.save(inventory);

			// Print out for confirmation
			System.out.println("Store saved: " + store.getName());
			System.out.println("Product saved: " + product.getDesc1());
			System.out.println("Inventory saved: " + inventory.getProduct().getDesc1() + " at " + inventory.getStore().getName());

		};
	}
}
