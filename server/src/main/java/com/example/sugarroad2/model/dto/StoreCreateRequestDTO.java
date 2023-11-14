package com.example.sugarroad2.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreCreateRequestDTO {
    private StoreRequestDTO storeRequestDTO;
    private List<MenuRequestDTO> menuRequestListDTO;
}