package ru.shopitem.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.shopitem.model.ShopItem;
import ru.shopitem.service.ShopItemService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = ItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
    static final String REST_URL = "/api/item";
    private final ShopItemService service;

    public ItemController(ShopItemService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShopItem> create(@Valid @RequestBody ShopItem item) {
        ShopItem newItem = service.create(item);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newItem.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(newItem);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ShopItem item, @PathVariable String id) {
        item.setId(id);
        service.update(item);
    }

    @GetMapping(value = "{id}")
    public ShopItem get(@PathVariable String id){
        return service.get(id);
    }

    @GetMapping(value = "/filter")
    public List<ShopItem> getByName(@RequestParam String name) {
        return service.getByName(name);
    }

    @GetMapping(value = "/filter/option")
    public List<ShopItem> getByOptionKeyAndValue(@RequestParam String key, @RequestParam String value){
        return service.getByOption(key, value);
    }
}
