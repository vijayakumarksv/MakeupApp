package com.gadget.android.makeupapp.model;

import java.io.Serializable;
import java.util.List;

public class ModelProducts implements Serializable {

    public int id;
    public String brand;
    public String name;
    public String price;
    public String price_sign;
    public String currency;
    public String image_link;
    public String product_link;
    public String website_link;
    public String description;
    public String rating;
    public String category;
    public String product_type;
    public List<String> tag_list;
    public String created_at;
    public String updated_at;
    public String product_api_url;
    public String api_featured_image;
    public List<ModelProductColor> product_colors;
}