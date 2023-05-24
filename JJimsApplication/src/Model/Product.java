package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class Product holds product information for manipulation
 */

public class Product {
    //Field Declarations
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    /**
     * Constructor Products
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    //Constructor
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Observable list that retrieves associated parts
     *
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

    /**
     * method for deleting parts in associated part list
     *
     * @param selectedAssociatedPart
     */
    public void deleteAssociatedPart(Part selectedAssociatedPart) {
       associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * method for adding parts to associated parts list
     *
     * @param part
     */
    public void addAssociatedPart(Part part) {

        associatedParts.add(part);
    }
    /**
     * method for retrieving an id
     *
     * @return id
     */

    public int getId() {

        return id;
    }

    /**
     * method for setting an id
     *
     * @param id
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * method for retrieving a name
     *
     * @return name
     */
    public String getName() {

        return name;
    }

    /**
     * method for setting a name
     *
     * @param name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * method for retrieving the price
     *
     * @return price
     */
    public double getPrice() {

        return price;
    }

    /**
     * method for setting the price
     *
     * @param price
     */
    public void setPrice(double price) {

        this.price = price;
    }

    /**
     * method for retrieving the stock value
     *
     * @return stock
     */
    public int getStock() {

        return stock;
    }
    /**
     * method for setting a stock value
     *
     * @param stock
     */

    public void setStock(int stock) {

        this.stock = stock;
    }
    /**
     * method for retrieving the minimum value
     *
     * @return min
     */

    public int getMin() {

        return min;
    }
    /**
     * method for setting a minimum value
     *
     * @param min
     */

    public void setMin(int min) {

        this.min = min;
    }
    /**
     * method for retrieving a maximum value
     *
     * @return max
     */

    public int getMax() {

        return max;
    }
    /**
     * method for setting a maximum value
     *
     * @param max
     */

    public void setMax(int max) {

        this.max = max;
     }
}


