package ru.shopitem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(name = ShopItem.GET_BY_NAME,
                query = "select items from ShopItem items where items.name=:name")
})
@Entity
@Table(name = "ShopItem", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "name_idx")})
public class ShopItem extends AbstractBaseEntity{

    public static final String GET_BY_NAME = "ShopItem.getByName";

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> options = new HashMap<>();

    public ShopItem() {
    }

    public ShopItem(String name, String description, Map<String, String> options) {
        this.name = name;
        this.description = description;
        this.options = options;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", options=" + options +
                '}';
    }


}
