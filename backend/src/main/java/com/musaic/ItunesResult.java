package com.musaic;


/**
 * Represents a result in a response from the iTunes Search API. This is
 * used by Gson to create an object from the JSON response body. This class
 * is provided with project's starter code, and the instance variables are
 * intentionally set to package private visibility.
 * @see <a href="https://developer.apple.com/library/archive/documentation/AudioVideo*
 * /Conceptual/iTuneSearchAPI/UnderstandingSearchResults.html#//apple_ref/doc/uid/*
 * TP40017632-CH8-SW1"> Understanding Search Results. </a>
 */
public class ItunesResult {
    String wrapperType;
    String kind;
    String artworkUrl100;
    String artistName;
    String trackName;


    // the rest of the result is intentionally omitted since we don't use it

    /**
     * Represents a single result from the iTunes Search API.
     * Contains details like the artist name, track name, and artwork URL.
     */
    public ItunesResult() {
        //can change this this was the preset of what was pulled from the json in 1302
        // this.wrapperType = wrapperType;
        // this.kind = kind;
        // this.artworkUrl100 = artworkUrl100;
        // this.artistName = artistName;
        // this.trackName = trackName;
    } // constructor

    /** The URL to the artwork image for the track.
     * @return a string with the link.
     */
    public String getImageUrl () {
        return artworkUrl100;
    } // getImageUrl

    /** The name of the artist.
     * @return a String.
     */
    public String getArtist() {
        return artistName;
    } //getArtist

    /**
     * Returns the artist's name.
     *
     * @return the artist name as a {@code String}
     */
    public String getTrackName() {
        return trackName;
    } // getTrackName

} // ItunesResult
