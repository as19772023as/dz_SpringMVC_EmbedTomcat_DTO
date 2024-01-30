package ru.strebkov.repository;

import org.springframework.stereotype.Repository;
import ru.strebkov.exception.NotFoundException;
import ru.strebkov.model.Post;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Repository
public class PostRepository implements PostRepositoryInterface {
    private final ConcurrentMap<Long, Post> list = new ConcurrentHashMap<>();
    private final static AtomicLong NAMBER_ID = new AtomicLong(0);

    public Collection<Post> all() {
        return list.values().stream()
                .filter(Post::isRemoved)
                .collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        Post postRemoved = list.get(id);
        return Optional.ofNullable(postRemoved.isRemoved() ? null : list.get(id));
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0 && post.isRemoved()) {
            long newId = NAMBER_ID.incrementAndGet();
            post.setId(newId);
            //post.setRemoved(false);
            list.put(newId, post);
        }
        if (list.containsKey(post.getId()) && post.isRemoved()) {
            list.put(post.getId(), post);
        } else {
            throw new NotFoundException("Элемент обновить не получилось");
        }
        return post;
    }

    @Override
    public void removeById(long id) {
        Post postRemoved = list.get(id);
        if(postRemoved != null){
            postRemoved.setRemoved(true);
        }
        throw new NotFoundException("Такого id нет");
    }
}
