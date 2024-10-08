package com.example.springsocial.service;

import com.example.springsocial.dto.base.ItemDTO;
import com.example.springsocial.dto.PhotoDTO;
import com.example.springsocial.dto.SpecificItemDTO;
import com.example.springsocial.dto.UserDTO;
import com.example.springsocial.model.Item;
import com.example.springsocial.model.Photo;
import com.example.springsocial.model.User;
import com.example.springsocial.repository.ItemRepository;
import com.example.springsocial.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<String> postItem(String title, String description, Double price, MultipartFile[] files,
                                           String email, String category, String subcategory, String county,
                                           String city, String settlement, double latitude, double longitude,
                                           Boolean radius) throws IOException {

        List<Photo> photoList = new ArrayList<>();

        Item item = Item.builder()
                .title(title)
                .description(description)
                .price(price.toString())
                .category(category)
                .subcategory(subcategory)
                .createdDate(LocalDateTime.now())
                .county(county)
                .city(city)
                .settlement(settlement)
                .latitude(latitude)
                .longitude(longitude)
                .radius(radius)
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

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        item.setPhotos(photoList);
        item.setUser(user);

        itemRepository.saveAndFlush(item);

        return ResponseEntity.ok("Item saved successfully");
    }

    @Override
    public Page<ItemDTO> getItems(Pageable pageable) {
        Page<Item> items = itemRepository.getAllBy(pageable);
        return items.map(this::convertToDTO);
    }

    @Override
    public Page<ItemDTO> getItemsByCategory(String category, Pageable pageable) {
        Page<Item> items = itemRepository.findByCategory(category, pageable);
        return items.map(this::convertToDTO);
    }

    @Override
    public Page<ItemDTO> getItemsByCategoryAndSubcategory(String category, String subcategory, Pageable pageable) {
        Page<Item> items = itemRepository.findByCategoryAndSubcategory(category, subcategory, pageable);
        return items.map(this::convertToDTO);
    }

    @Override
    public SpecificItemDTO getSpecificItem(String itemIdentifier) {

        // Custom repository method to fetch item with photos in a single query
        Item item = itemRepository.findItemWithPhotosById(Long.parseLong(itemIdentifier))
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Manually map Item to SpecificItemDTO using builder pattern

        return SpecificItemDTO.builder()
                .id(item.getId())
                .title(item.getTitle())
                .price(item.getPrice())
                .latitude(item.getLatitude())
                .longitude(item.getLongitude())
                .radius(item.getRadius())
                .createdDate(item.getCreatedDate())
                .category(item.getCategory())
                .subcategory(item.getSubcategory())
                .county(item.getCounty())
                .city(item.getCity())
                .settlement(item.getSettlement())
                .description(item.getDescription())
                .photos(item.getPhotos().stream()
                        .map(photo -> PhotoDTO.builder()
                                .id(photo.getId())
                                .fileName(photo.getFileName())
                                .contentType(photo.getContentType())
                                .data(photo.getData())
                                .build())
                        .collect(Collectors.toList()))
                .user(UserDTO.builder()
                        .email(item.getUser().getEmail())
                        .name(item.getUser().getName())
                        .imageUrl(item.getUser().getImageUrl())
                        .build())
                .build();
    }

    private ItemDTO convertToDTO(Item item) {
        PhotoDTO photoDTO = convertToDTO(item.getPhotos().get(0));
        return ItemDTO.builder()
                .id(item.getId())
                .title(item.getTitle())
                .price(item.getPrice())
                .county(item.getCounty())
                .city(item.getCity())
                .createdDate(item.getCreatedDate())
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
