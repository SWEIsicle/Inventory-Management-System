package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class modifies parts that have already been added to the inventory.
 *
 * Future Enhancement: Could require all minimums to be at least one, to provide better
 * stock conditions for client company
 *
 * Runtime Error: A runtime error occured when trying to pass a parts information into the ModifyPartsController.java.
 * This was rectified using a sendData method from CI's webinar that showed an instance of the controller being made
 * in the "MainController" and sent to the "ModifyPartsController"
 */
public class ModifyPartsController  implements Initializable{

    /**
     * Declares a part to modify
     */
    private Part modifiedPart;


    @FXML
    private RadioButton inHouseRBtn;

    @FXML
    private ToggleGroup partSourceTG;

    @FXML
    private RadioButton outsourcedRBtn;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField pricePerCostTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField machineIdTxt;

    @FXML
    private Label machineIdLbl;

    @FXML
    private TextField companyNameIdTxt;

    @FXML
    private TextField minTxt;

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
     * This method is used in the main controller to send selected part information to be initialized
     * in the ModifyPartsController
     *
     * This works for the modify parts, but caused a runtime error with modify products. To solve the error
     * This sendData method was instead implemented directly into "ModifyProductController.java" initialize method.
     *
     * @param selectedItem
     */
    public void sendData(Part selectedItem){
        if(selectedItem instanceof InHouse) {
            inHouseRBtn.isSelected();
            inHouseRBtn.setSelected(true);
            partIdTxt.setText(String.valueOf(selectedItem.getId()));
            partNameTxt.setText(selectedItem.getName());
            inventoryTxt.setText(String.valueOf(selectedItem.getStock()));
            pricePerCostTxt.setText(String.valueOf(selectedItem.getPrice()));
            maxTxt.setText(String.valueOf(selectedItem.getMax()));
            minTxt.setText(String.valueOf(selectedItem.getMin()));
            machineIdTxt.setText(String.valueOf(((InHouse) selectedItem).getMachineId()));
        }
        if(selectedItem instanceof Outsourced) {
            outsourcedRBtn.isSelected();
            outsourcedRBtn.setSelected(true);
            machineIdLbl.setText("Company Name");
            partIdTxt.setText(String.valueOf(selectedItem.getId()));
            partNameTxt.setText(selectedItem.getName());
            inventoryTxt.setText(String.valueOf(selectedItem.getStock()));
            pricePerCostTxt.setText(String.valueOf(selectedItem.getPrice()));
            maxTxt.setText(String.valueOf(selectedItem.getMax()));
            minTxt.setText(String.valueOf(selectedItem.getMin()));
            machineIdTxt.setText(String.valueOf(((Outsourced) selectedItem).getCompanyName()));

        }
    }

    /**
     * This method simply maps the user back to the main form on button click
     *
     * @param actionEvent
     */
    @FXML//This block successfully links Cancel button on ModifyPartsForm to MainForm
    public void Cancel(ActionEvent actionEvent) throws IOException {
        Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Scene cancelScene = new Scene(addPartsParent);
        Stage cancelStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        cancelStage.setScene(cancelScene);
        cancelStage.show();
    }

    /**
     * This method switches between, and selects, the appropriate radio button
     *
     * @param event
     */
    //Succesfully Switches "Machine ID" and "Company Name"
    @FXML
    void onActionInHouse(ActionEvent event) {
        inHouseRBtn.isSelected();
        if (inHouseRBtn.isSelected()) {
            companyNameIdTxt = machineIdTxt;
            machineIdLbl.setText("Machine ID");
            machineIdTxt.setText("");


        }

    }

    /**
     * This method switches between, and selects, the appropriate radio button
     *
     * @param event
     */
    //Succesfully Switches "Machine ID" and "Company Name"
    @FXML
    void onActionOutsourced(ActionEvent event) {
        outsourcedRBtn.isSelected();
        if (outsourcedRBtn.isSelected()) {
            machineIdTxt = companyNameIdTxt;
            machineIdLbl.setText("Company Name");
            companyNameIdTxt.setText("");

        }
    }
    /**
     * This method saves a parts data to inventory, provides a unique id, and sends the user back to the main form
     *
     * @param event
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            int id = modifiedPart.getId();
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(inventoryTxt.getText());
            double price = Double.parseDouble(pricePerCostTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());
            boolean addPart = false;

            if (inputValidation(min, max) && stockValidation(min, max, stock)) {
                if (inHouseRBtn.isSelected()) {
                    int machineId = Integer.parseInt(machineIdTxt.getText());
                    InHouse inHousePart = new InHouse(id, name, price, stock, min, max, machineId, true);
                    inHousePart.setId(Inventory.newId());
                    addPart = true;
                    Inventory.addPart(inHousePart);
                }
                if (outsourcedRBtn.isSelected()) {
                    String companyName = machineIdTxt.getText();
                    Outsourced part = new Outsourced(id, name, price, stock, min, max, companyName, false);
                    part.setId(Inventory.newId());
                    addPart = true;
                    Inventory.addPart(part);
                }
                if(addPart){
                    Inventory.deletePart(modifiedPart);

                    Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                    Scene cancelScene = new Scene(addPartsParent);
                    Stage cancelStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    cancelStage.setScene(cancelScene);
                    cancelStage.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Logical Error");
                alert.setContentText("Min must be fewer than Max, but Stock should be in between.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Form contains incorrect field.");
            alert.showAndWait();
        }
    }
    /**
     * Initializes part data from selected part on MainScreen
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        modifiedPart = MainController.getPartToModify();

        if (modifiedPart instanceof InHouse) {
            inHouseRBtn.setSelected(true);
            machineIdLbl.setText("Machine ID");
            machineIdTxt.setText(String.valueOf(((InHouse) modifiedPart).getMachineId()));
        }

        if (modifiedPart instanceof Outsourced){
            outsourcedRBtn.setSelected(true);
            machineIdLbl.setText("Company Name");
            machineIdTxt.setText(((Outsourced) modifiedPart).getCompanyName());
        }

        partIdTxt.setText(String.valueOf(modifiedPart.getId()));
        partNameTxt.setText(modifiedPart.getName());
        inventoryTxt.setText(String.valueOf(modifiedPart.getStock()));
        pricePerCostTxt.setText(String.valueOf(modifiedPart.getPrice()));
        maxTxt.setText(String.valueOf(modifiedPart.getMax()));
        minTxt.setText(String.valueOf(modifiedPart.getMin()));
    }
}
