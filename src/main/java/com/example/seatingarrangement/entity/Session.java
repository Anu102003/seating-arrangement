package com.example.seatingarrangement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String sessionId;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createDate;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastmodifiedDate;
    @ManyToOne()
    private CompanyDetails companyDetails;

    private LocalDateTime logoutTime;

}
