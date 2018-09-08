package manager;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import config.User;

import java.util.List;

public class CloudantDatabase {

    CloudantClient client = ClientBuilder.account("example")
            .username("exampleUser")
            .password("examplePassword")
            .build();
    Database db = client.database("example_db", false);

    public CloudantDatabase(){
    }

    public void listDatabase(){

    }

    public void save(List<User> users) {
        System.out.println("Server Version: " + client.serverVersion());

        List<String> databases = client.getAllDbs();
        System.out.println("All my databases : ");
        for (String db : databases) {
            System.out.println(db);
        }

        client.createDB("example_db");
        Database db = client.database("example_db", false);
        db.bulk(users);
    }
}
