package com.example.seatingarrangement.entity;

import com.example.seatingarrangement.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Company")
public class Company {

    @Id
    private String companyId;
    private String companyName;
    private List<DefaultLayout> companyLayout;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DefaultLayout {
        @UuidGenerator
        private String layoutId;
        private boolean isChanged;
        private int[][] companyLayout;
        private int totalSpace;
    }

}
