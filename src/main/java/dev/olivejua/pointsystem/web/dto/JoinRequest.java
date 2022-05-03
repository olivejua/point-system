package dev.olivejua.pointsystem.web.dto;

public class JoinRequest {
    private String name;

    public JoinRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
