package com.example.seatingarrangement.repository.service;


import com.example.seatingarrangement.dto.TeamDto;
import com.example.seatingarrangement.entity.Team;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TeamRepoService {
    Team findTeamsByTeamInfo(List<TeamDto> teamInfoList, int size);

    Optional<Team> findByTeamId(String teamId);
}

