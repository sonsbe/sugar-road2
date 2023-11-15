package com.example.sugarroad2.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreCreateRequestDTO {
    private StoreRequestDTO storeRequestDTO;
    private List<MenuRequestDTO> menuRequestListDTO;
}