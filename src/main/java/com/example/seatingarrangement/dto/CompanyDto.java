package com.example.seatingarrangement.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull(message = "company name must not be null")
    private String companyName;
    @NotNull(message = "atleast one layout must be present")
    @Size(min = 1, message = "atleast one layout must be present")
    private List<int[][]> companyLayoutList;
}
