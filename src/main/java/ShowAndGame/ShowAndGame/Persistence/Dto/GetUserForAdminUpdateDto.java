package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.UserRole;

public class GetUserForAdminUpdateDto {
    private String userName;
    private UserRole userRole;

    public GetUserForAdminUpdateDto( String userName, UserRole userRole) {
        this.userName = userName;
        this.userRole = userRole;
    }

    public GetUserForAdminUpdateDto() {
    }



    public String getUserName() {
        return userName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

}
