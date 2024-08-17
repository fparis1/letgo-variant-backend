package com.example.springsocial.dto.base;

import com.example.springsocial.dto.PhotoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    private String title;
    private String price;
    private PhotoDTO photo;
    private LocalDateTime createdDate;
    private String county;
    private String city;
}
