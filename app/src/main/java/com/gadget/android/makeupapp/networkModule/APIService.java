package com.gadget.android.makeupapp.networkModule;

import com.gadget.android.makeupapp.model.ModelProducts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APIService {

    @GET("/api/v1/products.json")
    Call<List<ModelProducts>> getProducts();

    @GET("api/v1/products.json?")
    Call<List<ModelProducts>> searchProducts(@Query("brand") String brand, @Query("product_type") String productType);
}
