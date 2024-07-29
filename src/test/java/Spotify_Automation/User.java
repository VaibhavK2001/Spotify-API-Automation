package Spotify_Automation;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class User {

    String token = "BQBp2iXPIzjxjnhGjXVVy972eZXnDYosXxgLpOxM68hA0FI5_Tx32VU4BjKYdh5ps-Gp7g8XdFPDGFhjikoRpP09BogqyBEc47-CI3cVYxsLG65zYyogrX_Mkt3n2AH-czmGrWPzIo4SbGHGcNHzeVKSP75m0lQCdWdrfDrm1cEt3k7XJo2k7mOB767tdq2t6lKHsOc_4FOz2E4IuWJ6gnKI78DuEzTR0kJgeRDg50-3ojA7EnLVJPPFFojafyu6utqsqURe7x-KJCZii7bqI8OcLtF-ENgaCsRrboQ2d4katGTa_Ff_AGtP722o4ejOHMRBpzCjqm-7cwI";

    @Test
    public void getCurrentUserProfile() {

        Response res =
                given()
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("https://api.spotify.com/v1/me");
        res.prettyPrint();
        res.then().assertThat().statusCode(200);
        String id = res.path("id");
        Assert.assertEquals(id, "31zuj4l6tqinifqep5qlsfwxikji");
    }

    @Test
    public void getUserTopItems() {

        Response res =
                given()
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("https://api.spotify.com/v1/me/top/artists");
        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void getUserProfile() {

        Response res =
                given()
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .pathParam("id", "31zuj4l6tqinifqep5qlsfwxikji")
                        .when()
                        .get("https://api.spotify.com/v1/users/{id}");
        res.prettyPrint();
        res.then().assertThat().statusCode(200);
        String name = res.path("display_name");
        Assert.assertEquals("Vaibhav", name);
    }

    @Test
    public void putfollowPlaylist() {

        Response res =
                given()
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .pathParam("playlist_id", "37i9dQZF1DX0XUfTFmNBRM")
                        .body("{\n" +
                                "    \"public\": false\n" +
                                "}")
                        .when()
                        .put("https://api.spotify.com/v1/playlists/{playlist_id}/followers");
        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void deleteunfollowPlaylist() {

        Response res =
                given()
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .pathParam("playlist_id", "37i9dQZF1DX0XUfTFmNBRM")
                        .body("{\n" +
                                "    \"public\": false\n" +
                                "}")
                        .when()
                        .delete("https://api.spotify.com/v1/playlists/{playlist_id}/followers");
        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void getFollowedArtist() {

        Response res =
                given()
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .pathParam("type", "artist")
                        .when()
                        .get("https://api.spotify.com/v1/me/following?type={type}");
        res.prettyPrint();
        String id = res.path("artists.items[0].id");
        Assert.assertEquals(id, "0oOet2f43PA68X5RxKobEy");
        res.then().statusCode(200);
    }

    @Test
    public void putfollowArtitsorUser() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("type", "artist")
                .pathParam("id", "0oOet2f43PA68X5RxKobEy")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"0oOet2f43PA68X5RxKobEy\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/following?type={type}&ids={id}");
        res.prettyPrint();
        res.then().statusCode(204);

    }

    @Test
    public void deleteArtist() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("type", "artist")
                .pathParam("id", "0oOet2f43PA68X5RxKobEy")
                .when()
                .delete("https://api.spotify.com/v1/me/following?type={type}&ids={id}");
        res.prettyPrint();
        res.then().statusCode(204);
    }

    @Test
    public void getUserFollowsArtists() {
        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("type", "artist")
                .pathParam("ids", "0oOet2f43PA68X5RxKobEy")
                .when()
                .get("https://api.spotify.com/v1/me/following/contains?type={type}&ids={ids}");
        response.prettyPrint();
        response.then().statusCode(200);

    }

    @Test
    public void currentUserFollowsPlaylists() {
        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("playlist_id", "37i9dQZF1EIWQ7iXYvVC5w")
                .when()
                .get("https://api.spotify.com/v1/playlists/{playlist_id}/followers/contains");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    // -------------------------------- Album Requests --------------------------------

    @Test
    public void getAlbum() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "2noRn2Aes5aoNVsU6iWThc")
                .when()
                .get("https://api.spotify.com/v1/albums/{id}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getSeveralAlbums() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("ids", "2noRn2Aes5aoNVsU6iWThc,382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo")
                .when()
                .get("https://api.spotify.com/v1/albums?ids={ids}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getAlbumTracks() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "13t8iDyl1vkhPcO3Zl29a9")
                .when()
                .get("https://api.spotify.com/v1/albums/{id}/tracks");
        response.prettyPrint();
        response.then().statusCode(200);

    }

    @Test
    public void getUserSavedAlbums() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/albums");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void saveAlbumforCurrentUser() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("ids", "382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/albums?ids={ids}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void removeUserSavedAlbum() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "382ObEPsp2rxGrnsizN5TX")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"382ObEPsp2rxGrnsizN5TX\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/albums?ids={id}");
        response.prettyPrint();
        response.then().statusCode(200);

    }

    @Test
    public void checkUsersSavedAlbum() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("ids", "7khRLZe8P6XcUoRgOa3nnf,13t8iDyl1vkhPcO3Zl29a9,382ObEPsp2rxGrnsizN5TX")
                .when()
                .get("https://api.spotify.com/v1/me/albums/contains?ids={ids}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getNewRelease() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/browse/new-releases");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    // -------------------------------- Artists Requests --------------------------------

    @Test
    public void getArtists() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "7HCqGPJcQTyGJ2yqntbuyr")
                .when()
                .get("https://api.spotify.com/v1/artists/{id}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getSeveralArtists() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6")
                .when()
                .get("https://api.spotify.com/v1/artists?ids={id}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getArtistsAlbum() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "7HCqGPJcQTyGJ2yqntbuyr")
                .when()
                .get("https://api.spotify.com/v1/artists/{id}/albums");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getArtistsTopTrack() {
        Response response =
                given()
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .pathParam("id", "7HCqGPJcQTyGJ2yqntbuyr")
                        .when()
                        .get("https://api.spotify.com/v1/artists/{id}/top-tracks");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getArtistsReleatedArtists() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "7HCqGPJcQTyGJ2yqntbuyr")
                .when()
                .get("https://api.spotify.com/v1/artists/{id}/related-artists");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    // -------------------------------- AudioBook Requests --------------------------------


    @Test
    public void getAudioBook() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "7iHfbu1YPACw6oZPAFJtqe")
                .when()
                .get("https://api.spotify.com/v1/audiobooks/{id}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getSeveralAudiobooks() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/audiobooks");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getAudioBooksChapter() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "7iHfbu1YPACw6oZPAFJtqe")
                .when()
                .get("https://api.spotify.com/v1/audiobooks/{id}/chapters");
        response.prettyPrint();
        response.then().statusCode(200);

    }

    @Test
    public void getUserSavedAudiobook() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void saveAudioBookforCurrentuser() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("ids", "18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe")
                .when()
                .put("https://api.spotify.com/v1/me/audiobooks?ids={ids}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void removeUsersAudioBook() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("ids", "18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe")
                .when()
                .delete("https://api.spotify.com/v1/me/audiobooks?ids={ids}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void checkUsersSavedAudioBook() {
        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("ids", "18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe")
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks?ids={ids}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    // -------------------------------- Categoris Requests --------------------------------

    @Test
    public void getSeveralBrowseCategoris() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories");
        response.prettyPrint();
        String id = response.path("categories.items[0].id");
        Assert.assertEquals(id, "0JQ5DAt0tbjZptfcdMSKl3");
        response.then().statusCode(200);
    }

    @Test
    public void getSingleBrowseCategories() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories/dinner");
        response.prettyPrint();
        String id = response.path("id");
        response.then().statusCode(200);
        Assert.assertEquals(id, "0JQ5DAqbMKFRY5ok2pxXJ0");
    }

    // -------------------------------- Chapter Requests --------------------------------

    @Test
    public void getChapter() {
        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "0D5wENdkdwbqlrHoaJ9g29")
                .when()
                .get("https://api.spotify.com/v1/chapters/{id}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getSeveralChapter() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("ids", "0IsXVP0JmcB2adSE338GkK,3ZXb8FKZGU0EHALYX6uCzU,0D5wENdkdwbqlrHoaJ9g29")
                .when()
                .get("https://api.spotify.com/v1/chapters?ids={ids}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getEpisode() {

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", "4nU7HmEIfBLHRiX5tNozyi")
                .when()
                .get("https://api.spotify.com/v1/episodes/{id}");
        response.prettyPrint();
        response.then().statusCode(200);
        String id = response.path("id");
        Assert.assertEquals(id, "4nU7HmEIfBLHRiX5tNozyi");
    }

    @Test
    public void getSeveralEpisodes(){
        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("ids", "77o6BIVlYM3msb4MMIL1jH,0Q86acNRm6V9GYx55SXKwf")
                .when()
                .get("https://api.spotify.com/v1/episodes?ids={ids}");
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void getUserSavedEpisodes(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/episodes");
        response.prettyPrint();
        response.then().statusCode(200);

    }

    @Test
    public void saveEpisodesforCurrentUser(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("ids","77o6BIVlYM3msb4MMIL1jH,0Q86acNRm6V9GYx55SXKwf")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"77o6BIVlYM3msb4MMIL1jH,0Q86acNRm6V9GYx55SXKwf\"\n" +
                        "    ]\n" +
                        "}")
                .put("https://api.spotify.com/v1/me/episodes?ids={ids}");
        response.prettyPrint();
        response.then().statusCode(200);

    }

    @Test
    public void removeUserSavedEpisodes(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("ids","7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/episodes?ids={ids}");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getAvailableGenreSeeds(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/recommendations/available-genre-seeds");
        response.prettyPrint();
        response.then().statusCode(200);
    }


    @Test
    public void getAvailableMarkets(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/recommendations/available-genre-seeds");
        response.prettyPrint();
        response.then().statusCode(200);
    }


    @Test
    public void getPlaybackState(){
         Response response = given()
                 .header("accept", "application/json")
                 .header("Content-Type", "application/json")
                 .header("Authorization", "Bearer " + token)
                 .when()
                 .get("https://api.spotify.com/v1/me/player");
         response.prettyPrint();
         response.then().statusCode(204);
    }

    @Test
    public void transferPlayer(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "    \"device_ids\": [\n" +
                        "        \"74ASZWbe4lXaubB36ztrGX\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/player");
        response.prettyPrint();
        response.then().statusCode(403);
    }

    @Test
    public void getAvailableDevices(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/devices");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getCurrentlyPlayingTrack(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/currently-playing");
        response.prettyPrint();
        response.then().statusCode(204);

    }

    @Test
    public void StartResumePlayback(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .body("  {\n" +
                        "      \"context_uri\": \"spotify:album:5ht7ItJgpBH7W6vJ5BqpPr\",\n" +
                        "      \"offset\": {\n" +
                        "          \"position\": 5\n" +
                        "      },\n" +
                        "      \"position_ms\": 0\n" +
                        "  }")
                .when()
                .put("https://api.spotify.com/v1/me/player/play?device_id={device_id}");
        response.prettyPrint();
        response.then().statusCode(403);

    }

    @Test
    public void PausePlayback(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/pause?device_id={device_id}");
        response.prettyPrint();
        response.then().statusCode(403);

    }

    @Test
    public void skipToNext(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .post("https://api.spotify.com/v1/me/player/next?device_id={device_id}");
        response.prettyPrint();
        response.then().statusCode(403);
    }

    @Test
    public void skipToPrevious(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/previous?device_id={device_id}");
        response.prettyPrint();
        response.then().statusCode(403);
    }

    @Test
    public void seekToPosition(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/seek?position_ms=25000&device_id={device_id}");
        response.prettyPrint();
        response.then().statusCode(403);
    }

    @Test
    public void setRepeatMode() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("device_id", "ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/repeat?state=context&device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    @Test
    public void setPlayBakckVolume(){

        Response response = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("device_id", "ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/volume?volume_percent=50&device_id={device_id}");
        response.prettyPrint();
        response.then().statusCode(403);
    }

    @Test
    public void togglePlaybackShuffle() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .put("https://api.spotify.com/v1/me/player/shuffle?state=true&device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    @Test
    public void getRecentlyPlayedTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/recently-played");
        res.prettyPrint();
        res.then().statusCode(200);
    }

    @Test
    public void getTheUserQueue(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/player/queue");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.message");
        Assert.assertEquals(msg, "Forbidden.");
    }

    @Test
    public void addItemToPlaybackQueue() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("device_id","ad1a8d083ae172b11f60f404704d77f69af6675b")
                .when()
                .post("https://api.spotify.com/v1/me/player/queue?uri=spotify%3Atrack%3A4iV5W9uYEdYUVa79Axb7Rh&device_id={device_id}");
        res.prettyPrint();
        res.then().statusCode(403);
        String msg = res.path("error.reason");
        Assert.assertEquals(msg, "PREMIUM_REQUIRED");
    }

    // ---------------------------------- Playlists Requests ----------------------------------

    @Test
    public void getPlaylists(){

        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("playlist_id", "37i9dQZF1EIWQ7iXYvVC5w")
                .when()
                .get("https://api.spotify.com/v1/playlists/{playlist_id}");
            response.prettyPrint();
            response.then().statusCode(200);
            String id = response.path("id");
            Assert.assertEquals(id,"37i9dQZF1EIWQ7iXYvVC5w");
    }

    @Test
    public void changePlaylistDetails(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParams("playlist_id","70RrDRbVXRIfuPwtbqmJJs")
                .body("{\n" +
                        "    \n" +
                        "    \"description\": \"Updated playlist description\",\n" +
                        "    \"public\": true\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/{playlist_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void getPlaylistItems(){

        Response response = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParam("id","37i9dQZF1EIWQ7iXYvVC5w")
                .when()
                .get("https://api.spotify.com/v1/playlists/{id}/tracks");
        response.prettyPrint();
        response.then().statusCode(200);

    }

    @Test
    public void updatePlaylistItems(){

        Response response = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParam("id","37i9dQZF1EIWQ7iXYvVC5w")
                .body("{\n" +
                        "    \"range_start\": 1,\n" +
                        "    \"insert_before\": 3,\n" +
                        "    \"range_length\": 2\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/{id}/tracks");
        response.then().statusCode(200);
        response.prettyPrint();

    }

    @Test
    public void updatePlaylistItems1(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParams("playlist_id","45Lryho7oYdwwlWcQryTA8")
                .body("{\n" +
                        "    \"range_start\": 1,\n" +
                        "    \"insert_before\": 3,\n" +
                        "    \"range_length\": 2\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/{playlist_id}/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void addItemtoPlaylist(){

        Response response = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParam("id","4gXlXbp7BHKqyUHy2dNJIU")
                .body("{\n" +
                        "    \"uris\": [\n" +
                        "        \"spotify:track:38h4tmH1AWBVuemWAy4R4y\"\n" +
                        "    ],\n" +
                        "    \"position\": 0\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/{id}/tracks");
        response.prettyPrint();
        response.then().statusCode(200);
    }


    @Test
    public void removeItemsfromPlaylist(){

        Response response =given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParam("id","4gXlXbp7BHKqyUHy2dNJIU")
                .body("{\n" +
                        "    \"tracks\": [\n" +
                        "        {\n" +
                        "            \"uri\": \"spotify:track:38h4tmH1AWBVuemWAy4R4y\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"snapshot_id\": \"AAAACuRDqYZs4zed3+wWJpQXQjQdh0iA\"\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/playlists/{id}/tracks");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getCurrentUserPlaylist(){
        Response response = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");
            response.prettyPrint();
            response.then().statusCode(200);

    }
    
    @Test
    public void getUsersPlaylists(){

        Response response = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParam("id","31zuj4l6tqinifqep5qlsfwxikji")
                .when()
                .get("https://api.spotify.com/v1/users/{id}/playlists");
        response.prettyPrint();
        response.then().statusCode(200);
        String id = response.path("items[0].id");
        Assert.assertEquals(id,"4gXlXbp7BHKqyUHy2dNJIU");
    }

    @Test
    public void createPlaylist(){

        Response response = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .pathParam("id","31zuj4l6tqinifqep5qlsfwxikji")
                .body("{\n" +
                        "    \"name\": \"New Playlist\",\n" +
                        "    \"description\": \"New playlist description\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/users/{id}/playlists");
        response.prettyPrint();
        response.then().statusCode(201);
    }

    @Test
    public void getFeaturedPlaylist(){
        Response response = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer " +token)
                .when()
                .get("https://api.spotify.com/v1/browse/featured-playlists ");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void getCategoryPlaylists() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories/gaming/playlists");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test
    public void getPlaylistCoverImage() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("playlist_id", "70RrDRbVXRIfuPwtbqmJJs")
                .when()
                .get("https://api.spotify.com/v1/playlists/{playlist_id}/images");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
        String value = res.path("[0].url");
        Assert.assertEquals(value, "https://i.scdn.co/image/ab67616d0000b273af9e8dfd99c2150e692cd96e");
    }

    @Test
    public void addCustomPlaylistCoverImage() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("playlist_id", "3bwRZhow8p8qwJwkOAFuqV")
                .body("/9j/2wCEABoZGSccJz4lJT5CLy8vQkc9Ozs9R0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0cBHCcnMyYzPSYmPUc9Mj1HR0dEREdHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR//dAAQAAf/uAA5BZG9iZQBkwAAAAAH/wAARCAABAAEDACIAAREBAhEB/8QASwABAQAAAAAAAAAAAAAAAAAAAAYBAQAAAAAAAAAAAAAAAAAAAAAQAQAAAAAAAAAAAAAAAAAAAAARAQAAAAAAAAAAAAAAAAAAAAD/2gAMAwAAARECEQA/AJgAH//Z")
                .when()
                .put("https://api.spotify.com/v1/playlists/{playlist_id}/images");
        res.prettyPrint();
        res.then().statusCode(202);
        Assert.assertEquals(res.statusCode(), 202);
    }

        @Test
        public void searchForItem() {
            Response res = given()
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .get("https://api.spotify.com/v1/search?q=remaster%2520track%3ADoxy%2520artist%3AMiles%2520Davis&type=album&limit=2");
            res.prettyPrint();
            res.then().statusCode(200);
            Assert.assertEquals(res.statusCode(), 200);
        }

    @Test
    public void getShow(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .when()
                .get("https://api.spotify.com/v1/shows/{show_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("episodes.items[0].id");
        Assert.assertEquals(value,"1Q0ge9mpbUTbpV2631Cv6A");
    }

    @Test
    public void getSeveralShows(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .pathParams("show_id2","2C+2jXbuRAD5DBeMb0uQpWtOH")
                .when()
                .get("https://api.spotify.com/v1/shows?ids={show_id}&{show_id2}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("shows[0].id");
        Assert.assertEquals(value,"6h7u9VphsbDw1m0F2eQBIy");
    }

    @Test
    public void getShowEpisodes(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .when()
                .get("https://api.spotify.com/v1/shows/{show_id}/episodes");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("items[0].id");
        Assert.assertEquals(value,"1Q0ge9mpbUTbpV2631Cv6A");
    }

    @Test
    public void getUserSavedShows(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/shows");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void saveShowsForCurrentUser(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .when()
                .put("https://api.spotify.com/v1/me/shows?ids={show_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void removeUserSavedShows(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .when()
                .delete("https://api.spotify.com/v1/me/shows?ids={show_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void checkUserSavedShows(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("show_id","6h7u9VphsbDw1m0F2eQBIy")
                .when()
                .get("https://api.spotify.com/v1/me/shows/contains?ids={show_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        boolean value = res.path("[0]");
        Assert.assertTrue(true);
    }


    @Test
    public void getTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .when()
                .get("https://api.spotify.com/v1/tracks/{track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("album.artists[0].name");
        Assert.assertEquals(value,"Darshan Raval");
    }


    @Test
    public void getSeveralTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("ids", "7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B")
                .when()
                .get("https://api.spotify.com/v1/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("tracks[0].album.artists[0].id");
        Assert.assertEquals(value,"12Chz98pHFMPJEknJQMWvI");
    }

    @Test
    public void getUserSavedTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/tracks");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("items[0].track.album.artists[0].name");
        Assert.assertEquals(value,"Darshan Raval");
    }

    @Test
    public void saveTracksForCurrentUser(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"2fsBaSK1551VvUD9Uvbb9Q\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/tracks?ids={track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }


    @Test
    public void removeUserSavedTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"2fsBaSK1551VvUD9Uvbb9Q\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/tracks?ids={track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
    }

    @Test
    public void checkUserSavedTracks(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .when()
                .get("https://api.spotify.com/v1/me/tracks/contains?ids={track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        boolean value = res.path("[0]");
        Assert.assertTrue(true);
    }

    @Test
    public void getSeveralTracksAudioFeatures(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("ids", "2fsBaSK1551VvUD9Uvbb9Q,2C34Fh4HXZmnuBdtgejWUZg2")
                .when()
                .get("https://api.spotify.com/v1/audio-features");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("audio_features[0].id");
        Assert.assertEquals(value,"2fsBaSK1551VvUD9Uvbb9Q");
    }

    @Test
    public void getTrackAudioFeatures(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .when()
                .get("https://api.spotify.com/v1/audio-features/{track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("uri");
        Assert.assertEquals(value,"spotify:track:2fsBaSK1551VvUD9Uvbb9Q");
    }

    @Test
    public void getTrackAudioAnalysis(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id","2fsBaSK1551VvUD9Uvbb9Q")
                .when()
                .get("https://api.spotify.com/v1/audio-analysis/{track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("meta.platform");
        Assert.assertEquals(value,"Linux");
    }

    @Test
    public void getRecommendations(){
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("artist_id","4YRxDV8wJFPHPTeXepOstw")
                .when()
                .get("https://api.spotify.com/v1/recommendations?seed_artists={artist_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(),200);
        String value = res.path("tracks[0].album.album_type");
        Assert.assertEquals(value,"ALBUM");
    }

    @Test
    public void getTrackAudioAnalysis1() {
        Response res = given()
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParams("track_id", "2fsBaSK1551VvUD9Uvbb9Q")
                .when()
                .get("https://api.spotify.com/v1/audio-analysis/{track_id}");
        res.prettyPrint();
        res.then().statusCode(200);
        Assert.assertEquals(res.statusCode(), 200);
        String value = res.path("meta.platform");
        Assert.assertEquals(value, "Linux");
    }


}


