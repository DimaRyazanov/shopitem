package ru.shopitem.repository;

import ru.shopitem.model.ShopItem;

import java.util.List;

public interface ShopItemRepository {
    ShopItem save(ShopItem item);
    ShopItem get(String id);
    List<ShopItem> getByName(String name);
    List<ShopItem> getByOption(String key, String value);
    boolean delete(String id);
}
