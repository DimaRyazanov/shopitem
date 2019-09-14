package ru.shopitem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shopitem.AbstractTestCase;
import ru.shopitem.TestMather;
import ru.shopitem.model.ShopItem;
import ru.shopitem.util.NotFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static ru.shopitem.ShopItemsTestData.PHONE;
import static ru.shopitem.ShopItemsTestData.TELEVISION;

class ShopItemServiceTest extends AbstractTestCase {

    @Autowired
    private ShopItemService service;

    @Test
    void createNewItem() throws Exception {
        Map<String, String> options = new HashMap<>();
        options.put("A", "Aaaa");
        options.put("B", "Bbbb");
        ShopItem testItem = new ShopItem("Test Item from Service", "Test Item from Service Description", options);
        ShopItem created = service.create(testItem);
        testItem.setId(created.getId());

        TestMather.assertMatch(created, testItem);
    }

    @Test
    void createNullItem() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
    }

    @Test
    void createNotNewItem() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> service.create(PHONE));
    }

    @Test
    void getById() throws Exception {
        ShopItem expected = service.get(TELEVISION.getId());
        TestMather.assertMatch(TELEVISION, expected);
    }

    @Test
    void getByUnknownId() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get("1234566789"));
    }

    @Test
    void getByName() throws Exception {
        List<ShopItem> expected = service.getByName("Samsung Galaxy S9");
        TestMather.assertMatch(Collections.singletonList(PHONE), expected);
    }

    @Test
    void getByNullName() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> service.get(null));
    }

    @Test
    void getByBadName() throws Exception {
        List<ShopItem> expected = service.getByName("Bad 123456 Name");
        assertEquals(expected.size(), 0);
    }

    @Test
    void getByOption() throws Exception {
        List<ShopItem> expected = service.getByOption("Battery", "3000mAh");
        TestMather.assertMatch(Collections.singletonList(PHONE), expected);
    }

    @Test
    void getByUnknownOption() throws Exception {
        List<ShopItem> expected = service.getByOption("Unknown Battery", "3000mAh Unknown");
        assertEquals(0, expected.size());
    }
}