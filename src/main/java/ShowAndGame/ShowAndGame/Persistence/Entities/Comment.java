package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_id")
    private FeedPost feedPost;

    public Comment(){
    }

    public Comment(String description, User user, FeedPost feedPost){
        this.description = description;
        this.user = user;
        this.feedPost = feedPost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FeedPost getfeedPost() {
        return feedPost;
    }

    public void setFeedPost(FeedPost feedPost) {
        this.feedPost = feedPost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
