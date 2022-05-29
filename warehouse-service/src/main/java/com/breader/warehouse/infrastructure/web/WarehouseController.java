package com.breader.warehouse.infrastructure.web;

import com.breader.warehouse.application.WarehouseService;
import com.breader.warehouse.domain.item.ItemId;
import com.breader.warehouse.infrastructure.web.request.CreateItemRequest;
import com.breader.warehouse.infrastructure.web.response.CreateItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping("items")
    public ResponseEntity<CreateItemResponse> createItem(@RequestBody CreateItemRequest request) {
        ItemId itemId = warehouseService.createItem(request.name(), request.quantity());
        CreateItemResponse response = new CreateItemResponse(itemId);
        return ResponseEntity.ok(response);
    }

}
