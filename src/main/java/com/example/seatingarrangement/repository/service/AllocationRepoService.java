package com.example.seatingarrangement.repository.service;

import com.example.seatingarrangement.dto.GetAllocationDto;
import com.example.seatingarrangement.entity.Allocation;
import com.example.seatingarrangement.enums.Type;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface AllocationRepoService {


    Optional<Allocation> findByDefaultLayoutIdAndAllocationTypeAndAllocationPreference(String layoutId, Type allocationType, Integer allocationPref);

    Optional<List<GetAllocationDto>> findByDefaultLayoutId(String layoutId);

}
