package com.gadget.android.makeupapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.gadget.android.makeupapp.view.adapter.AdapterProduct;
import com.gadget.android.makeupapp.viewmodel.ProductViewModel;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressbar;
    private RecyclerView rvProductView;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        progressbar = findViewById(R.id.progressbar);
        rvProductView = findViewById(R.id.rvProducts);
        rvProductView.setHasFixedSize(true);
        rvProductView.setLayoutManager(new LinearLayoutManager(this));

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.init(this);
        productViewModel.getProducts().observe(this, modelProducts -> {
            progressbar.setVisibility(View.VISIBLE);
            if(modelProducts != null && modelProducts.size() > 0 ) {
                progressbar.setVisibility(View.GONE);
                AdapterProduct adapterProduct = new AdapterProduct();
                adapterProduct.productList(this, modelProducts);
                rvProductView.setAdapter(adapterProduct);
            }
        });
    }
}