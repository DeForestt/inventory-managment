import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock, min, max;

    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        associatedParts = FXCollections.observableArrayList();

    }

    //region Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
    //endregion

    //region Getters

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public int getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    //endregion

    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else return false;
    }


}
