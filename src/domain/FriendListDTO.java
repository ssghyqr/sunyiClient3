package domain;

public class FriendListDTO {
    private Long userId;
    private String userName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "FriendListDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
