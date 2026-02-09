package com.musaic;

import static spark.Spark.*;
import com.musaic.authentication.AuthService;

public class Main {
    public static void main(String[] args) {
        System.out.println("[Main] Starting Musaic backend...");

        try {
            System.out.println("[Main] Setting port...");
            port(4567); // if the server doesn't work on 4567 use 4568 and add PORT=4568 to mvn compile command

            System.out.println("[Main] Registering /health...");
            get("/health", (req, res) -> {
                res.type("text/plain");
                return "ok";
            });
            

            String issuer = "https://dev-gm2117rej4tw1lvw.us.auth0.com/";
            String audience = "https://api.musaic";
            System.out.println("[Main] Creating AuthService...");
            AuthService authService = new AuthService(issuer, audience);

            System.out.println("[Main] Calling AuthRoutes.register(...)");
            AuthRoutes.register(authService);

            System.out.println("[Main] init()");
            init();

            System.out.println("[Main] awaitInitialization()");
            awaitInitialization();

            System.out.println("[Main] Server running on http://localhost:4567/health");

            // Keep the server alive
            Thread.currentThread().join();

        } catch (InterruptedException ie) {
            System.out.println("[Main] Interrupted; shutting down.");
        } catch (Throwable t) {
            System.out.println("[Main] Startup failed:");
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }
}