package com.example.sugarroad2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StoreCreateRequestDTO {
    private StoreRequest storeRequest;
    private List<MenuRequest> menuRequestList;
}