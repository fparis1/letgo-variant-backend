package com.example.springsocial.controller;

import com.example.springsocial.dto.ItemDTO;
import com.example.springsocial.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/getItem")
    public List<ItemDTO> getItems() {

        // Return a response entity
        return itemService.getItems();
    }
}
