package dao;


import com.mongodb.MongoClient;
import models.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class MongoUserDaoTest {
    MongoUserDao mongoUserDao = new MongoUserDao(new MongoClient());

    @Test
    public void testInsertUser(){
        User u1 = new User("hello@gmail.com", "hello123");
        mongoUserDao.insertUser(u1);
    }

    @Test
    public void testUserAuthenticationShouldReturnTrue(){
        String email = "hello@gmail.com", password = "hello123";
        User u1 = new User(email, password);
        mongoUserDao.insertUser(u1);
        assertTrue(mongoUserDao.authenticate(email, password));
    }

    @Test
    public void testUserAuthenticationShouldReturnFalse(){
        String email = "hello@gmail.com", password = "hello123";
        assertFalse(mongoUserDao.authenticate(email, password+"*"));
    }
}
