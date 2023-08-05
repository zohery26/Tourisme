package com.itu.tourisme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/destinations")
    Call<List<ListeDestination>> getItems();
}