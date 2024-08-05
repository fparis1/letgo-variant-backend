package com.example.springsocial.dto;

import lombok.*;

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
}