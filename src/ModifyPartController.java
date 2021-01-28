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

public class ModifyPartController implements Initializable {

    @FXML
    public ToggleGroup toggleGroup;

    @FXML
    private RadioButton inHouse;

    @FXML
    private RadioButton outSourced;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField invTextField;

    @FXML
    private TextField priceCosTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private TextField machineIDTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private Label companyLabel;

    @FXML
    private TextField companyTextField;

    @FXML
    private Label machineIDLabel;

    private Part activePart;


    /**
     * Replaces the part on the parts list
     * @param event Button Action Event
     */
    public void saveButtonListener(ActionEvent event) {
        try {
            if (Integer.parseInt(minTextField.getText()) <= Integer.parseInt(invTextField.getText()) &
                    Integer.parseInt(invTextField.getText()) <= Integer.parseInt(maxTextField.getText())) {
                if (this.inHouse.isSelected()) {
                    String name = nameTextField.getText();
                    double price = Double.parseDouble(priceCosTextField.getText());
                    int min = Integer.parseInt(minTextField.getText());
                    int max = Integer.parseInt(maxTextField.getText());
                    int stock = Integer.parseInt(invTextField.getText());
                    int machineId = Integer.parseInt(machineIDTextField.getText());
                    InHouse inHouse = new InHouse(activePart.getId(), name, price, stock, min, max, machineId);
                    Inventory.updatePart(Inventory.getAllParts().indexOf(this.activePart), inHouse);
                } else if (this.outSourced.isSelected()) {
                    String name = nameTextField.getText();
                    double price = Double.parseDouble(priceCosTextField.getText());
                    int min = Integer.parseInt(minTextField.getText());
                    int max = Integer.parseInt(maxTextField.getText());
                    int stock = Integer.parseInt(invTextField.getText());
                    String company = companyTextField.getText();
                    Outsourced outsourced = new Outsourced(activePart.getId(), name, price, stock, min, max, company);
                    Inventory.updatePart(Inventory.getAllParts().indexOf(this.activePart), outsourced);
                }

                Parent home = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
                Scene scene = new Scene(home);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Value Error");
                alert.setHeaderText("Invalid inventory constraints");
                alert.setContentText("Please Ensure that Min <= Inv <= Max");
                alert.showAndWait();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Value Error");
            alert.setHeaderText("Invalid Values Entered");
            alert.setContentText("Please Ensure that the correct data types are entered in the form" + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Switches the form to allow the user to enter an in-house part with a machine ID
     */
    public void inHouseListener(){
        this.companyTextField.setVisible(false);
        this.companyLabel.setVisible(false);
        this.machineIDLabel.setVisible(true);
        this.machineIDTextField.setVisible(true);
    }

    /**
     * Switches form to allow the user to enter an outsourced part with a company name
     */
    public void outsourcedListener(){
        this.machineIDLabel.setVisible(false);
        this.machineIDTextField.setVisible(false);
        this.companyTextField.setVisible(true);
        this.companyLabel.setVisible(true);
    }

    /**
     * Closes the form without saving
     * @param event Button Action Event
     * @throws IOException Exception thrown if the Home screen cannot be found.
     */
    public void cancelButtonListener(ActionEvent event) throws IOException{
        Parent home = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        Scene scene = new Scene(home);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Initializes the input form
     * @param location Drawn from Parent
     * @param resources Drawn from Parent
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.idTextField.setDisable(true);
        this.toggleGroup = new ToggleGroup();
        inHouse.setToggleGroup(toggleGroup);
        outSourced.setToggleGroup(toggleGroup);
    }

    /**
     * Initializes the form with new part
     * @param p Part to be modified
     */
    public void take(Part p) {
        this.activePart = p;
        this.nameTextField.setText(activePart.getName());
        this.invTextField.setText(String.valueOf(activePart.getStock()));
        this.priceCosTextField.setText(String.valueOf(activePart.getPrice()));
        this.maxTextField.setText(String.valueOf(activePart.getMax()));
        this.minTextField.setText(String.valueOf(activePart.getMin()));
        this.idTextField.setText(String.valueOf(activePart.getId()));
        if (this.activePart instanceof InHouse) {
            this.companyTextField.setVisible(false);
            this.companyLabel.setVisible(false);
            this.machineIDLabel.setVisible(true);
            this.machineIDTextField.setVisible(true);
            this.machineIDTextField.setText(String.valueOf(((InHouse) activePart).getMachineId()));
            this.inHouse.setSelected(true);
        }
        else if (this.activePart instanceof Outsourced) {
            this.companyTextField.setVisible(true);
            this.companyLabel.setVisible(true);
            this.machineIDLabel.setVisible(false);
            this.machineIDTextField.setVisible(false);
            this.companyTextField.setText(((Outsourced) activePart).getCompanyName());
            this.outSourced.setSelected(true);
        }
    }
}
