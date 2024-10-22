package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.*;

@Entity
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userWhoLiked;

    @ManyToOne
    @JoinColumn(name = "feed_post_id", nullable = false)
    private FeedPost feedPostLiked;
    private boolean isLiked;

    public UserLike(){

    }

    public UserLike(Long id, User userWhoLiked, FeedPost feedPostLiked, boolean isLiked) {
        this.id = id;
        this.userWhoLiked = userWhoLiked;
        this.feedPostLiked = feedPostLiked;
        this.isLiked = isLiked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserWhoLiked() {
        return userWhoLiked;
    }

    public void setUserWhoLiked(User userWhoLiked) {
        this.userWhoLiked = userWhoLiked;
    }

    public FeedPost getFeedPostLiked() {
        return feedPostLiked;
    }

    public void setFeedPostLiked(FeedPost feedPostLiked) {
        this.feedPostLiked = feedPostLiked;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
