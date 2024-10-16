package com.example.ishopinglist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ishopinglist.R;
import com.example.ishopinglist.activities.Models.Product;
import com.example.ishopinglist.activities.Editor.EditProductActivity;

public class ProductDetailActivity extends AppCompatActivity {

    private Product selectedProduct;  // Variable para almacenar el producto seleccionado
    private static final int EDIT_PRODUCT_REQUEST_CODE = 1;  // Código de solicitud para editar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);  // Establecer el layout de la actividad

        // Referenciar elementos del layout
        TextView productName = findViewById(R.id.productName);
        TextView productQuantity = findViewById(R.id.productQuantity);
        TextView productStatus = findViewById(R.id.productStatus);
        TextView productLactosa = findViewById(R.id.productLactosa);
        TextView productGlucosa = findViewById(R.id.productGlucosa);
        Button editProductButton = findViewById(R.id.editProductButton);
        Button backButton = findViewById(R.id.backButton);


        // Recibir el producto seleccionado desde el Intent
        selectedProduct = (Product) getIntent().getSerializableExtra("product");

        // Mostrar los detalles del producto
        if (selectedProduct != null) {
            productName.setText(selectedProduct.getName());  // Mostrar el nombre del producto
            productQuantity.setText("Cantidad: " + selectedProduct.getQuantity());  // Mostrar la cantidad
            productStatus.setText(selectedProduct.isPendiente() ? "Comprado" : "No Comprado");  // Mostrar el estado de compra
            productLactosa.setText(selectedProduct.isLactosa() ? "Tiene Lactosa" : "No Tiene Lactosa");
            productGlucosa.setText(selectedProduct.isGluten() ? "Tiene Glucosa " : "No Tiene Glucosa");
        }

        // Configurar el botón para editar el producto
        editProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para abrir la actividad de edición
                Intent intent = new Intent(ProductDetailActivity.this, EditProductActivity.class);
                intent.putExtra("product", selectedProduct);  // Pasar el producto seleccionado a la actividad de edición
                startActivityForResult(intent, EDIT_PRODUCT_REQUEST_CODE);  // Iniciar la actividad para obtener un resultado
            }
        });

        // Configurar el botón de regresar
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Cerrar esta actividad y regresar a la anterior
            }
        });
    }

    // Manejar el resultado de la actividad de edición
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PRODUCT_REQUEST_CODE && resultCode == RESULT_OK) {
            // Recibir el producto actualizado
            Product updatedProduct = (Product) data.getSerializableExtra("updatedProduct");

            if (updatedProduct != null) {
                // Devolver el producto actualizado a MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedProduct", updatedProduct);  // Pasar el producto actualizado
                setResult(RESULT_OK, resultIntent);  // Establecer el resultado como OK
                finish();  // Cerrar esta actividad y regresar a MainActivity
            }
        }
    }
}






