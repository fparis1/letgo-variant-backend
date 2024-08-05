package com.example.springsocial.service;

import com.example.springsocial.dto.base.ItemDTO;
import com.example.springsocial.dto.SpecificItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ItemService {

    ResponseEntity<String> postItem(String title, String description, Double price, MultipartFile[] files, String email, String category, String subcategory) throws IOException;

    Page<ItemDTO> getItems(Pageable pageable);


    SpecificItemDTO getSpecificItem(String itemIdentifier);
}
