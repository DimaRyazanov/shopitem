package ru.shopitem.model;

public class NativeQueries {
    private NativeQueries() {}

    public static String queryByOption(String key, String value) {
        return "db.ShopItem.find({'options." + key + "' : '" + value + "'})";
    }
}
