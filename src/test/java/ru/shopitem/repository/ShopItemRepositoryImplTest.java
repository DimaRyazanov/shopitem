package ru.shopitem.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shopitem.AbstractTestCase;
import ru.shopitem.TestMather;
import ru.shopitem.model.ShopItem;
import ru.shopitem.util.TransactionException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ru.shopitem.ShopItemsTestData.PHONE;
import static ru.shopitem.ShopItemsTestData.TELEVISION;


class ShopItemRepositoryImplTest extends AbstractTestCase {

    @Autowired
    private ShopItemRepository repository;

    @Test
    void saveNewItem() throws Exception {
        Map<String, String> options = new HashMap<>();
        options.put("CPU", "HP 8300");
        options.put("RAM", "8GB");
        options.put("OS", "Windows 10 Professional");
        options.put("SSD", "256GB");
        ShopItem computer = new ShopItem("HP Elite 8300 SFF", "HP Elite 8300 SFF Small Form Business", options);
        ShopItem created = repository.save(computer);
        computer.setId(created.getId());

        TestMather.assertMatch(created, computer);
    }

    @Test
    void saveNullItemTransactionException() throws Exception {
        assertThrows(TransactionException.class, () -> repository.save(null));
    }

    @Test
    void getByName() throws Exception {
        List<ShopItem> expected = repository.getByName("Samsung UN65RU7100FXZA");
        TestMather.assertMatch(Collections.singletonList(TELEVISION), expected);
    }

    @Test
    void getByBadNameReturnZeroItems() throws Exception {
        List<ShopItem> expected = repository.getByName("Bad name 11111");
        assertEquals(expected.size(), 0);
    }

    @Test
    void getById() throws Exception {
        ShopItem expected = repository.get(PHONE.getId());
        TestMather.assertMatch(PHONE, expected);
    }

    @Test
    void getByUnknownId() throws Exception {
        ShopItem expected = repository.get("11223344");
        assertNull(expected);
    }

    @Test
    void getByOptionKeyValue() throws Exception {
        List<ShopItem> items = repository.getByOption("Style", "S9");
        TestMather.assertMatch(Collections.singletonList(PHONE), items);
    }

    @Test
    void getByUnknownOptionKeyValue() throws Exception {
        List<ShopItem> items = repository.getByOption("Unknown Style", "S9");
        assertEquals(0, items.size());
    }
}