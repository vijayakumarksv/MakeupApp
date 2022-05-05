package com.gadget.android.makeupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.ic_search:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}