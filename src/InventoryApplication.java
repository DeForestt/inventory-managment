import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Entry Point Program
 */
public class InventoryApplication extends Application {
    /**
     * Starts the application
     * @param primaryStage Given in command arguments
     * @throws IOException When the FXML file cannot be found
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * initiating class
     * @param args Command Line Arguments and states for the program
     */
    public static void main(String[] args) {
        launch(args);
    }
}
