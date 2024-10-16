package com.example.ishopinglist.activities.DataBase;
import com.example.ishopinglist.activities.Models.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
public class ProductList {


        // Lista estática para simular una base de datos en memoria
        private static List<Product> products = new ArrayList<>();

        // Método para obtener la lista de productos
        public static List<Product> getProducts() {
            return products;
        }

        // Método para añadir un producto a la lista
        public static void addProduct(Product product) {
            products.add(product);
        }

        // Método para actualizar un producto en la lista
        public static void updateProduct(Product updatedProduct) {
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                if (product.getId() == updatedProduct.getId()) {
                    products.set(i, updatedProduct);
                    break;
                }
            }
        }

        // Método para eliminar un producto de la lista
        public static void deleteProduct(int productId) {
            products.removeIf(product -> product.getId() == productId);
        }

        // Método para buscar un producto por su ID
        public static Product getProductById(int productId) {
            for (Product product : products) {
                if (product.getId() == productId) {
                    return product;
                }
            }
            return null; // Si no se encuentra el producto
        }

    public static int getNextProductId() {
        return products.size() + 1; // Asignar un nuevo ID basado en el tamaño de la lista actual
    }

        // Método para precargar algunos productos de ejemplo
        public static void preloadProducts() {
            if (products.isEmpty()) {
                products.add(new Product(1, "Leche", "2", false,true,false));
                products.add(new Product(2, "Pan", "1", false, false,true));
                products.add(new Product(3, "Huevos", "12", true , true, true));
            }
        }
    }

