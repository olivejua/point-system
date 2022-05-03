package dev.olivejua.pointsystem.service;

import dev.olivejua.pointsystem.domain.User;
import dev.olivejua.pointsystem.web.dto.PostRequest;

public interface PostService {
    Long write(PostRequest request, User author);
}
