package com.example.seatingarrangement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.spec.X509EncodedKeySpec;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeamObjectDto {

    private String layoutId;

    private List<TeamDto> teamDtoList;

    private Integer algorithmPref;

    private Integer preference;
}
//Asc =2 Desc =1  rand=3