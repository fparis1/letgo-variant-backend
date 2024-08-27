package com.example.springsocial.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class SpecificItemDTO {
    private Long id;
    private String title;
    private String description;
    private String price;
    private List<PhotoDTO> photos;
    private UserDTO user;
    private double latitude;
    private double longitude;
    private Boolean radius;
    private LocalDateTime createdDate;
    private String category;
    private String subcategory;
    private String county;
    private String city;
    private String settlement;
}