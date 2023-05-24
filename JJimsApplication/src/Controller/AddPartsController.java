package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class controller handles adding parts to the parts list and inventory.
 *
 * Logical Errors: Logical errors that occurred came from lack of input validation, such as min being less than max,
 * max being greater than min, and stock being in between. This was solved using the inputValidation and
 * stockValidation methods.
 *
 * Future Enhancement: This class could benefit from a more contiguous id for loop, to better represent part's uniqueness
 */
public class AddPartsController {


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
     * This method simply links back to the main form on button click
     *
     * @param actionEvent
     */
    @FXML//This block successfully links Cancel button on AddPartsForm to MainForm
    public void Cancel(ActionEvent actionEvent) throws IOException {
            Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
            Scene cancelScene = new Scene(addPartsParent);
            Stage cancelStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            cancelStage.setScene(cancelScene);
            cancelStage.show();
    }

    /**
     * This method is used to cause an event that selects the appropriate radio button
     *
     * @param event
     */
    //Successfully Switches "Machine ID" and "Company Name"
    @FXML
    void onActionInHouse(ActionEvent event) {
        inHouseRBtn.isSelected();
        if (inHouseRBtn.isSelected()) {
            machineIdLbl.setText("Machine ID");
        }

    }

    /**
     * This method is used to cause an event that selects the appropriate radio button
     *
     * @param event
     */
    //Successfully Switches "Machine ID" and "Company Name"
    @FXML
    void onActionOutsourced(ActionEvent event) {
        outsourcedRBtn.isSelected();
        if (outsourcedRBtn.isSelected()) {
            machineIdLbl.setText("Company Name");
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
            int id = 1;
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(inventoryTxt.getText());
            double price = Double.parseDouble(pricePerCostTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

            if (inputValidation(min, max) && stockValidation(min, max, stock)) {
                if (inHouseRBtn.isSelected()) {
                    int machineId = Integer.parseInt(machineIdTxt.getText());
                    InHouse inHousePart = new InHouse(id, name, price, stock, min, max, machineId, true);
                    inHousePart.setId(Inventory.newId());

                    Inventory.addPart(inHousePart);

                    Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                    Scene cancelScene = new Scene(addPartsParent);
                    Stage cancelStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    cancelStage.setScene(cancelScene);
                    cancelStage.show();
                }
                if (outsourcedRBtn.isSelected()) {
                    String companyName = machineIdTxt.getText();
                    Outsourced part = new Outsourced(id, name, price, stock, min, max, companyName, false);
                    part.setId(Inventory.newId());

                    Inventory.addPart(part);

                    Parent addPartsParent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                    Scene cancelScene = new Scene(addPartsParent);
                    Stage cancelStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    cancelStage.setScene(cancelScene);
                    cancelStage.show();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Logical Error");
                alert.setContentText("Min must be fewer than Max, but Stock should be in between.");
                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Form contains incorrect field.");
            alert.showAndWait();
            }
    }


}
