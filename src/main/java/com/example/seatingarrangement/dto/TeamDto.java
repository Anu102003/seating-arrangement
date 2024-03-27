package com.example.seatingarrangement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    @NotNull(message = "teamName must not be null")
    @JsonProperty("teamName")
    private String teamName;
    @NotNull(message = "teamCount must not be null")
    @Range(min = 1, message = "teamCount must be greater than 0")
    @JsonProperty("teamCount")
    private Integer teamCount;
}
