package com.example.common.models;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private static SessionManager instance;
    private final Map<String, UserSession> sessions;

    public SessionManager() {
        this.sessions = new ConcurrentHashMap<>();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    @CachePut(value = "user_session", key = "#email")
    public void createSession(String email, UserSession session) {
        sessions.put(email, session);
    }

    @Cacheable(value = "user_session", key = "#email")
    public UserSession getSession(String email) {
        return sessions.get(email);
    }

    @CacheEvict(value = "user_session", key = "#email")
    public void removeSession(String email) {
        sessions.remove(email);
    }

    public boolean validateSession(String email, String token) {
        UserSession session = sessions.get(email);
        return session != null &&
                session.getToken().equals(token) &&
                !session.isExpired();
    }

    public static void main(String[] args) {
        SessionManager sessionManager1 = SessionManager.getInstance();
        UserSession userSession = new UserSession(
                "token",
                new UserDTO(
                        Type.USER,
                        1,
                        "firstName",
                        "lastName@gmail.com",
                        "01101101111",
                        Status.ACTIVE
                ),
                Instant.now().plusSeconds(3600)
        );
        sessionManager1.createSession("lastName@gmail.com", userSession);
        SessionManager sessionManager2 = SessionManager.getInstance();
        UserSession userSession1 =sessionManager2.getSession("lastName@gmail.com");
        System.out.println(userSession1.getUserDTO().getName());
    }
}
