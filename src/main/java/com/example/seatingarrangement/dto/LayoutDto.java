package com.example.seatingarrangement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LayoutDto {
    private String layoutId;
    @NotNull(message = "company name must not be null")
    private String companyName;
    private int[][] defaultLayout;
}