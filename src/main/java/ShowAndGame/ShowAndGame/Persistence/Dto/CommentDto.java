package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.Comment;

public class CommentDto {
    private String description;
    private String username;
    private String userProfileImage;

    public CommentDto(Comment comment){
        this.description = comment.getDescription();
        this.username = comment.getUser().getUsername();
        this.userProfileImage = comment.getUser().getProfileImage();
    }
}
