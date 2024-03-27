package com.example.seatingarrangement.dto;

import lombok.Data;

@Data
public class LayoutDto {
    private String layoutId;
    private String companyName;
    private int[][] defaultLayout;
}