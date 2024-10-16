package com.example.ishopinglist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ishopinglist.R;
import com.example.ishopinglist.activities.Models.Product;
import com.example.ishopinglist.activities.DataBase.ProductList;

public class AddProductActivity extends AppCompatActivity {

    private EditText productNameInput;
    private EditText productQuantityInput;
    private CheckBox productBoughtInput;
    private CheckBox productLactosaInput;
    private CheckBox productGlutenInput;
    private Button saveButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referenciar los elementos del layout
        productNameInput = findViewById(R.id.productNameInput);
        productQuantityInput = findViewById(R.id.productQuantityInput);
        productBoughtInput = findViewById(R.id.productBoughtInput);
        productLactosaInput = findViewById(R.id.productLactosaInput);
        productGlutenInput = findViewById(R.id.productGlutenInput);

        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Botón para guardar el nuevo producto
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de texto y checkbox
                String name = productNameInput.getText().toString();
                String quantity = productQuantityInput.getText().toString();
                boolean isBought = productBoughtInput.isChecked();
                boolean isLactosa = productLactosaInput.isChecked();
                boolean isGlucosa = productGlutenInput.isChecked();
                // Crear un nuevo producto con un ID único generado automáticamente
                int newProductId = ProductList.getNextProductId(); // Obtener el próximo ID
                Product newProduct = new Product(newProductId, name, quantity, isBought, isLactosa, isGlucosa);

                // Devolver el resultado a la actividad principal
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newProduct", newProduct);
                setResult(RESULT_OK, resultIntent);
                finish(); // Finalizar la actividad y volver a la principal
            }
        });

        // Botón para cancelar la creación del producto y volver a la pantalla principal
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}

