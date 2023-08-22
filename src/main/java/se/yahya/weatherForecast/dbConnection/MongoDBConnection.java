package se.yahya.weatherForecast.dbConnection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Service;

@Service
public class MongoDBConnection {
    private static final String database_url = "mongodb://localhost:27017";
    private static final String database_name = "weatherForecast";

    private MongoClient client;
    private MongoDatabase database;

    public MongoDBConnection(){
        client = MongoClients.create(database_url);
        database = client.getDatabase(database_name);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void close(){
        client.close();
    }
}
