package ru.shopitem.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.shopitem.model.ShopItem;
import ru.shopitem.repository.ShopItemRepository;
import ru.shopitem.util.ValidationUtil;

import java.util.List;

@Service
public class ShopItemService {
    private final ShopItemRepository repository;

    public ShopItemService(ShopItemRepository repository) {
        this.repository = repository;
    }


    public ShopItem create(ShopItem item) {
        Assert.notNull(item, "shop item must not be null");
        ValidationUtil.checkIsNew(item);
        return repository.save(item);
    }

    public ShopItem update(ShopItem item) {
        Assert.notNull(item, "shop item must not be null");
        ValidationUtil.checkIsNotNew(item);
        return repository.save(item);
    }

    public ShopItem get(String id) {
        ShopItem result = repository.get(id);
        ValidationUtil.checkNotFound(result, "id=" + id);
        return result;
    }

    public List<ShopItem> getByName(String name) {
        Assert.notNull(name, "Param 'name' must be not null");
        return repository.getByName(name);
    }

    public List<ShopItem> getByOption(String key, String value) {
        Assert.notNull(key, "Key option must be not null");
        Assert.notNull(value, "Value option must be not null");
        return repository.getByOption(key, value);
    }

    public void delete(String id) {
        Assert.notNull(id, "id must be not null");
        ValidationUtil.checkNotFound(repository.delete(id), "id=" + id);
    }
}
