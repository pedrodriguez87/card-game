package tech.bts.cardgame.service;

public class JoinGame {

    private long gameId;
    private String username;

    public JoinGame() {
        // Needed to POST in Spring Boot
    }

    public long getGameId() {
        return gameId;
    }

    public String getUsername() {
        return username;
    }
}
