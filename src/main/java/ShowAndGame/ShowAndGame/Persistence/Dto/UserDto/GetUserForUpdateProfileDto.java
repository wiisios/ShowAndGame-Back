package ShowAndGame.ShowAndGame.Persistence.Dto.UserDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.*;

public class GetUserForUpdateProfileDto {
    private String userName;
    private String profileImage;
    private String backgroundImage;

    public GetUserForUpdateProfileDto(){

    }
    public GetUserForUpdateProfileDto(User user) {
        this.userName = user.getUsername();
        this.profileImage = user.getProfileImage();
        this.backgroundImage = user.getBackgroundImage();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}