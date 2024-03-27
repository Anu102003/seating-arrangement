package com.example.seatingarrangement.entity;

import com.example.seatingarrangement.enums.Type;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Allocation")
public class Allocation {

    @Id
    @UuidGenerator
    private String allocationId;
    private String defaultLayoutId;
    private String teamId;
    private Integer algorithmPref;
    @CreatedDate
    Date createdDate;
    @Enumerated
    private Type allocationType;
    private String[][] allocationLayout;
}

