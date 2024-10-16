package com.example.ishopinglist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ishopinglist.R;
import com.example.ishopinglist.activities.Models.Product;
import com.example.ishopinglist.activities.Adapters.ProductAdapter;
import com.example.ishopinglist.activities.DataBase.ProductList;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView productListView; // Vista de lista para mostrar los productos
    private ProductAdapter adapter; // Adaptador para manejar la lista de productos
    private Button addProductButton, addPendingButton, IrSeleccionados; // Botones para añadir productos
    private Spinner SpinnerLactosaGlucosa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main); // Establecemos el diseño de la actividad
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Ajustamos el padding para que no se superponga con las barras del sistema
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referenciamos los elementos de la interfaz
        productListView = findViewById(R.id.productListView);
        addProductButton = findViewById(R.id.addProductButton);
        addPendingButton = findViewById(R.id.addPendingButton);
        SpinnerLactosaGlucosa = findViewById(R.id.SpinnerLactosaGlucosa);

        // Cargar productos de la lista
        ProductList.preloadProducts();

        // Obtener la lista de productos
        List<Product> productList = ProductList.getProducts();

        // Inicializar el adaptador con la lista de productos
        adapter = new ProductAdapter(this, productList);
        productListView.setAdapter(adapter); // Establecer el adaptador en la lista

        // Manejar la selección de un producto en la lista
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = productList.get(position); // Obtener el producto seleccionado
                Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                intent.putExtra("product", selectedProduct); // Pasar el producto a la siguiente actividad
                startActivityForResult(intent, 3); // Iniciar la actividad para mostrar detalles del producto
            }
        });

        // Listener para el botón que añade un nuevo producto
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivityForResult(intent, 1); // Iniciar la actividad para añadir un nuevo producto
            }
        });

        // Listener para el botón que añade productos pendientes
        addPendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPendingProductActivity.class);
                startActivityForResult(intent, 2); // Iniciar la actividad para añadir productos pendientes
            }
        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.add("Productos Lactosa");
        spinnerAdapter.add("Productos Glucosa");
        spinnerAdapter.add("Todos");
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerLactosaGlucosa.setAdapter(spinnerAdapter);

    }

    // Método para recibir datos de las actividades de añadir/editar productos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) { // Verificar si la respuesta es correcta
            if (requestCode == 1) { // Añadir un nuevo producto
                Product newProduct = (Product) data.getSerializableExtra("newProduct");
                if (newProduct != null) {
                    // Verificar si el producto ya existe en la lista
                    if (!ProductList.getProducts().contains(newProduct)) {
                        // Añadir el nuevo producto a la base de datos
                        ProductList.addProduct(newProduct);
                        Log.d("MainActivity", "Añadiendo nuevo producto: " + newProduct.getName());
                    } else {
                        Log.d("MainActivity", "El producto ya existe: " + newProduct.getName());
                    }
                }
            } else if (requestCode == 2) { // Productos pendientes
                Product pendingProduct = (Product) data.getSerializableExtra("pendingProduct");
                if (pendingProduct != null) {
                    // Actualizar el estado del producto en la lista
                    ProductList.updateProduct(pendingProduct);
                    Log.d("MainActivity", "Producto marcado como pendiente: " + pendingProduct.getName());
                }
            } else if (requestCode == 3) { // Editar producto
                Product updatedProduct = (Product) data.getSerializableExtra("updatedProduct");
                if (updatedProduct != null) {
                    // Actualizar el producto existente en la lista
                    ProductList.updateProduct(updatedProduct);
                    Log.d("MainActivity", "Producto actualizado: " + updatedProduct.getName());
                }
            }

            // Actualizar el adaptador para mostrar los cambios en la lista
            adapter.notifyDataSetChanged();
        }
    }

}





