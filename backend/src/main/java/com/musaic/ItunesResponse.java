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

/**
 * Represents a response from the iTunes Search API. This is used by Gson to
 * create an object from the JSON response body. This class is provided with
 * project's starter code, and the instance variables are intentionally set
 * to package private visibility.
 */
public class ItunesResponse {
    int resultCount;
    ItunesResult[] results;

    /** This method {@code int} int that
     * represents the number of results
     * reciebved from the json.
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
    public ItunesResult[] getResults() {
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
     * Creates a properly formatted URI for querying the iTunes Search API.
     *
     * @param term  the search term to query (e.g. artist or song name)
     * @param media the type of media to search for (e.g. "music", "movie")
     * @return a {@code URI} object representing the full API query
     */
    public URI createURI (String term, String media) {
        String newTerm = URLEncoder.encode(term, StandardCharsets.UTF_8);
        String limit = URLEncoder.encode("200", StandardCharsets.UTF_8);
        String query = String.format("?term=%s&media=%s&limit=%s", newTerm, media, limit);
        return URI.create("https://itunes.apple.com/search" + query);
    } // createUrl

    /**
     * Sends a request to the iTunes Search API using the provided search term and media type.
     * Parses the JSON response into {@code ItunesResult[]} and stores it for later use.
     *
     * @param term  the search term (e.g. artist name)
     * @param media the media type to search for (e.g. "music")
     */
    public void sendRequest(String term, String media) {
        /** HTTP client. */
        URI userURI = createURI(term, media);
        HttpRequest request = HttpRequest.newBuilder().uri(userURI).build();

        try {
            HttpResponse<String> response = HTTP_CLIENT
                .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            ItunesResponse parsedJson = GSON.<ItunesResponse>fromJson(json, ItunesResponse.class);
            //this.printItunesResponse(parsedJson);
            //System.out.println(json);
            //System.out.println(parsedJson);
            this.resultCount = parsedJson.getResultCount();
            this.results = parsedJson.results;
        } catch (IOException | InterruptedException ioe) {
            System.out.println("Error communicating with the remote server");
        } // catch

    } // sendRequest
    /**
     * Print a response from the iTunes Search API.
     * @param itunesResponse the response object
     */

    private static void printItunesResponse(ItunesResponse itunesResponse) {
        System.out.println();
        System.out.println("********** PRETTY JSON STRING: **********");
        System.out.println(GSON.toJson(itunesResponse));
        System.out.println();
        System.out.println("********** PARSED RESULTS: **********");
        System.out.printf("resultCount = %s\n", itunesResponse.resultCount);
        for (int i = 0; i < itunesResponse.results.length; i++) {
            System.out.printf("itunesResponse.results[%d]:\n", i);
            ItunesResult result = itunesResponse.results[i];
            System.out.printf(" - wrapperType = %s\n", result.wrapperType);
            System.out.printf(" - kind = %s\n", result.kind);
            System.out.printf(" - artworkUrl100 = %s\n", result.artworkUrl100);
        } // for
    } // parseItunesResponse

} // ItunesResponse
