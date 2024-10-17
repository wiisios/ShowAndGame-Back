package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable = false)
    private long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private int rating;
    @Column
    private String profileImage;
    @Column
    private String backgroundImage;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedPost> feedPosts;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewPost> reviews;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id")
    private User owner;
    private Integer followerAmount;
    @ManyToMany
    @JoinTable(
            name = "game_tags",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "gameFollowed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> follows;

    public Game() {
    }

    public Game(String name, String description, int rating, String profileImage, String backgroundImage, List<FeedPost> feedPosts, User owner, List<Tag> tags, Integer followerAmount, List<Follow> follows) {
        this.name = name;
        this.description = description;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
        this.rating = rating;
        this.feedPosts = feedPosts;
        this.owner = owner;
        this.tags = tags;
        this.followerAmount = followerAmount;
        this.follows = follows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<FeedPost> getPosts() {
        return feedPosts;
    }

    public void setPosts(List<FeedPost> feedPosts) {
        this.feedPosts = feedPosts;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<ReviewPost> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewPost> reviews) {
        this.reviews = reviews;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Integer getFollowerAmount() {
        return followerAmount;
    }

    public void setFollowerAmount(Integer followerAmount) {
        this.followerAmount = followerAmount;
    }

    public List<Follow> getFollows() {
        return follows;
    }

    public void setFollows(List<Follow> follows) {
        this.follows = follows;
    }
}
