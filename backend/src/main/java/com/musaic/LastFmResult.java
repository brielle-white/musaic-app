package com.musaic;

public class LastFmResult {
    String name;
    String url;
    String playCount;
    String artists;   

    /** 
    creates a LastRmResult object with the name of a song, the name of the artists,
    the amount of times a song has been played, and the image url.
    */
    public LastFmResult() {
        this.name = name;
        this.url = url;
        this.playCount = playCount;
        this.artists = artists;
    }

    /**
     * Returns the name of the user's song.
     * @return the name of the song.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the image url of the song.
     * @return the image url.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Returns the times a song has been repeated.
     * @return the repeat count.
     */
    public String getPlayCount() {
        return this.playCount;
    }

    /**
     * Returns the artist of a song.
     * @return the artist.
     */
    public String getArtist() {
        return this.artists;
    }
}