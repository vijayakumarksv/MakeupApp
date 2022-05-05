package com.gadget.android.makeupapp.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gadget.android.makeupapp.R;
import com.gadget.android.makeupapp.SearchActivity;
import com.gadget.android.makeupapp.Utils.AppConstants;
import com.gadget.android.makeupapp.model.ModelProducts;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {
    private Context mContext;
    private List<ModelProducts> modelProductsList;

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (modelProductsList != null) {
            String pName = modelProductsList.get(position).name != null ? modelProductsList.get(position).name : "";
            String pDesc = modelProductsList.get(position).description != null ? modelProductsList.get(position).description : "";
            String pPrice = modelProductsList.get(position).price != null ? modelProductsList.get(position).price : "";
            String pPriceSign = modelProductsList.get(position).price_sign != null ? modelProductsList.get(position).price_sign : "";
            String pBrand = modelProductsList.get(position).brand != null ? modelProductsList.get(position).brand : "";
            holder.productName.setText(pName);
            holder.productDesc.setMaxLines(2);
            holder.productDesc.setMinLines(1);
            holder.productDesc.setText(pDesc);

            holder.productDesc.post(() -> {
                if (holder.productDesc.getLayout() != null) {
                    final int lineCount = holder.productDesc.getLayout().getLineCount();
                    if (lineCount >= 2) {
                        holder.txt_more.setVisibility(View.VISIBLE);
                    } else {
                        holder.txt_more.setVisibility(View.GONE);
                    }
                    holder.productDesc.setText(pDesc);
                }
            });
            holder.txt_productPrice.setText("Price : " + pPriceSign + " " + pPrice);
            holder.txt_productBrand.setText(Html.fromHtml("<font><b>" + "Brand : " + "</b></font>" + pBrand));
            Picasso.get()
                    .load(modelProductsList.get(position).image_link)
                    .placeholder(R.drawable.fashion)
                    .into(holder.productImage);
            holder.txt_more.setOnClickListener(v -> {
                if (modelProductsList.get(position) != null) {
                    alertDialogDescription(modelProductsList.get(position));
                }
            });

            holder.itemView.setOnClickListener(v -> {
                if (modelProductsList.get(position).brand != null && modelProductsList.get(position).product_type != null) {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.putExtra(AppConstants.BRAND, modelProductsList.get(position).brand);
                    intent.putExtra(AppConstants.PRODUCT_TYPE, modelProductsList.get(position).product_type);
                    Log.e("producttyoe", modelProductsList.get(position).product_type);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private void alertDialogDescription(ModelProducts modelProducts) {
        Dialog dialog = new Dialog(mContext);

        final View customLayout = ((AppCompatActivity) mContext).getLayoutInflater().inflate(R.layout.custom_alert, null);
        dialog.setContentView(customLayout);

        TextView txt_Title = customLayout.findViewById(R.id.txt_Title);
        //TextView txt_description = customLayout.findViewById(R.id.txt_description);
        WebView descriptionWebview = customLayout.findViewById(R.id.descriptionWebview);
        LinearLayout layout_close = customLayout.findViewById(R.id.layout_close);
        RelativeLayout rlv_bg = customLayout.findViewById(R.id.rlv_bg);
        LinearLayout layout_bg = customLayout.findViewById(R.id.layout_bg);

        txt_Title.setText(modelProducts.name);
        if (modelProducts.description != null) {
            WebSettings webSettings = descriptionWebview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            descriptionWebview.loadData(modelProducts.description, "text/html", "UTF-8");
        } else {
            dialog.dismiss();
        }

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        dialog.show();
        layout_close.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }

    public void productList(Context context, List<ModelProducts> productsList) {
        this.mContext = context;
        this.modelProductsList = productsList;
    }

    @Override
    public int getItemCount() {
        return modelProductsList != null ? modelProductsList.size() : 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productDesc, productName, txt_more, txt_productPrice, txt_productBrand;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productDesc = itemView.findViewById(R.id.txt_pDesc);
            productName = itemView.findViewById(R.id.txt_pName);
            txt_more = itemView.findViewById(R.id.txt_more);
            txt_productPrice = itemView.findViewById(R.id.txt_productPrice);
            txt_productBrand = itemView.findViewById(R.id.txt_productBrand);
            productImage = itemView.findViewById(R.id.image_pImage);
        }
    }
}
