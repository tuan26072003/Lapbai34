package com.example.lap.Buoi5;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumService {
    @GET("phots")
    Call<List<Album>> getAlbums();
}
