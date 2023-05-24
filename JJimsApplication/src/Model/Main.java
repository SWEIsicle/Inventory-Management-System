package Model;

import com.sun.xml.internal.ws.wsdl.writer.document.Part;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** This class is he main function for an inventory management system.
 *
 *  Future Enhancement: This application can be improved with the implementation of a database for larger sets of data.
 *
 * Location of JAVADOC folder: D:\Computer Science\JavaDoc
 */

public class Main extends Application {

    /** This method sets, and loads the stage.
     *
     *   @param primaryStage
     *   @throws Exception
     *
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Displays sample data
     *
     *  Can be improved with a more integrated id allocation.
     *
     * @Param args
     */
    public static void main(String[] args) {

        InHouse part1 = new InHouse(101,"Cheese", 2.99, 10, 0, 20, 101, true);
        InHouse part2 = new InHouse(102, "Sauce", .99, 5, 0, 20, 102, true);
        InHouse part3 = new InHouse(103, "Dough", 1.99, 20, 0, 20, 103, true);
        InHouse part4 = new InHouse(104, "Oregano", .79, 2, 0, 20, 104, true);
        Outsourced part5 = new Outsourced(105, "Basil",.79, 2, 0, 20, "Herbs amd Spices", false);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);

        launch(args);
    }
}
