package com.example.seatingarrangement.repository.service;

import com.example.seatingarrangement.entity.Session;

import java.util.Optional;

public interface SessionRepoService {
    void save(Session session);

    Optional<Session> findBySessionId(String sessionId);
}
