package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.dataStructures.ArrayStack;
import application.dataStructures.CircularArrayQueue;
import application.dataStructures.UnorderedArrayList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditProductController implements Initializable {
	public TextField nameBox;
	public ComboBox<String> categoryBox;
	public TextField costPriceBox;
	public TextField sellingPriceBox;
	public ComboBox<String> vendorBox;
	public TextField quantityBox;
	public TextField barcodeBox;
	public List<Category> categories;
	public List<Vendor> vendors;
	public Product selectedProduct;

	public void edit() {
		selectedProduct = (Product) costPriceBox.getUserData();

		try {
			Statement statement = DB_Connection.connection.createStatement();

			Category selectedCategory = null;
			Vendor selectedVendor = null;

			for (Category c : categories) {
				if (c.getName().equals(categoryBox.getSelectionModel().getSelectedItem())) {
					selectedCategory = c;
				}
			}
			for (Vendor v : vendors) {
				if (v.getName().equals(vendorBox.getSelectionModel().getSelectedItem())) {
					selectedVendor = v;
				}
			}

//				String query = String.format(
//						"INSERT INTO product(name, category, cost_price, selling_price, supplier, quantity, barcode) VALUES ('%s',%d,%.1f,%.1f,%d,%d,'%s')",
//						nameBox.getText(), selectedCategory.getID(), Double.parseDouble(costPriceBox.getText()),
//						Double.parseDouble(sellingPriceBox.getText()), selectedVendor.getID(),
//						Integer.parseInt(quantityBox.getText()), barcodeBox.getText());

			String query = String.format(
					"UPDATE product SET name = '%s', category = %d, cost_price = %.1f, selling_price = %.1f, supplier = %d, quantity = %d, barcode = '%s' WHERE (id = %d);",
					nameBox.getText(), selectedCategory.getID(), Double.parseDouble(costPriceBox.getText()),
					Double.parseDouble(sellingPriceBox.getText()), selectedVendor.getID(),
					Integer.parseInt(quantityBox.getText()), barcodeBox.getText(), selectedProduct.getID());
			statement.executeUpdate(query);
			
			@SuppressWarnings("unchecked")
			ArrayStack<Product> products1to4 = (ArrayStack<Product>)nameBox.getUserData();
			@SuppressWarnings("unchecked")
			CircularArrayQueue<Product> products5to7 = (CircularArrayQueue<Product>)categoryBox.getUserData();
			@SuppressWarnings("unchecked")
			UnorderedArrayList<Product> products8to11 = (UnorderedArrayList<Product>)quantityBox.getUserData();
			
			
			if (selectedCategory.getID() == 1 || selectedCategory.getID() == 2 || selectedCategory.getID() == 3
					|| selectedCategory.getID() == 4) {
				
				ArrayStack<Product> temp = new ArrayStack<>();
				int size = products1to4.size();
				
				for(int i = 0; i < size; i++) {
					Product p = products1to4.pop();
					if(p.getID() == selectedProduct.getID()) {
						p.setQuantity(p.getQuantity()-Integer.parseInt(quantityBox.getText()));
					}
					temp.push(p);
				}
				products1to4 = temp;
				
			} else if (selectedCategory.getID() == 5 || selectedCategory.getID() == 6 || selectedCategory.getID() == 7) {
				
				CircularArrayQueue<Product> temp = new CircularArrayQueue<>();
				int size = products5to7.size();
				
				for(int i = 0; i < size; i++) {
					Product p = products5to7.dequeue();
					if(p.getID() == selectedProduct.getID()) {
						p.setQuantity(p.getQuantity()-Integer.parseInt(quantityBox.getText()));
					}
					temp.enqueue(p);
				}
				products5to7 = temp;
				
			} else if (selectedCategory.getID() == 8 || selectedCategory.getID() == 9 || selectedCategory.getID() == 10
					|| selectedCategory.getID() == 11) {
				
				UnorderedArrayList<Product> temp = new UnorderedArrayList<>();
				int size = products8to11.size();
				
				for(int i = 0; i < size; i++) {
					Product p = products8to11.removeLast();
					if(p.getID() == selectedProduct.getID()) {
						p.setQuantity(p.getQuantity()-Integer.parseInt(quantityBox.getText()));
					}
					temp.addToRear(p);
				}
				products8to11 = temp;
			}
			
			Stage editStage = (Stage) nameBox.getScene().getWindow();
			editStage.close();

		} catch (SQLIntegrityConstraintViolationException e) {
			ErrorMessage.display("Duplicate Product", "A product with the specified name already exists");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			ErrorMessage.display("Invalid Fields", "Please make sure all your fields are of the correct type");
		}
	}
	
	public void delete() {
		selectedProduct = (Product) costPriceBox.getUserData();
		
		try {
			Statement statement = DB_Connection.connection.createStatement();
			String query = String.format("DELETE FROM product WHERE (id = %d);", selectedProduct.getID());
			statement.executeUpdate(query);

			@SuppressWarnings("unchecked")
			ArrayStack<Product> products1to4 = (ArrayStack<Product>)nameBox.getUserData();
			@SuppressWarnings("unchecked")
			CircularArrayQueue<Product> products5to7 = (CircularArrayQueue<Product>)categoryBox.getUserData();
			@SuppressWarnings("unchecked")
			UnorderedArrayList<Product> products8to11 = (UnorderedArrayList<Product>)quantityBox.getUserData();
			
			ArrayStack<Product> temp = new ArrayStack<>();
			int size = products1to4.size();
			for(int i = 0; i < size; i++) {
				Product p = products1to4.pop();
				if(p.getID() != selectedProduct.getID()) {
					temp.push(p);
				}
			}
			products1to4 = temp;
			
			CircularArrayQueue<Product> temp2 = new CircularArrayQueue<>();
			int size2 = products5to7.size();
			for(int i = 0; i < size2; i++) {
				Product p = products5to7.dequeue();
				if(p.getID() != selectedProduct.getID()) {
					temp2.enqueue(p);
				}
			}
			products5to7 = temp2;
			
			UnorderedArrayList<Product> temp3 = new UnorderedArrayList<>();
			int size3 = products8to11.size();
			for(int i = 0; i < size3; i++) {
				Product p = products8to11.removeLast();
				if(p.getID() != selectedProduct.getID()) {
					temp3.addToRear(p);
				}
			}
			products8to11 = temp3;
			
			Stage editStage = (Stage) nameBox.getScene().getWindow();
			editStage.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Statement statement;
		try {
			statement = DB_Connection.connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM category");

			categories = new ArrayList<>();
			while (result.next()) {
				categories.add(new Category(result.getInt("id"), result.getString("name")));
			}

			result = statement.executeQuery("SELECT * FROM supplier");
			vendors = new ArrayList<>();
			while (result.next()) {
				vendors.add(new Vendor(result.getInt("id"), result.getString("name"), result.getString("phone"),
						result.getString("email")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
