package com.gadget.android.makeupapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget.android.makeupapp.Utils.AppConstants;
import com.gadget.android.makeupapp.model.ModelProducts;
import com.gadget.android.makeupapp.view.adapter.AdapterProductSearch;
import com.gadget.android.makeupapp.viewmodel.ProductSearchViewModel;
import com.gadget.android.makeupapp.viewmodel.ProductViewModel;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ProgressBar progressbar;
    private RecyclerView rvProductView;
    private ProductSearchViewModel productViewModel;
    private AdapterProductSearch adapterProduct;
    private List<ModelProducts> mModelProducts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        progressbar = findViewById(R.id.progressbar);
        rvProductView = findViewById(R.id.rvProducts);
        rvProductView.setHasFixedSize(true);
        rvProductView.setLayoutManager(new LinearLayoutManager(this));

        String brand = getIntent().getStringExtra(AppConstants.BRAND);
        String productType = getIntent().getStringExtra(AppConstants.PRODUCT_TYPE);
        productViewModel = ViewModelProviders.of(SearchActivity.this).get(ProductSearchViewModel.class);
        productViewModel.init(SearchActivity.this, brand, productType);
        productViewModel.getProductSearch().observe(SearchActivity.this, productsList -> {
            progressbar.setVisibility(View.VISIBLE);
            if (productsList != null && productsList.size() > 0) {
                mModelProducts = productsList;
                progressbar.setVisibility(View.GONE);
                adapterProduct = new AdapterProductSearch();
                adapterProduct.productList(SearchActivity.this, productsList);
                rvProductView.setAdapter(adapterProduct);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.ic_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(searchView != null) {
                searchView.clearFocus();}
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(mModelProducts != null && mModelProducts.size() > 0) {
                    adapterProduct.getFilter().filter(newText);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}
