package com.example.seatingarrangement.repository;

import com.example.seatingarrangement.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SessionRepo extends JpaRepository<Session,Integer> {
    Optional<Session> findBySessionId(String sessionId);


//   Optional<Session> findBysessionId(String sessionId);
}
