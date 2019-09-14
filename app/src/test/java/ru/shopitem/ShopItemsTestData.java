package ru.shopitem;

import ru.shopitem.model.ShopItem;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

public class ShopItemsTestData {
    public final static ShopItem PHONE;
    public final static ShopItem TELEVISION;

    static {
        Map<String, String> phoneOptions = new HashMap<>();
        phoneOptions.put("Color", "Black");
        phoneOptions.put("Size", "64GB");
        phoneOptions.put("Style", "S9");
        phoneOptions.put("Screen size", "5.8");
        phoneOptions.put("Battery", "3000mAh");
        phoneOptions.put("Weight", "163g");


        Map<String, String> televisionOptions = new HashMap<>();
        televisionOptions.put("Size", "65");
        televisionOptions.put("Style", "TV only");
        televisionOptions.put("HDR", "Yes");
        televisionOptions.put("HDMI/USB Ports", "3 / 2 + 1 Component Video");
        televisionOptions.put("Weight", "26kg");

        PHONE = new ShopItem("Samsung Galaxy S9",
                "Samsung Galaxy S9 G960U 64GB 4G - Midnight Black", phoneOptions);
        TELEVISION = new ShopItem("Samsung UN65RU7100FXZA",
                "Samsung UN65RU7100FXZA Flat 65-Inch 4K UHD 7 Series Ultra HD Smart TV with HDR and Alexa Compatibility",
                televisionOptions);
    }

    public static void populateTestData(EntityManager entityManager) {

        entityManager.persist(PHONE);
        entityManager.persist(TELEVISION);
    }

    public static void dropShopItem(EntityManager entityManager) {
        entityManager.createNativeQuery(TestQueries.DROP_ALL).executeUpdate();
    }

    public static void clearShopItem(EntityManager entityManager) {
        entityManager.createNativeQuery(TestQueries.REMOVE_ALL).executeUpdate();
    }
}
