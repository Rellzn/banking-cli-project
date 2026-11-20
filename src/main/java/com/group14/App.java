package com.group14;

import com.group14.config.ApplicationContext;
import com.group14.exception.BankingException;

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
    }

    // public static void postgresConnectionTest() {
        
    // }

    // public static MongoClient getMongoClient() {
        
    // }
}