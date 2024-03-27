package com.example.seatingarrangement.repository;

import com.example.seatingarrangement.dto.GetLayoutDto;
import com.example.seatingarrangement.entity.Company;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

    Optional<Company> findByCompanyName(String companyName);


    @Aggregation(pipeline = {"{'$unwind': {'path': '$companyLayout'}}",
            "{'$match': {'companyLayout.layoutId': ?0}}",
            "{'$addFields': {'availableSpaces': '$companyLayout.totalSpace','layout': '$companyLayout.companyLayout'}}",
            "{'$project': {'layout':1,'availableSpaces': 1,'_id': 0,'companyName': 1}}"})
    GetLayoutDto findByLayoutId(String layoutId);
}
