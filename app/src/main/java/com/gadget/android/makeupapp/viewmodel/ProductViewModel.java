package com.gadget.android.makeupapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gadget.android.makeupapp.model.ModelProducts;
import com.gadget.android.makeupapp.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private Context mContext;
    private MutableLiveData<List<ModelProducts>> productsMutableLiveData;
    private ProductRepository productRepository;

    public void init(Context context) {
        if(productsMutableLiveData != null) {
            this.mContext = context;
        }
        productRepository  = ProductRepository.getInstance();
        productsMutableLiveData = productRepository.getProducts();
    }

    public LiveData<List<ModelProducts>> getProducts() {
        return productsMutableLiveData;
    }

}
