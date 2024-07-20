package com.example.springsocial.service;

import com.example.springsocial.dto.ItemDTO;
import com.example.springsocial.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    ResponseEntity<String> postItem(String title, String description, Double price, MultipartFile[] files) throws IOException;

    List<ItemDTO> getItems();

}
