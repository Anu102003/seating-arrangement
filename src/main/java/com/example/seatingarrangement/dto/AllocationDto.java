package com.example.seatingarrangement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllocationDto {

    private String layoutId;
    private HashMap<String, Integer> toBeAllocated;
    private Integer preference;

}
