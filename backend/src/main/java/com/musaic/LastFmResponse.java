package com.musaic;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LastFmResponse {
    int resultCount;
    LastFmResult[] results;

    /** This method {@code int} int that
     * represents the number of results
     * recieved from the json.
     * @return an int
     */
    public int getResultCount() {
        return resultCount;
    } // getResultCount

    /**
     * Returns the array of iTunes results that were parsed from the most recent API response.
     *
     * @return an array of {@code ItunesResult} objects
     */
    public LastFmResult[] getResults() {
        return results;
    } // getResults

       /** HTTP client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object


    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()                          // enable nice output when printing
        .create();                                    // builds and returns a Gson object

    /**
     * Creates a properly formatted URI for querying the Last.Fm API.
     *
     * @param user The user that we will be requesting the data of
     * @param limit, the rate limit of the query.
     * @return a {@code URI} object representing the full API query
     * 
     */
    public URI createTopArtistURL (String user, String limit) {
        String encoderUser = URLEncoder.encode(user, StandardCharsets.UTF_8);
        String encoderLimit = URLEncoder.encode(limit, StandardCharsets.UTF_8);

        String method = "user.gettopartists";
        String apiKey = "b9e48679fc282d14a704c27dc2a1e0b7";

        String query = String.format("?method=%s&user=%s&limit=%s&api_key=%s&format=json", 
           method, encoderUser, encoderLimit, apiKey);
        return URI.create("https://ws.audioscrobbler.com/2.0/" + query);
    } // createUrl

}