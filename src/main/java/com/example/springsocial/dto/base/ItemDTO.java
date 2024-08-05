package com.example.springsocial.dto.base;

import com.example.springsocial.dto.PhotoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    private String title;
    private String description;
    private String price;
    private PhotoDTO photo;
    private LocalDate createdDate;
    private String county;
    private String city;
    private String settlement;
}
