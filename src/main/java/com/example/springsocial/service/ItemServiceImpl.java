package com.example.springsocial.service;

import com.example.springsocial.dto.ItemDTO;
import com.example.springsocial.dto.PhotoDTO;
import com.example.springsocial.model.Item;
import com.example.springsocial.model.Photo;
import com.example.springsocial.repository.ItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<String> postItem(String title, String description, Double price, MultipartFile[] files) throws IOException {

        List<Photo> photoList = new ArrayList<>();

        Item item = Item.builder()
                .title(title)
                .description(description)
                .price(price.toString())
                .build();

        for (MultipartFile file : files) {
            Photo photo = Photo.builder()
                    .fileName(file.getOriginalFilename())
                    .data(file.getBytes())
                    .contentType(file.getContentType())
                    .item(item)  // Set the item here
                    .build();
            photoList.add(photo);
        }

        item.setPhotos(photoList);

        itemRepository.saveAndFlush(item);

        return ResponseEntity.ok("Item saved successfully");
    }

    @Override
    public List<ItemDTO> getItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ItemDTO convertToDTO(Item item) {
        PhotoDTO photoDTO = convertToDTO(item.getPhotos().get(0));
        return ItemDTO.builder()
                .id(item.getId())
                .title(item.getTitle())
                .price(item.getPrice())
                .photo(photoDTO)
                .build();
    }

    private PhotoDTO convertToDTO(Photo photo) {
        return PhotoDTO.builder()
                .id(photo.getId())
                .fileName(photo.getFileName())
                .contentType(photo.getContentType())
                .data(photo.getData())
                .build();
    }
}
