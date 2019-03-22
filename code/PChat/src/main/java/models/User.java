package models;

public class User {
    public String email, password;
    UserMeta userMeta;

    public User(){}

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, UserMeta userMeta){
        this(email, password);
        this.userMeta = userMeta;
    }

    @Override
    public String toString() {
        return "email: " + email + "\n" + "password: " + password;
    }
}
