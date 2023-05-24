package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main controller that links all pages, and sends information about parts/products
 *
 * Future Enhancement: This controller redundantly uses code to map forms. In the future, the system would
 * benefit from a singular method for mapping forms. This would significantly save time
 * the larger the project grows.
 */
public class MainController implements Initializable {

    /**
     * this statement declares a product object to be used to modify
     */
    private static Product productToModify;

    /**
     * this statement declares a part object to be used to modify
     */
    private static Part partToModify;


        @FXML
        public TableColumn partInventoryLevelCol;

        @FXML
        public TableColumn partPricePerUnitCol;

        @FXML
        public TextField searchByPartTxt;

        @FXML
        public TextField searchByProductTxt;

        @FXML
        public TableView<Product> productTableView;

        @FXML
        public TableColumn<Product, Integer> productIdCol;

        @FXML
        public TableColumn<Product, String> productNameCol;

        @FXML
        public TableColumn<Product, Integer> inventoryLevelCol;

        @FXML
        public TableColumn<Product, Double> pricePerUnitCol;

        @FXML
        public TableView<Part> partTableView;

        @FXML
        public TableColumn<Part, Integer> partIdCol;

        @FXML
        public TableColumn<Part, String> partNameCol;

    /**
     * This method retrieves the product to be modified
     *
     * @return productToModify
     */
    public static Product getProductToModify() {
        return productToModify;
    }

    public static Part getPartToModify() {
        return partToModify;
    }

    /**
     * This method deletes parts that are highlighted
     *
     * @param actionEvent
     */
    //Successfully deletes highlighted part.
        public void onActionDelete(javafx.event.ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setContentText("Are you sure you want to remove this item?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Model.Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
            }
        }

    /**
     * This method deletes products only if they have no associated parts.
     *
     * @param actionEvent
     */
        public void onActionDeleteProduct(javafx.event.ActionEvent actionEvent) {
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            ObservableList<Part> assoParts = selectedProduct.getAllAssociatedParts();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Parts are Linked to product");
            alert.setContentText("Remove Items with associated parts.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Model.Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
            }
        else{
            if(assoParts.isEmpty()){
                Model.Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());
            }
        }
    }
    /**
     * This method closes the entire application
     *
     * @param actionEvent
     */
        public void onActionExit(javafx.event.ActionEvent actionEvent) {

            System.exit(0);
        }

    /**
     * This method adds parts to the part list, and sends the user back to the main form
     *
     * @param actionEvent
     */
//This successfully maps MainForm and AddPartsForm
        public void addParts(ActionEvent actionEvent) throws IOException {
            Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View/AddPartsForm.fxml"));
            Scene addPartsScene = new Scene(addPartsParent);
            Stage addPartsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            addPartsStage.setScene(addPartsScene);
            addPartsStage.show();
        }

    /**
     * This method adds products to the product list, and sends the user back to the main form
     *
     * @param actionEvent
     */
//This successfully maps MainForm and AddProductsForm
        public void addProducts(ActionEvent actionEvent) throws IOException {

            Parent addProductsParent = FXMLLoader.load(getClass().getResource("/View/AddProductForm.fxml"));
            Scene addProductsScene = new Scene(addProductsParent);
            Stage addProductsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            addProductsStage.setScene(addProductsScene);
            addProductsStage.show();
        }

    /**
     * This method modifies parts in the part list, and sends the user back to the main form
     *
     * @param actionEvent
     */
        public void modifyParts(ActionEvent actionEvent) throws IOException {

            partToModify = partTableView.getSelectionModel().getSelectedItem();


            Parent parent = FXMLLoader.load(getClass().getResource("../View/ModifyPartsForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }

    /**
     * This method modifies products in the product list, and sends the user back to the main form
     *
     * @param actionEvent
     */
        public void modifyProducts(ActionEvent actionEvent) throws IOException {

            productToModify = productTableView.getSelectionModel().getSelectedItem();

            Parent parent = FXMLLoader.load(getClass().getResource("../View/ModifyProductForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }

    /**
     * This method loads the part list, and product list into tables with stored data
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * This method is used to search for parts, and displays an information alert if a field is incorrectly populated
     *
     * @param event
     */
    @FXML
    private void searchHandler(ActionEvent event) {
        String searchPart = searchByPartTxt.getText();

        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        for (Part part : Inventory.getAllParts()) {
            if (String.valueOf(part.getId()).contains(searchPart) ||
                    part.getName().contains(searchPart)) {
                filteredParts.add(part);
            }
        }
        partTableView.setItems(filteredParts);
    }

    /**
     * This method is used to search for products, and displays an information alert if a field is incorrectly populated
     *
     * @param event
     */
    @FXML
    private void prodSearchHandler(ActionEvent event) {
        String searchProduct = searchByProductTxt.getText();

        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        for (Product product : Inventory.getAllProducts()) {
            if (String.valueOf(product.getId()).contains(searchProduct) ||
                    product.getName().contains(searchProduct)) {
                filteredProducts.add(product);
            }
        }

        productTableView.setItems(filteredProducts);
    }
}

