package com.group14;

import org.bson.Document;

import com.group14.config.ApplicationContext;
import com.group14.exception.BankingException;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class App {
    public static void main(String[] args) {
        
        //postgresConnectionTest();
        //getMongoClient();


        try (ApplicationContext ctx = new ApplicationContext()) {
            System.out.println("db ready");
        } catch (BankingException e) {
            System.err.println("startup failed: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("cause: " + e.getCause().getMessage());
            }
            System.exit(1);
        }

        String uri = System.getenv("MONGODB_URI");
        if (uri == null) {
            System.err.println("MONGODB_URI ENVIRONMENT VARIABLE NOT SET");
            return;
        }

        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(uri)).serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();

        try (MongoClient client = MongoClients.create(settings)) {
            MongoDatabase db = client.getDatabase("testdb");

            //ping to check connection
            db.runCommand(new Document("ping", 1));
            System.out.println("Connected to MongoDB!");

        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
        }                                                
    }

    // public static void postgresConnectionTest() {
        
    // }

    // public static MongoClient getMongoClient() {
        
    // }
}