package com.musaic;

import com.google.gson.Gson;
import com.musaic.authentication.AuthService;

import static spark.Spark.*;

public class AuthRoutes {
     public static void register(AuthService authService) {

Gson gson = new Gson();


       // Protect everything under /api/*
       before("/api/*", (req, res) -> {
           String authHeader = req.headers("Authorization");
           if (authHeader == null || !authHeader.startsWith("Bearer ")) {
               halt(401, "Missing or invalid Authorization header");
           }


           String token = authHeader.substring("Bearer ".length()).trim();


           try {
               String sub = authService.verifyAndGetSub(token);
               req.attribute("sub", sub);
           } catch (Exception e) {
               halt(401, "Invalid token");
           }
       });

       // Protected endpoint to prove auth works
       get("/api/me", (req, res) -> {
           res.type("application/json");
           String sub = req.attribute("sub");
           return gson.toJson(new MeResponse(sub));
       });
   }
    static class MeResponse {
       String sub;
       MeResponse(String sub) { this.sub = sub; }
   }



}
