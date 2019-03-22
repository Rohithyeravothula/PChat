package dao;

import models.User;
import models.UserMeta;

interface UserDao {
    public boolean insertUser(User user);
    public boolean deleteUser(User user);
    public boolean authenticate(String email, String password);
    public boolean updateUser(User user);
}
