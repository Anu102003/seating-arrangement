package com.example.seatingarrangement.repository;

import com.example.seatingarrangement.dto.TeamDto;
import com.example.seatingarrangement.entity.Team;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TeamRepository extends MongoRepository<Team, String> {
    @Aggregation(pipeline = {"{'$project':{'teams.teamCode': 0}}",
            "{'$match': {'teams': {'$all': ?0},'$expr':{'$eq':[{'$size': '$teams'},?1], }, },}"})
    Team findTeamsByTeamInfo(List<TeamDto> teamInfoList, int size);

    Optional<Team> findByTeamId(String teamId);
}
