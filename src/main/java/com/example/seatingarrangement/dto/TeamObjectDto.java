package com.example.seatingarrangement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamObjectDto {
    @NotNull(message = "layout Id must not be null")
    private String layoutId;
    @NotNull(message = "atleast one team must be present")
    private List<TeamDto> teamDtoList;
    @NotNull(message = "algorithm preference must be present")
    @Range(min = 1, max = 2, message = "algorithm preference must be 1 or 3")
    private Integer algorithmPref;
    @NotNull(message = "preference must be present")
    @Range(min = 1, max = 3, message = "preference must be between 1 and 3")
    private Integer preference;
}
//Asc =2 Desc =1  rand=3