package ShowAndGame.ShowAndGame.Persistence.Dto.UserDto;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.GetGamesByUserDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.*;

import java.util.List;
import java.util.stream.Collectors;

public class GetUserByIdDto {
    private Long id;
    private String userName;
    private String email;
    private String profileImage;
    private String backgroundImage;
    private List<GetGamesByUserDto> followedGames;
    private List<GetGamesByUserDto> OwnedGames;



    public GetUserByIdDto(User user) {
        this.id = user.getId();
        this.userName = user.getUsername();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.backgroundImage = user.getBackgroundImage();
        this.followedGames = user.getFollowedGames().stream().map(game -> new GetGamesByUserDto(game)).collect(Collectors.toList());
    }

    public GetUserByIdDto(UserDev userDev){
        this.id = userDev.getId();
        this.userName = userDev.getUsername();
        this.email = userDev.getEmail();
        this.profileImage = userDev.getProfileImage();
        this.backgroundImage = userDev.getBackgroundImage();
        this.followedGames = userDev.getFollowedGames().stream().map(game -> new GetGamesByUserDto(game)).collect(Collectors.toList());
        this.OwnedGames = userDev.getOwnedGames().stream().map(game -> new GetGamesByUserDto(game)).collect(Collectors.toList());
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


    public List<GetGamesByUserDto> getFollowedGames() {
        return followedGames;
    }

    public void setFollowedGames(List<GetGamesByUserDto> followedGames) {
        this.followedGames = followedGames;
    }

    public List<GetGamesByUserDto> getOwnedGames() {
        return OwnedGames;
    }

    public void setOwnedGames(List<GetGamesByUserDto> ownedGames) {
        OwnedGames = ownedGames;
    }
}
