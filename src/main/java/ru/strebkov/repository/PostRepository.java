package ru.strebkov.repository;

import ru.strebkov.model.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostRepository {
    Collection<Post> all();

    Optional<Post> getById(long id);

    Post save(Post post);

    void removeById(long id);
}
