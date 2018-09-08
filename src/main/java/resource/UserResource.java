package resource;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import config.User;
import config.UserDao;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserDao userDao;

    public UserResource(UserDao userService) {
        this.userDao = userService;
    }

    @POST
    @Path("register")
    public User request(
            @FormParam("name") String name,
            @FormParam("phone") String phone,
            @DefaultValue("0") @FormParam("latitude") Double latitude,
            @DefaultValue("0") @FormParam("longitude") Double longitude

    ) {
        User user = new User(new ObjectId(), phone, name, new double[]{latitude, longitude}, System.currentTimeMillis());
        userDao.createUser(user);
        return user;
    }

    @GET
    @Path("getbyphone")
    public Optional<User> getBynum(
            @QueryParam("phone") String phone

    ) {
        return userDao.getUserByPhone(phone);
    }

    @POST
    @Path("need")
    public User addItems(
            @QueryParam("user_id") ObjectId id,
            @QueryParam("items") Set<String> items
    ){
        userDao.addNeeds(items, id);
        return userDao.get(id);
    }

    @GET
    @Path("get")
    public User get(@QueryParam("user_id") ObjectId userId){
        return userDao.get(userId);
    }

    @GET
    @Path("all")
    public List<User> getUsers(){
        return userDao.getUsers();
    }

    @GET
    @Path("near")
    public Object getEntries(
            @NotNull @QueryParam("la") Float lat,
            @NotNull @QueryParam("lo") Float lon,
            @DefaultValue("10") @QueryParam("range") Integer range
    ) {
        return userDao.getUsers(lat, lon, range);
    }

    @GET
    @Path("process")
    public String exportData() throws Exception {
        String s =  readFileAsString("/Users/gopikrishnav.m./Downloads/rescue/d0.json");
        //System.out.println(s.exists());

        //System.out.println(s.substring(0,10));
        Document dbo = Document.parse(s);

        DBObject dbObject = (DBObject) JSON.parse(s);
        //Document d = dbo.get("data");
        return "Ok";
    }

    public static String readFileAsString(String fileName)throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}
