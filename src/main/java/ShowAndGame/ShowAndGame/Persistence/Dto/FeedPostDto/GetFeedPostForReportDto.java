package ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;

import java.time.LocalDate;

public class GetFeedPostForReportDto {
    private Long id;
    private Integer likesCounter;
    private LocalDate date;
    private String username;

    public GetFeedPostForReportDto(FeedPost feedPost) {
        this.id = feedPost.getId();
        this.likesCounter = feedPost.getLikesCounter();
        this.date = feedPost.getDate();
        this.username = feedPost.getUser().getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(Integer likesCounter) {
        this.likesCounter = likesCounter;
    }

}
