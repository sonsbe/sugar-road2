package com.example.sugarroad2.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreCreateRequest {
    private StoreRequest storeRequest;
    private List<MenuRequest> menuRequestList;
}