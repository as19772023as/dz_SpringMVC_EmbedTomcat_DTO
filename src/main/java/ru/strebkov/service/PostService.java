package ru.strebkov.service;

import org.springframework.stereotype.Service;
import ru.strebkov.exception.NotFoundException;
import ru.strebkov.model.Post;
import ru.strebkov.model.PostDTO;
import ru.strebkov.repository.PostRepositoryInterface;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class PostService {
    private final PostRepositoryInterface repository;
//    protected   ModelMapper modelMapper;

    public PostService(PostRepositoryInterface repository) {
        this.repository = repository;
    }

    public Collection<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }

}
