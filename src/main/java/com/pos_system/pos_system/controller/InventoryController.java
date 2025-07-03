package com.pos_system.pos_system.controller;

// DTOs define the shape of data sent to/from the client
import com.pos_system.pos_system.dto.CreateInventoryRequest;
import com.pos_system.pos_system.dto.AdjustInventoryRequest;
import com.pos_system.pos_system.dto.InventoryDto;

// Custom exception type for 404 Not Found
import com.pos_system.pos_system.exception.NotFoundException;

// Mapper handles conversion between domain entities and DTOs
import com.pos_system.pos_system.mapper.InventoryMapper;

// Spring Web annotations for REST controllers
import org.springframework.web.server.ResponseStatusException;
import com.pos_system.pos_system.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// Utility for converting lists
import java.util.stream.Collectors;
import java.util.List;

/**
 * REST controller for managing Inventory entries.
 * Inventory links a Product and a Store, tracking the quantity available at each location.
 * This controller exposes CRUD operations:
 * - List inventory by store
 * - Retrieve a single inventory entry
 * - Create new inventory
 * - Adjust quantity on hand
 * - Delete inventory entries
 */
@RestController  // Marks this class as a REST endpoint handler
@RequestMapping("/api/inventory")  // Base URL path for all endpoints in this controller
public class InventoryController {

    // Final field holds a reference to the service; ensures thread-safety
    private final InventoryService inventoryService;

    /**
     * Constructor-based dependency injection.
     * Spring will automatically wire an InventoryService instance here.
     * Using constructor injection makes the field easier to test and immutable.
     *
     * @param inventoryService the service that contains business logic for inventories
     */
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;  // assign injected service to the field
    }

    /**
     * GET /api/inventory?storeId={storeId}
     * Retrieves all inventory entries for a given store ID.
     * The client supplies storeId as a query parameter.
     *
     * @param storeId the ID of the store whose inventory we want to list
     * @return a List of InventoryDto objects ( one per inventory row )
     */
    @GetMapping  // HTTP GET at /api/inventory
    public List<InventoryDto> getAllInventory(@RequestParam Long storeId) {
        // 1. Call service to fetch List<Inventory> from the database
        // 2. Convert each Inventory entity into an InventoryDto via InventoryMapper
        // 3. Collect results into a List and return to client ( auto-serialized to JSON )

        return inventoryService.getInventoryByStoreId(storeId)
                .stream()  // convert List to Stream for transformation
                .map(InventoryMapper::toDto)  // map each Inventory -> InventoryDto
                .collect(Collectors.toList());  // gather back into a List
    }

    /**
     * GET /api/inventory/{id}
     * Retrieves a single inventory entry by its unique ID.
     *
     * @param id the inventory record ID ( path variable )
     * @return the InventoryDto representing the record
     * @throws NotFoundException if no inventory with the given ID exists
     */
    @GetMapping("/{id}")
    public InventoryDto getInventoryById(@PathVariable Long id) {

        // Service returns Optional<Inventory>; if present, map to DTO
        // If not, throw NotFoundException ( handled globally by @ControllerAdvice )
        return inventoryService.findInventoryById(id)
                .map(InventoryMapper::toDto)  // convert entity to DTO
                .orElseThrow(() ->  // if empty, throw exception
                        new NotFoundException("Inventory not found with id: " + id));
    }

    /**
     * POST /api/inventory
     * Create a new inventory record linking a store and product.
     * Client must send a JSON ody matching CreateInventoryRequest:
     * {
     *     "storeId": 1,
     *     "productId": 42,
     *     "initialQuantity": 100
     * }
     * @param request the payload containing storeId, productId, and initialQuantity
     * @return the newly created InventoryDto ( with generated ID and stored quantity )
     */
    @PostMapping  // HTTP POST at /api/inventory
    public InventoryDto createInventory(@RequestBody CreateInventoryRequest request) {
        // Directly map service-created entity to DTO without intermediate variable
        return InventoryMapper.toDto(
                inventoryService.createInventory(
                        request.getStoreId(),  // store ID from client
                        request.getProductId(),  // product ID from client
                        request.getInitialQuantity()  // initial quantity from client
                )
        );
    }

    /**
     * PUT /api/inventory
     * Adjusts the quantity on hand by a delta for a store-product entry.
     * Client must send JSON matching AdjustInventoryRequest:
     * {
     *     "storeId": 1,
     *     "productId": 42,
     *     "delta": -5
     * }
     *
     * @param request payload with storeId, productId, and delta adjustment
     * @return updated InventoryDto after applying adjustment
     * @throws ResponseStatusException(HttpStatus.BAD_REQUEST) on invalid adjustment
     */
    @PutMapping("/{id}")
    public InventoryDto adjustInventory(@RequestBody AdjustInventoryRequest request) {
        try {
            return InventoryMapper.toDto(
                    inventoryService.adjustInventory(
                            request.getStoreId(),
                            request.getProductId(),
                            request.getDelta()
                    )
            );
        } catch (IllegalArgumentException exception) {
            // Wrap any invalid-argument exception as a 400 Bad Request
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        }
    }

    /**
     * DELETE /api/inventory/{id}
     * Deletes an inventory record by its ID.
     * Responds with HTTP 204 No Content on success.
     *
     * @param id ID of the inventory record to delete
     * @throws NotFoundException when no record exists for the given ID
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
    }

}
