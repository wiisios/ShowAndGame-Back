package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.*;

import java.util.List;

public class GetUserByIdDto {
    private Long id;
    private String userName;
    private String email;
    private String profileImage;
    private String backgroundImage;
    private List<Comment> comments;
    private List<Game> followedGames;
    private List<ReviewPost> reviewPosts;
    private List<FeedPost> feedPosts;
    private List<Game> ownedGames;

    public GetUserByIdDto(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.backgroundImage = user.getBackgroundImage();
        this.comments = user.getComments();
        this.followedGames = user.getFollowedGames();
        this.reviewPosts = user.getReviewPosts();
        this.feedPosts = user.getFeedPosts();
    }

    public GetUserByIdDto(UserDev userDev){
        this.id = userDev.getId();
        this.userName = userDev.getUsername();
        this.email = userDev.getEmail();
        this.profileImage = userDev.getProfileImage();
        this.backgroundImage = userDev.getBackgroundImage();
        this.comments = userDev.getComments();
        this.followedGames = userDev.getFollowedGames();
        this.reviewPosts = userDev.getReviewPosts();
        this.feedPosts = userDev.getFeedPosts();
        this.ownedGames = userDev.getOwnedGames();
    }

    public List<FeedPost> getFeedPosts() {
        return feedPosts;
    }

    public void setFeedPosts(List<FeedPost> feedPosts) {
        this.feedPosts = feedPosts;
    }

    public List<ReviewPost> getReviewPosts() {
        return reviewPosts;
    }

    public void setReviewPosts(List<ReviewPost> reviewPosts) {
        this.reviewPosts = reviewPosts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Game> getFollowedGames() {
        return followedGames;
    }

    public void setFollowedGames(List<Game> followedGames) {
        this.followedGames = followedGames;
    }

    public List<Game> getOwnedGames() {
        return ownedGames;
    }

    public void setOwnedGames(List<Game> ownedGames) {
        this.ownedGames = ownedGames;
    }
}
