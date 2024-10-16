package com.example.ishopinglist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ishopinglist.R;
import com.example.ishopinglist.activities.DataBase.ProductList;
import com.example.ishopinglist.activities.Models.Product;

import java.util.ArrayList;
import java.util.List;


import com.example.ishopinglist.activities.Models.Product;

public class AddPendingProductActivity extends AppCompatActivity {

    private Spinner pendingProductSpinner;
    private Button savePendingButton;
    private Button cancelPendingButton;
    private ArrayList<Product> nonPendingProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cargamos el layout
        setContentView(R.layout.activity_add_pending_product);

        // Comprobamos si el ID "main" está presente en el layout antes de aplicar WindowInsets
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Referenciar elementos del layout
        pendingProductSpinner = findViewById(R.id.pendingProductSpinner);
        savePendingButton = findViewById(R.id.savePendingButton);
        cancelPendingButton = findViewById(R.id.cancelPendingButton);

        // Verificación adicional de que las vistas no son nulas
        if (pendingProductSpinner == null || savePendingButton == null || cancelPendingButton == null) {
            Log.e("AddPendingProductActivity", "Error: Spinner o botones no encontrados en el layout.");
            Toast.makeText(this, "Error en la interfaz de usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        // Manejo de posibles NullPointerExceptions al obtener la lista de productos
        nonPendingProducts = new ArrayList<>();
        List<Product> allProducts = ProductList.getProducts();
        if (allProducts != null && !allProducts.isEmpty()) {
            for (Product product : allProducts) {
                if (!product.isPendiente()) {
                    nonPendingProducts.add(product);
                }
            }
        } else {
            Log.w("AddPendingProductActivity", "La lista de productos está vacía o es nula.");
            Toast.makeText(this, "No hay productos disponibles para agregar como pendientes.", Toast.LENGTH_SHORT).show();
            finish();  // Cerramos la actividad si no hay productos para mostrar
            return;
        }

        // Configurar el Spinner con los productos no pendientes
        try {
            ArrayAdapter<Product> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nonPendingProducts);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            pendingProductSpinner.setAdapter(spinnerAdapter);
        } catch (Exception e) {
            Log.e("AddPendingProductActivity", "Error al configurar el Spinner: " + e.getMessage());
            Toast.makeText(this, "Error al cargar los productos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Botón para marcar producto como pendiente
        savePendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Product selectedProduct = (Product) pendingProductSpinner.getSelectedItem();
                    if (selectedProduct != null) {
                        selectedProduct.setPendiente(true);  // Marcamos como pendiente (no comprado)

                        // Actualizamos el producto en la base de datos
                        ProductList.updateProduct(selectedProduct);

                        // Devolver el producto actualizado a la actividad principal
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("pendingProduct", selectedProduct);
                        setResult(RESULT_OK, resultIntent);
                        finish();  // Finalizar la actividad
                    } else {
                        Toast.makeText(AddPendingProductActivity.this, "No se ha seleccionado un producto", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("AddPendingProductActivity", "Error al guardar el producto pendiente: " + e.getMessage());
                    Toast.makeText(AddPendingProductActivity.this, "Error al guardar el producto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón para cancelar
        cancelPendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}

