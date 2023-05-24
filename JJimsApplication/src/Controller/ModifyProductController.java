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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the controller for the modify product screen
 *
 * Runtime Errors: Run time errors were caused when trying to cast data types incorrectly,
 * and null pointer exception when trying to access methods from outside the file
 *
 * To get around these errors instances of Product and the observable list are declared
 * in this file, to more simply access methods without null pointer exceptions or data type
 * inconsistencies
 */
public class ModifyProductController implements Initializable {

    /**
     * This declares the observable list for associated parts.
     * This is used to help filter when a product has parts linked to it
     */
    private ObservableList<Part> assoParts = FXCollections.observableArrayList();

    /**
     * This declares an object of the Product class to access methods and select an individual product
     */
    Product selectedProduct;

    @FXML
    private TextField productIdTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> inventoryLevelCol;

    @FXML
    private TableColumn<Part, Double> pricePerUnitCol;

    @FXML
    private TextField searchByPartTxt;

    @FXML
    private TableView<Part> assoPartTableView;

    @FXML
    private TableColumn<Part, Integer> assoPartIdCol;

    @FXML
    private TableColumn<Part, String> assoPartNameCol;

    @FXML
    private TableColumn<Part, Integer> assoInventoryLevelCol;

    @FXML
    private TableColumn<Part, Double> assoPricePerUnitCol;

    /**
     * This method is used to check for logical accuracy of input fields for min and max
     *
     * @return valid
     */
    boolean inputValidation(int min, int max){
        boolean valid = false;

        if(min < max){
            valid = true;
        }
        return valid;
    }

    /**
     * This method is used to check for logical accuracy of input fields for min and max with stock
     *
     * @return valid
     */
    boolean stockValidation(int min, int max, int stock){
        boolean valid = false;

        if(min < stock && stock < max){
            valid = true;
        }
        return valid;
    }

    /**
     * This method simply links back to the main form on button click
     *
     * @param actionEvent
     */
    @FXML
    public void Cancel(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Scene cancelScene = new Scene(addPartsParent);
        Stage cancelStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        cancelStage.setScene(cancelScene);
        cancelStage.show();
    }

    /**
     * This method removes parts from the associated parts list, allowing for dynamic updating of a
     * products associated parts.
     *
     * @param event
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setContentText("Are you sure you want to remove this item?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part part = assoPartTableView.getSelectionModel().getSelectedItem();
            selectedProduct.getAllAssociatedParts().remove(part);
        }

    }
    int id = 0;
    /**
     * This method saves a parts data to inventory, provides a unique id, and sends the user back to the main form
     *
     * @param event
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        try {
            //Current~ Successfully saves product, AND uniquely generates product id
            for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
                id = id + 1;
            }
            String name = productNameTxt.getText();
            int stock = Integer.parseInt(inventoryTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

            if(inputValidation(min, max) && stockValidation(min, max, stock)) {

                Product product = new Product(id, name, price, stock, min, max);

                for (Part part : assoParts) {
                    product.addAssociatedPart(part);
                }

                Inventory.addProduct(product);
                Inventory.deleteProduct(selectedProduct);


                //This works properly to direct to MainForm.
                Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                Scene cancelScene = new Scene(addPartsParent);
                Stage cancelStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                cancelStage.setScene(cancelScene);
                cancelStage.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Logical Error");
                alert.setContentText("Min must be fewer than Max, but Stock should be in between.");
                alert.showAndWait();
            }
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
            }
    }

    /**
     * This method adds parts from the associated parts list, allowing for dynamic updating of a
     * products associated parts
     *
     * @param actionEvent
     */
     public void addToAsso(ActionEvent actionEvent) {

         Part assoPart = partTableView.getSelectionModel().getSelectedItem();
         assoParts.add(assoPart);
         assoPartTableView.setItems(assoParts);
    }

    /**
     * This method loads the part list, the product information, and displays associated parts to a product
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedProduct = MainController.getProductToModify();
        assoParts = selectedProduct.getAllAssociatedParts();


        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assoPartTableView.setItems(selectedProduct.getAllAssociatedParts());
        assoPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assoPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assoInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assoPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdTxt.setText(String.valueOf(selectedProduct.getId()));
        productNameTxt.setText(selectedProduct.getName());
        inventoryTxt.setText(String.valueOf(selectedProduct.getStock()));
        priceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        maxTxt.setText(String.valueOf(selectedProduct.getMax()));
        minTxt.setText(String.valueOf(selectedProduct.getMin()));

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
}
