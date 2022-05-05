package com.gadget.android.makeupapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.gadget.android.makeupapp.model.ModelProducts;
import com.gadget.android.makeupapp.networkModule.APIService;
import com.gadget.android.makeupapp.networkModule.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private static ProductRepository productRepository;

    private APIService apiService;

    public ProductRepository() {
        apiService = ApiClient.getService();
    }

    public static ProductRepository getInstance() {
        if (productRepository == null) {
            productRepository = new ProductRepository();
        }
        return productRepository;
    }

    public MutableLiveData<List<ModelProducts>> getProducts() {
        MutableLiveData<List<ModelProducts>> productsMutableLiveData = new MutableLiveData<>();
         apiService.getProducts().enqueue(new Callback<List<ModelProducts>>() {
             @Override
             public void onResponse(Call<List<ModelProducts>> call, Response<List<ModelProducts>> response) {
                 if(response.isSuccessful()) {
                     productsMutableLiveData.setValue(response.body());
                 } else {
                     productsMutableLiveData.setValue(null);
                 }
             }

             @Override
             public void onFailure(Call<List<ModelProducts>> call, Throwable t) {
                 productsMutableLiveData.setValue(null);
             }
         });
        return productsMutableLiveData;
    }
}
