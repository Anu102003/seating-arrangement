package com.example.seatingarrangement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLayoutDto {

    private int[][] layout;

    private int availableSpaces;
    private String companyName;
}
