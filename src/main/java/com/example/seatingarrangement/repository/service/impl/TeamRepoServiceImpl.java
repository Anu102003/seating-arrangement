package com.example.seatingarrangement.repository.service.impl;

import com.example.seatingarrangement.dto.TeamDto;
import com.example.seatingarrangement.entity.Team;
import com.example.seatingarrangement.repository.service.TeamRepoService;
import com.example.seatingarrangement.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TeamRepoServiceImpl implements TeamRepoService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team findTeamsByTeamInfo(List<TeamDto> teamInfoList, int size) {
        return teamRepository.findTeamsByTeamInfo(teamInfoList, size);
    }

    @Override
    public Optional<Team> findByTeamId(String teamId) {
        return teamRepository.findByTeamId(teamId);
    }
}
