package com.musaic;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.musaic.AuthRoutes;
import com.musaic.authentication.AuthService;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // This is the starting point to create a port for the server
       // 1) Port (Spark default is 4567, but making it explicit helps)
       port(4567);


       // 2) Very basic route to confirm server is alive
       get("/health", (req, res) -> {
           res.type("text/plain");
           return "ok";
       });


       // Auth0 config
       String issuer = "https://dev-gm2117rej4tw1lvw.us.auth0.com/";
       String audience = "https://api.musaic";
       AuthService authService = new AuthService(issuer, audience);


       // Register routes (and the auth filter) in one place:
       AuthRoutes.register(authService);


       init();
       awaitInitialization();


       System.out.println("Server running on http://localhost:4567/health");

    }
}