package com.example.seatingarrangement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllocationDto {


    private List<AllocationDataDto> allocationDataList;

    private TeamDataDto teamData;
}
