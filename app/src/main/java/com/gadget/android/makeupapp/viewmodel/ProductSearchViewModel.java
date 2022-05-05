package com.gadget.android.makeupapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gadget.android.makeupapp.model.ModelProducts;
import com.gadget.android.makeupapp.repository.ProductSearchRepository;

import java.util.List;

public class ProductSearchViewModel extends ViewModel {
    private Context mContext;
    private String brand, productType;
    private MutableLiveData<List<ModelProducts>> productsMutableLiveData;
    private ProductSearchRepository productSearchRepository;

    public void init(Context context, String brand, String productType) {
        if(productsMutableLiveData != null) {
            this.mContext = context;
            this.brand = brand;
            this.productType = productType;
        }
        productSearchRepository  = ProductSearchRepository.getInstance();
        productsMutableLiveData = productSearchRepository.getSearchProducts(brand, productType);
    }

    public LiveData<List<ModelProducts>> getProductSearch() {
        return productsMutableLiveData;
    }

}
