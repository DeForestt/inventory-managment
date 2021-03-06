import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Modify Products Screen
 */
public class ModifyProductController implements Initializable {
    public TableView<Part> associatedPartsTable;
    public Label idLabel;
    public Label nameLabel;
    public Label invLabel;
    public Label priceLabel;
    public Label maxLabel;
    public Label minLabel;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField invTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TextField minTextField;
    public TableView<Part> avalPartsTable;
    public TableColumn<?, ?> avalIDColumn;
    public TableColumn<?, ?> avalNameColumn;
    public TableColumn<?, ?> avalInvColumn;
    public TableColumn<?, ?> avalPriceColumn;
    public TableColumn<?, ?> idColumn;
    public TableColumn<?, ?> nameColumn;
    public TableColumn<?, ?> invColumn;
    public TableColumn<?, ?> priceColumn;
    public TextField searchTextField;
    public Button addButton;
    public Button removePartButton;
    public Button saveButton;
    public Button cancelButton;
    Product modProduct;
    Product passProduct;

    /**
     * Initializes the Form
     * @param location Inherited from parent
     * @param resources Inherited from parent
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.modProduct = new Product(0, null, 0, 0, 0, 0);

        avalNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        avalIDColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        avalInvColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        avalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        invColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        avalPartsTable.setItems(Inventory.getAllParts());
        associatedPartsTable.setItems(modProduct.getAllAssociatedParts());
    }

    /**
     * Initializes the form with new Product
     * @param p Product to be modified
     */
    public void take(Product p){

        this.idTextField.setText(String.valueOf(p.getId()));
        this.invTextField.setText(String.valueOf(p.getStock()));
        this.maxTextField.setText(String.valueOf(p.getMax()));
        this.minTextField.setText(String.valueOf(p.getMin()));
        this.nameTextField.setText(p.getName());
        this.priceTextField.setText(String.valueOf(p.getPrice()));
        this.passProduct = p;
        this.modProduct = p;
        this.associatedPartsTable.setItems(modProduct.getAllAssociatedParts());
    }

    /**
     * Searches for the id or name when ready the enter button was pushed.
     * Had a runtime Argument Invalid Exception keep coming up because I did not switch the event from onAction to onKeyReleased
     * @param event Key click event
     */
    public void searchTextFieldListener(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            if(searchTextField.getText().equalsIgnoreCase("")) {
                avalPartsTable.setItems(Inventory.getAllParts());
            } else {
                String text = searchTextField.getText();
                Part part = null;
                ObservableList<Part> partHolder = FXCollections.observableArrayList();
                try {
                    if (text.matches("-?\\d+")) {
                        int id = Integer.parseInt(text);
                        part = Inventory.lookupPart(id);
                    } else {
                        part = Inventory.lookupPart(text);
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Search Error!");
                    alert.setHeaderText("part not found");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
                partHolder.add(part);
                avalPartsTable.setItems(partHolder);
            }
        }
    }

    /**
     * Adds the selected part from the avalPartsTable to the products associatedParts list.
     */
    public void addButtonListener() {
        modProduct.addAssociatedPart(avalPartsTable.getSelectionModel().getSelectedItem());
        associatedPartsTable.setItems(modProduct.getAllAssociatedParts());
    }

    /**
     * saves the new part and directs back to the home screen
     * @param event Button click event
     */
    public void saveButtonListener(ActionEvent event) {
        try {
            if (Integer.parseInt(minTextField.getText()) <= Integer.parseInt(invTextField.getText()) &
                    Integer.parseInt(invTextField.getText()) <= Integer.parseInt(maxTextField.getText())) {
                modProduct.setName(nameTextField.getText());
                modProduct.setPrice(Double.parseDouble(priceTextField.getText()));
                modProduct.setMin(Integer.parseInt(minTextField.getText()));
                modProduct.setMax(Integer.parseInt(maxTextField.getText()));
                modProduct.setStock(Integer.parseInt(invTextField.getText()));
                Inventory.updateProduct(Inventory.getAllProducts().indexOf(passProduct), modProduct);

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
     * Returns to the home screen.
     * @param event Button click action event
     * @throws IOException Throws exception if the FXML file cannot be found
     */
    public void cancelButtonListener(ActionEvent event) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        Scene scene = new Scene(home);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Removes a part from the associated parts list
     */
    public void removePartButtonListener() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Wait!!!");
        confirm.setHeaderText("This action cannot be undone");
        confirm.setContentText("Are you sure you would like to remove this item?");
        confirm.showAndWait();
        if(confirm.getResult() == ButtonType.OK) {
            if (!modProduct.deleteAssociatedPart(associatedPartsTable.getSelectionModel().getSelectedItem())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Part Selected");
                alert.showAndWait();
            }
            associatedPartsTable.setItems(modProduct.getAllAssociatedParts());
        }
    }
}
