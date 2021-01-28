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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Home Screen Controller
 * Next update will include the ability to change stock numbers from the home page
 * @author DeForestt
 * @version 1.0
 */
public class HomeScreenController implements Initializable {

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partIDTableColumn;

    @FXML
    private TableColumn<Part, String> partNameTableColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelTableColumn;

    @FXML
    private TableColumn<Part, Double> partPriceCostTableColumn;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productIDTableColumn;

    @FXML
    private TableColumn<Product, String> productNameTableColumn;

    @FXML
    private TableColumn<Product, Integer> productInventoryLevelTableColumn;

    @FXML
    private TableColumn<Product, Double> productPriceCostTableColumn;


    @FXML
    private TextField partsSearchTextField;

    @FXML
    private TextField productsSearchTextField;


    @FXML
    private Button exitButton;

    /**
     * Opens the add new part dialog
     * @param event button action event
     * @throws IOException Throws exception FXML file cannot be found
     */
    public void addPartButtonListener(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Opens the add new Product dialog
     * @param event button action event
     * @throws IOException Throws exception FXML file cannot be found
     */
    public void addProductButtonListener(ActionEvent event) throws IOException {
        Parent addProduct = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene scene = new Scene(addProduct);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Deletes the selected part
     */
    public void deletePartButton() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Wait!!!");
        confirm.setHeaderText("This action cannot be undone");
        confirm.setContentText("Are you sure you would like to remove this item?");
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.OK) {
            if (partsTable.getSelectionModel().getSelectedItem() != null) {
                Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
                Inventory.deletePart(selectedPart);
                partsTable.setItems(Inventory.getAllParts());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Selection found");
                alert.setHeaderText("No Part Selected");
                alert.setContentText("Please Select a part before attempting to modify.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Deletes selected product
     */
    public void deleteProductButtonListener() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Wait!!!!");
        confirm.setHeaderText("This action cannot be undone");
        confirm.setContentText("Are you sure you would like to remove this item?");
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.OK) {
            Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                if (selectedProduct.getAllAssociatedParts().size() == 0) {
                    Inventory.deleteProduct(selectedProduct);
                    productsTable.setItems(Inventory.getAllProducts());
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cannot Delete Product");
                    alert.setHeaderText("Attempted To Delete Product With Parts Associated.");
                    alert.setContentText("Please Remove associated parts before deleting product.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Selection found");
                alert.setHeaderText("No Product Selected");
                alert.setContentText("Please Select a product before attempting to delete");
                alert.showAndWait();
            }
        }
    }

    /**
     * Closes the program
     */
    public void exitButtonListener() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Opens the modify part form
     * @param event Button Action Event
     * @throws IOException Throws when FXML file cannot be found
     */
    public void modifyPartButtonListener(ActionEvent event) throws IOException {
        if(partsTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            Parent addParts = loader.load();
            Scene scene = new Scene(addParts);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            ModifyPartController controller = loader.getController();
            controller.take(partsTable.getSelectionModel().getSelectedItem());
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Selection found");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please Select a part before attempting to modify");
            alert.showAndWait();
        }
    }

    /**
     * Opens the modify Product form
     * @param event Button Action Event
     * @throws IOException Throws when FXML file cannot be found
     */
    public void modifyProductButtonListener(ActionEvent event) throws IOException {
        if(productsTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
            Parent addProduct = loader.load();
            Scene scene = new Scene(addProduct);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            ModifyProductController controller = loader.getController();
            controller.take(productsTable.getSelectionModel().getSelectedItem());
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Selection found");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please Select a product before attempting to modify");
            alert.showAndWait();
        }
    }

    /**
     * Searches for the part by id or name
     * @param event Key Event
     */
    public void partsSearchTextFieldListener(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if(partsSearchTextField.getText().equalsIgnoreCase("")) {
                partsTable.setItems(Inventory.getAllParts());
            } else {
                String text = partsSearchTextField.getText();
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
                partsTable.setItems(partHolder);
            }
        }
    }

    /**
     * Searches for the product by id or name
     * @param event Key Event
     */
    public void productsSearchTextFieldListener(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if(productsSearchTextField.getText().equalsIgnoreCase("")) {
                productsTable.setItems(Inventory.getAllProducts());
            } else {
                String text = partsSearchTextField.getText();
                Product product = null;
                ObservableList<Product> productHolder = FXCollections.observableArrayList();

                try {
                    if (text.matches("-?\\d+")) {
                        int id = Integer.parseInt(text);
                        product = Inventory.lookupProduct(id);
                    } else {
                        product = Inventory.lookupProduct(text);
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Search Error!");
                    alert.setHeaderText("Product not found");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
                productHolder.add(product);
                productsTable.setItems(productHolder);
            }
        }

    }

    /**
     * Initializes the form
     * @param location Inherited from parent
     * @param resources Inherited from parent
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partInventoryLevelTableColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPriceCostTableColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        productNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productInventoryLevelTableColumn.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productPriceCostTableColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());

    }
}
