package dev.olivejua.pointsystem.service.impl;

import dev.olivejua.pointsystem.domain.Post;
import dev.olivejua.pointsystem.domain.User;
import dev.olivejua.pointsystem.repository.PostRepository;
import dev.olivejua.pointsystem.service.PostService;
import dev.olivejua.pointsystem.web.dto.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultPostService implements PostService {
    private final PostRepository postRepository;

    @Override
    public Long write(PostRequest request, User author) {
        Post post = Post.create(author, request.getTitle(), request.getContent());
        postRepository.save(post);

        return post.getId();
    }
}
