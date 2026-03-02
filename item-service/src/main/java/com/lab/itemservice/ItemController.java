package com.lab.itemservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final List<Map<String, Object>> items = new ArrayList<>();
    private int idCounter = 1;

    public ItemController() {
        // Pre-load some sample items
        addSample("Book");
        addSample("Laptop");
        addSample("Phone");
    }

    private void addSample(String name) {
        Map<String, Object> item = new HashMap<>();
        item.put("id", idCounter++);
        item.put("name", name);
        items.add(item);
    }

    @GetMapping
    public List<Map<String, Object>> getItems() {
        return items;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addItem(@RequestBody Map<String, Object> body) {
        Map<String, Object> item = new HashMap<>();
        item.put("id", idCounter++);
        item.put("name", body.get("name"));
        items.add(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable int id) {
        return items.stream()
                .filter(i -> i.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
