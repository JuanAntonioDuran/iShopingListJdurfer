package com.example.ishopinglist.activities.Editor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ishopinglist.R;
import com.example.ishopinglist.activities.DataBase.ProductList;
import com.example.ishopinglist.activities.Models.Product;

public class EditProductActivity extends AppCompatActivity {

    private EditText editProductName;
    private EditText editProductQuantity;
    private CheckBox editProductBought;
    private CheckBox editProductLactosa;
    private CheckBox editProductGlucosa;
    private Button updateButton;
    private Button cancelEditButton;
    private Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        // Declaración de vistas
        editProductName = findViewById(R.id.editProductName);
        editProductQuantity = findViewById(R.id.editProductQuantity);
        editProductBought = findViewById(R.id.editProductBought);
        editProductLactosa = findViewById(R.id.editProductLactosa);
        editProductGlucosa = findViewById(R.id.editProductGlucosa);
        updateButton = findViewById(R.id.updateButton);
        cancelEditButton = findViewById(R.id.cancelEditButton);

        // Recibe el producto del intent
        selectedProduct = (Product) getIntent().getSerializableExtra("product");

        if (selectedProduct != null) {
            // Pre-rellena los elementos
            editProductName.setText(selectedProduct.getName());
            editProductQuantity.setText(selectedProduct.getQuantity()); // Mantener como String
            editProductBought.setChecked(selectedProduct.isPendiente());// Invertir el estado de comprado
            editProductGlucosa.setChecked(selectedProduct.isGluten());
            editProductLactosa.setChecked(selectedProduct.isLactosa());
        }

        // Botón Actualizar
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Revisa que el nombre del producto no esté vacío
                if (editProductName.getText().toString().isEmpty()) {
                    editProductName.setError("El nombre del producto no puede estar vacío");
                    return;
                }
                // Revisa que la cantidad no esté vacía
                String quantityStr = editProductQuantity.getText().toString();
                if (quantityStr.isEmpty()) {
                    editProductQuantity.setError("La cantidad no puede estar vacía");
                    return;
                }

                // Actualiza los detalles del producto
                selectedProduct.setName(editProductName.getText().toString());
                selectedProduct.setQuantity(quantityStr); // Mantener como String
                selectedProduct.setPendiente(editProductBought.isChecked()); // Actualiza el estado de pendiente
                selectedProduct.setGluten(editProductGlucosa.isChecked());
                selectedProduct.setLactosa(editProductLactosa.isChecked());
                // Actualiza el producto de la lista
                ProductList.updateProduct(selectedProduct);

                // Devuelve el producto actualizado
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedProduct", selectedProduct);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        // Botón Cancelar
        cancelEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}









