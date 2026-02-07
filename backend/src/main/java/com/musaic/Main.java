package com.musaic;
import static spark.Spark.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Hello world!");

        //This is the starting point to create a port for the server
        // 1) Port (Spark default is 4567, but making it explicit helps)
        port(4567);

        // 2) Very basic route to confirm server is alive
        get("/health", (req, res) -> {
            res.type("text/plain");
            return "ok";
        });
        // Force Spark to actually start
        init();
        awaitInitialization();

        System.out.println("Server running on http://localhost:4567/health");

        // Keep the JVM alive no matter what VS Code tries to do
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            // allow exit
        }
        System.out.println("Server running on http://localhost:4567/health");
        // System.out.println("Enter a term: ");
        // String name = input.nextLine().trim();
    }
}