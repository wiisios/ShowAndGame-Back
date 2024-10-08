package ShowAndGame.ShowAndGame.Persistence.Dto.CommentDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.Comment;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;

public class GetCommentForUpdateDto {
    private String description;

    public GetCommentForUpdateDto(Comment comment, User user){
        this.description = comment.getDescription();
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
