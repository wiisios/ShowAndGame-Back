package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Entities.Post;
import ShowAndGame.ShowAndGame.Persistence.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public Post create(Post post) {
        return postRepository.save(post);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Optional<Post> search(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> searchAll() {
        return postRepository.findAll();
    }

    public Post update(Post post) {
        return postRepository.save(post);
    }
}
