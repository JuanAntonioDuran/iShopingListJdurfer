package com.example.ishopinglist.activities.Adapters;


import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.ishopinglist.R;
import com.example.ishopinglist.activities.Models.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.activity_product_adapter, parent, false);
        }

        // Obtener el producto actual
        Product product = productList.get(position);

        // Vincular los datos del producto a las vistas del layout
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productQuantity = convertView.findViewById(R.id.productQuantity);
        TextView productStatus = convertView.findViewById(R.id.productStatus);
        TextView productLactosa = convertView.findViewById(R.id.productLactosa);
        TextView productGlucosa = convertView.findViewById(R.id.productGlucosa);

        productName.setText(product.getName());
        productQuantity.setText("Cantidad: " + product.getQuantity());
        productStatus.setText(product.isPendiente() ? "Comprado" : "No comprado");
        productLactosa.setText(product.isLactosa() ? "Tiene Lactosa" : "No Tiene Lactosa");
        productGlucosa.setText(product.isGluten() ? "Tiene Glucosa" : "No Tiene Glucosa");

        if (product.isLactosa() && !product.isGluten()){
            convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.Lactosa));
        } else if (product.isGluten() && !product.isLactosa()) {
            convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.Gluten));
        } else if (product.isGluten() && product.isLactosa()) {
            convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.Ambos));
        }


        return convertView;
    }
}