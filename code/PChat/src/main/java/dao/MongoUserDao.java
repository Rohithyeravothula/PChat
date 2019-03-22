package dao;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import models.User;
import models.UserMeta;
import org.bson.Document;

import javax.jws.soap.SOAPBinding;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


public class MongoUserDao implements UserDao {

    /*
    *  todo: add codec support instead of handling on our end
    * */

    private MongoClient mongoClient;
    private MongoDatabase userDb;
    private MongoCollection<Document> userCollection;
    Gson gson = new Gson();
    private final Morphia morphia = new Morphia();
    private Datastore datastore;

    MongoUserDao(MongoClient mongoClient){
        this.mongoClient = mongoClient;
        this.userDb = mongoClient.getDatabase("userdb");
        this.userCollection = this.userDb.getCollection("users");

        this.morphia.mapPackage("models.User");
        this.datastore = morphia.createDatastore(this.mongoClient, "userdb");
    }

    public boolean insertUser(User user) {
        try{
            this.datastore.save(user);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean deleteUser(User user) {
        return false;
    }

    public boolean authenticate(String email, String password) {
        Query<User> q = this.datastore.createQuery(User.class);
        q.and(q.criteria("email").equal(email),
                q.criteria("password").equal(password));
        List<User> response = q.asList();
        if(response.size() == 1)
            return true;
        return false;
    }

    public boolean updateUser(User user) {
        return false;
    }

}
