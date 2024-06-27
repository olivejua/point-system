package dev.olivejua.pointsystem.service;

import dev.olivejua.pointsystem.domain.UserEntity;
import dev.olivejua.pointsystem.web.dto.PostRequest;

public interface PostService {
    Long write(PostRequest request, UserEntity author);
}
