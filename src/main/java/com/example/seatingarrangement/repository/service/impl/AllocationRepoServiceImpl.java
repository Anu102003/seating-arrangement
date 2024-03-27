package com.example.seatingarrangement.repository.service.impl;

import com.example.seatingarrangement.dto.GetAllocationDto;
import com.example.seatingarrangement.entity.Allocation;
import com.example.seatingarrangement.enums.Type;
import com.example.seatingarrangement.repository.AllocationRepository;
import com.example.seatingarrangement.repository.service.AllocationRepoService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllocationRepoServiceImpl implements AllocationRepoService {

    @Autowired
    private AllocationRepository allocationRepository;


    @Override
    public Optional<Allocation> findByDefaultLayoutIdAndAllocationTypeAndAllocationPreference(String layoutId, Type allocationType, Integer allocationPref) {
        return allocationRepository.findByDefaultLayoutIdAndAllocationTypeAndAlgorithmPref(layoutId, allocationType, allocationPref);
    }

    @Override
    public Optional<List<GetAllocationDto>> findByDefaultLayoutId(String layoutId) {
        return allocationRepository.findByDefaultLayoutId(layoutId);
    }
}
