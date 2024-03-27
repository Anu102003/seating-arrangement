package com.example.seatingarrangement.repository;

import com.example.seatingarrangement.dto.GetAllocationDto;
import com.example.seatingarrangement.entity.Allocation;
import com.example.seatingarrangement.enums.Type;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllocationRepository extends MongoRepository<Allocation, String> {
    Optional<Allocation> findByDefaultLayoutIdAndAllocationTypeAndAlgorithmPref(String layoutId, Type allocationType, Integer allocationPref);

    @Aggregation({
            "{$match: {defaultLayoutId: '?0'}}",
            "{$sort: {allocationType: 1}}",
            "{$group: {_id: '$teamId', allocationDataList: {$push: '$$ROOT'}}}",
            "{$lookup: {from: 'Team', localField: '_id', foreignField: 'teamId', as: 'teamData'}}",
            "{$unwind: '$teamData'}",
            "{$project: {'data.teamId': 0, 'data.defaultLayoutId': 0, 'data._class': 0, 'result.layoutId': 0}}"
    })
    Optional<List<GetAllocationDto>> findByDefaultLayoutId(String layoutId);
}
