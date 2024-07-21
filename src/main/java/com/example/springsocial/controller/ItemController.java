package com.example.springsocial.controller;

import com.example.springsocial.dto.ItemDTO;
import com.example.springsocial.dto.SpecificItemDTO;
import com.example.springsocial.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/postItem")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> postItem(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("file") MultipartFile[] files) throws IOException {

        // Return a response entity
        return itemService.postItem(title, description, price, files);
    }

    @GetMapping("/getItems")
    public List<ItemDTO> getItems() {

        // Return a response entity
        return itemService.getItems();
    }

    @GetMapping("/getSpecificItem/{itemIdentifier}")
    public SpecificItemDTO getSpecificItem(@PathVariable String itemIdentifier) {

        // Return a response entity
        return itemService.getSpecificItem(itemIdentifier);
    }
}
