package com.example.seatingarrangement.dto;

import com.example.seatingarrangement.entity.TeamInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDataDto {
    private String teamId;

    private ArrayList<TeamInfo> teams;
}
