package com.gadget.android.makeupapp.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget.android.makeupapp.R;
import com.gadget.android.makeupapp.model.ModelProducts;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterProductSearch extends RecyclerView.Adapter<AdapterProductSearch.ProductSearchViewHolder> implements Filterable {
    private Context mContext;
    private List<ModelProducts> modelProductsList;
    private List<ModelProducts> modelProductsListFull;

    @NonNull
    @Override
    public ProductSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
        return new AdapterProductSearch.ProductSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSearchViewHolder holder, int position) {
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
                if (  holder.productDesc.getLayout() != null) {
                    final int lineCount = holder.productDesc.getLayout().getLineCount();
                    if (lineCount >= 2) {
                        holder.txt_more.setVisibility(View.VISIBLE);
                    } else {
                        holder.txt_more.setVisibility(View.GONE);
                    }
                    holder.productDesc.setText(pDesc);
                }
            });
            holder.txt_productPrice.setText("Price : "+pPriceSign+" "+pPrice);
            holder.txt_productBrand.setText(Html.fromHtml("<font><b>" + "Brand : "+ "</b></font>"+pBrand));
            Picasso.get()
                    .load(modelProductsList.get(position).image_link)
                    .placeholder(R.drawable.fashion)
                    .into(holder.productImage);
            holder.txt_more.setOnClickListener(v -> {
                if (modelProductsList.get(position) != null) {
                    alertDialogDescription(modelProductsList.get(position));
                }
            });
        }

    }

    public void productList(Context context, List<ModelProducts> productsList) {
        this.mContext = context;
        this.modelProductsList = productsList;
        modelProductsListFull = new ArrayList<>(productsList);
    }

    private void alertDialogDescription(ModelProducts modelProducts) {
        Dialog dialog = new Dialog(mContext);

        final View customLayout = ((AppCompatActivity)mContext).getLayoutInflater().inflate(R.layout.custom_alert, null);
        dialog.setContentView(customLayout);

        TextView txt_Title = customLayout.findViewById(R.id.txt_Title);
        TextView txt_description = customLayout.findViewById(R.id.txt_description);
        LinearLayout layout_close = customLayout.findViewById(R.id.layout_close);
        RelativeLayout rlv_bg = customLayout.findViewById(R.id.rlv_bg);
        LinearLayout layout_bg = customLayout.findViewById(R.id.layout_bg);

        txt_Title.setText(modelProducts.name);
        if(modelProducts.description != null)
            txt_description.setText(modelProducts.description);
        else
            dialog.dismiss();

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        dialog.show();
        layout_close.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }

    @Override
    public int getItemCount() {
        return modelProductsList != null ? modelProductsList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return productSearchFilter;
    }

    public static class ProductSearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productDesc, productName, txt_more, txt_productPrice, txt_productBrand;

        public ProductSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            productDesc = itemView.findViewById(R.id.txt_pDesc);
            productName = itemView.findViewById(R.id.txt_pName);
            txt_more = itemView.findViewById(R.id.txt_more);
            txt_productPrice = itemView.findViewById(R.id.txt_productPrice);
            txt_productBrand = itemView.findViewById(R.id.txt_productBrand);
            productImage = itemView.findViewById(R.id.image_pImage);
        }
    }

    private Filter productSearchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelProducts> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(modelProductsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelProducts item : modelProductsListFull) {
                    if (item.name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelProductsList.clear();
            modelProductsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
