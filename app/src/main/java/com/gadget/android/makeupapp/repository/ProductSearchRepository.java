package com.gadget.android.makeupapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.gadget.android.makeupapp.model.ModelProducts;
import com.gadget.android.makeupapp.networkModule.APIService;
import com.gadget.android.makeupapp.networkModule.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSearchRepository {
    private static ProductSearchRepository productSearchRepository;

    private APIService apiService;

    public ProductSearchRepository() {
        apiService = ApiClient.getService();
    }

    public static ProductSearchRepository getInstance() {
        if (productSearchRepository == null) {
            productSearchRepository = new ProductSearchRepository();
        }
        return productSearchRepository;
    }

    public MutableLiveData<List<ModelProducts>> getSearchProducts(String brand, String productType) {
        MutableLiveData<List<ModelProducts>> productsearchMutableLiveData = new MutableLiveData<>();
        apiService.searchProducts(brand, productType).enqueue(new Callback<List<ModelProducts>>() {
            @Override
            public void onResponse(Call<List<ModelProducts>> call, Response<List<ModelProducts>> response) {
                if(response.isSuccessful()) {
                    productsearchMutableLiveData.setValue(response.body());
                } else {
                    productsearchMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<ModelProducts>> call, Throwable t) {
                productsearchMutableLiveData.setValue(null);
            }
        });

        return productsearchMutableLiveData;
    }

}
