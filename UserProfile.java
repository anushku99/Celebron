package xyz.itechsyst.celebron;

public class UserProfile {
    public String userName;

    public UserProfile() {

    }

    public UserProfile(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }
}
