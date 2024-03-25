package com.example.seatingarrangement.repository.service.impl;

import com.example.seatingarrangement.entity.Session;
import com.example.seatingarrangement.repository.SessionRepo;
import com.example.seatingarrangement.repository.service.SessionRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionRepoServiceImpl implements SessionRepoService {

    private final SessionRepo sessionRepo;

    @Override
    public void save(Session session) {
        sessionRepo.save(session);
    }

    @Override
    public Optional<Session> findBySessionId(String sessionId) {
        return sessionRepo.findBySessionId(sessionId);
    }
}
