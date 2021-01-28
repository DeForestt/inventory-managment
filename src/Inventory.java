import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Static class that manages all of the items in the inventory as ObservableLists
 */
public class Inventory {

  private final static ObservableList<Part> allParts = FXCollections.observableArrayList();
  private final static ObservableList<Product> allProducts = FXCollections.observableArrayList();


  /**
   * Adds parts to the inventory
   * @param newPart Part to be added
   */
  public static void addPart(Part newPart) {
   allParts.add(newPart);
  }

  /**
   * adds Products to the inventory
   * @param newProduct product to be added
   */
  public static void addProduct(Product newProduct) {
   allProducts.add(newProduct);
  }

 /**
  * Looks up a part by ID
  * @param partId INT id of the part to found
  * @return returns the found part
  * @throws Exception Throws an exception if the part cannot be found in the inventory
  */
  public static Part lookupPart(int partId) throws Exception {
   for (Part part:allParts) {
    if(part.getId() == partId){
     return part;
    }
   }
   throw new Exception("Part number '" + partId + "does not exist in inventory");
  }

 /**
  * Looks up a product by ID
  * @param productId INT id of the product to found
  * @return returns the found product
  * @throws Exception Throws an exception if the product cannot be found in the inventory
  */
  public static Product lookupProduct(int productId) throws Exception{
   for (Product product: allProducts
        ) {
    if(product.getId() == productId){
     return product;
    }
   }
   throw new Exception("Product with ID '" + productId + "' does not exist in inventory'");
  }

 /**
  * Looks up a part by Name
  * @param partName String name of the part to found
  * @return returns the found part
  * @throws Exception Throws an exception if the part cannot be found in the inventory
  */
  public static Part lookupPart(String partName) throws Exception {
   for (Part part:allParts) {
    if(part.getName().toLowerCase().contains(partName.toLowerCase())){
      return part;
    }
   }
   throw new Exception("Part with the name '" + partName + "' does not exist in inventory");
  }

 /**
  * Looks up a product by Name
  * @param productName INT id of the part to found
  * @return returns the found product
  * @throws Exception Throws an exception if the product cannot be found in the inventory
  */
  public static Product lookupProduct(String productName) throws Exception{
   for (Product product: allProducts
   ) {
    if(product.getName().toLowerCase().contains(productName.toLowerCase())){
     return product;
    }
   }
   throw new Exception("Product with name '" + productName + "' does not exist in inventory'");
  }

 /**
  * Updates the part at a specific index of the list with the new part
  * @param index INT index to be updated
  * @param selectedPart Part to be inserted into list
  * @throws ArrayIndexOutOfBoundsException When the index is out of bounds of the array
  */
  public static void updatePart(int index, Part selectedPart) throws ArrayIndexOutOfBoundsException{
   try {
    allParts.set(index, selectedPart);
   } catch(ArrayIndexOutOfBoundsException e){throw e;}
  }

 /**
  * Updates the Product at a specific index of the list with the new part
  * @param index INT index to be updated
  * @param selectedProduct Product to be inserted into list
  * @throws ArrayIndexOutOfBoundsException When the index is out of bounds of the array
  */
  public static void updateProduct(int index, Product selectedProduct) throws ArrayIndexOutOfBoundsException{
   try {
    allProducts.set(index, selectedProduct);
   } catch(ArrayIndexOutOfBoundsException e){throw e;}
  }

 /**
  * Deletes a part
  * @param selectedPart Part to be deleted
  * @return Bool of whether or not the part was found
  */
  public static boolean deletePart(Part selectedPart){
   for(Part part:allParts){
    if (part.getId() == selectedPart.getId()){
     allParts.remove(part);
     return true;
    }
   }
   return false;
  }

 /**
  * Deletes a Product
  * @param selectedProduct Product to be deleted
  * @return Bool of whether or not the product was found
  */
  public static boolean deleteProduct(Product selectedProduct){
   for(Product product: allProducts){
    if (product.getId() == selectedProduct.getId()){
     allProducts.remove(product);
     return true;
    }
   }
   return false;
  }

 /**
  * Getter for parts list
  * @return ObservableList Parts
  */
 public static ObservableList<Part> getAllParts(){
   return allParts;
  }

 /**
  * Getter for product list
  * @return observableList of Products
  */
 public static ObservableList<Product> getAllProducts(){
  return allProducts;
  }

}


