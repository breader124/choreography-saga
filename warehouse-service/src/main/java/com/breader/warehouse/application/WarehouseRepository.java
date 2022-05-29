package com.breader.warehouse.application;

import com.breader.warehouse.domain.item.Item;
import com.breader.warehouse.domain.item.ItemId;

import java.util.Optional;

public interface WarehouseRepository {

    ItemId save(Item o);

    Optional<Item> fetch(ItemId id);

}
