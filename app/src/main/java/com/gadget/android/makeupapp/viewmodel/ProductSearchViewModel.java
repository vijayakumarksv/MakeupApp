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
    private MutableLiveData<List<ModelProducts>> productsMutableLiveData;
    private ProductSearchRepository productSearchRepository;

    public void init(Context context) {
        if(productsMutableLiveData != null) {
            this.mContext = context;
        }
        productSearchRepository  = ProductSearchRepository.getInstance();
        productsMutableLiveData = productSearchRepository.getSearchProducts();
    }

    public LiveData<List<ModelProducts>> getProductSearch() {
        return productsMutableLiveData;
    }

}
