package com.pos_system.pos_system.mapper;

import com.pos_system.pos_system.model.Inventory;
import com.pos_system.pos_system.dto.InventoryDto;

/**
 * Mapper for converting between Inventory and InventoryDTO
 */
public class InventoryMapper {

    public static InventoryDto toDto(Inventory inv) {
        return new InventoryDto(
                inv.getId(),
                inv.getStore().getId(),
                inv.getProduct().getId(),
                inv.getQuantityOnHand()
        );
    }

    // Map from Dto back to Entity
    public static Inventory toEntity(InventoryDto dto) {
        Inventory inv = new Inventory();
        inv.setId(dto.getId());
        //TODO
        //inv.setStore(fetch store using dto.getStoreId());
        //inv.setProduct(fetch product using dto.getProductId());
        inv.setQuantityOnHand(dto.getQuantityOnHand());
        return inv;
    }
}
