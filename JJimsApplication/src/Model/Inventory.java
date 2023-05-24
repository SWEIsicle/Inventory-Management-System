package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class meant to hold parts and products for later access.
 *
 * Future Enhancement: Can be improved in the future by better utilizing inheritance to access lists and methods in different contexts
 */

public class Inventory {

    /**
     * An observable list of all inventory parts
     */
    //Field Declarations
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * An observable list of all filtered parts
     */
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    /**
     * An observable list of all inventory products
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * An observable list of all filtered products
     */
    private static ObservableList<Part> filteredProducts = FXCollections.observableArrayList();

    /**
     * Part id to be assigned
     */
    private static int partId = 1;

    /**
     * Adds a new part to inventory
     *
     * @param newPart
     */
    //Method Declarations
    public static void addPart(Part newPart){

        allParts.add(newPart);
    }

    /**
     * Adds a new product to inventory
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct){

        allProducts.add(newProduct);
    }

    /**
     * a String to int conversion function
     *
     * @param input
     */
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * a method to search for parts
     *
     * @param searchTerm
     */
    public static int lookupPart(String searchTerm) {
        boolean isFound = false;
        int index = 0;
            if (isInteger(searchTerm)) {
                for (int i = 0; i < allParts.size(); i++) {
                    if (Integer.parseInt(searchTerm) == allParts.get(i).getId()) {
                        index = i;
                        isFound = true;
                    }
                }
            }
            else {
                for (int i = 0; i < allParts.size(); i++) {
                    searchTerm = searchTerm.toLowerCase();
                    if (searchTerm.equals(allParts.get(i).getName().toLowerCase())) {
                        index = i;
                        isFound = true;
                    }
                }
            }
            if (isFound == true) {
                return index;
            }
            else {
                System.out.println("No parts found.");
                return -1;
            }
    }

    /**
     * a method to search for products
     *
     * @param searchTerm
     */
    public static int lookupProduct(String searchTerm) {
        boolean isFound = false;
        int prodIndex = 0;
        if (isInteger(searchTerm)) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (Integer.parseInt(searchTerm) == allProducts.get(i).getId()) {
                    prodIndex = i;
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (searchTerm.equals(allProducts.get(i).getName())) {
                    prodIndex = i;
                    isFound = true;
                }
            }
        } if (isFound == true) {
            return prodIndex;
        }
        else {
            System.out.println("No products found.");
            return -1;
        }
    }

    /**
     * a method to retrieve list of all parts
     *
     * @return allParts
     */
    public static ObservableList<Part> getAllParts(){

        return allParts;
    }

    /**
     * a method to retrieve filteredParts
     *
     * @return filteredParts
     */
    public static ObservableList<Part> getAllFilteredParts(){

        return filteredParts;
    }

    /**
     * a method to retrieve allProducts
     *
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

    /**
     * a String to int conversion function
     *
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){

        allParts.set(index, selectedPart);
    }

    /**
     * a method to update a product
     *
     * @param index
     * @param product
     */
    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }

    /**
     * a method to delete a part from parts list
     *
     * @param part
     * @return
     */
    public static void deletePart(Part part) {

        allParts.remove(part);
        /*if (allParts.contains(part)) {
            allParts.remove(part);
            return true;
        }
        else {
            return false;
        }*/
    }

    /**
     * a method to delete products from products list
     *
     * @param product
     */
    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    /**
     * a method to generate new part Id
     *
     * @return  partId++
     */
    public static int newId() {
        return partId++;
    }
}

