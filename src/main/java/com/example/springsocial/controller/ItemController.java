package com.example.springsocial.controller;

import com.example.springsocial.dto.base.ItemDTO;
import com.example.springsocial.dto.SpecificItemDTO;
import com.example.springsocial.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
            @RequestParam("file") MultipartFile[] files,
            @RequestParam("email") String email,
            @RequestParam("category") String category,
            @RequestParam("subcategory") String subcategory) throws IOException {

        // Return a response entity
        return itemService.postItem(title, description, price, files, email, category, subcategory);
    }

    @GetMapping("/getItems")
    public Page<ItemDTO> getItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return itemService.getItems(pageable);
    }

    @GetMapping("/getSpecificItem/{itemIdentifier}")
    public SpecificItemDTO getSpecificItem(@PathVariable String itemIdentifier) {

        // Return a response entity
        return itemService.getSpecificItem(itemIdentifier);
    }
}
