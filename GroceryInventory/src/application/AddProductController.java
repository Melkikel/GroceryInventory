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

public class AddProductController implements Initializable {
	public TextField nameBox;
	public ComboBox<String> categoryBox;
	public TextField costPriceBox;
	public TextField sellingPriceBox;
	public ComboBox<String> vendorBox;
	public TextField quantityBox;
	public TextField barcodeBox;
	public ArrayStack<Product> products1to4;
	public CircularArrayQueue<Product> products5to7;
	public UnorderedArrayList<Product> products8to11;
	public List<Category> categories;
	public List<Vendor> vendors;
	public Product newProduct;

	public void add() {

		if (nameBox.getText().isBlank() || barcodeBox.getText().isBlank() || barcodeBox.getText().length() != 12) {
			ErrorMessage.display("Invalid Fields", "You can't have empty or invalid fields in your product");
		} else {
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


				if (Integer.parseInt(quantityBox.getText()) > 200) {
					ErrorMessage.display("Stocks too high", "The stock allocated is too high!");
				} else {

					String query = String.format(
							"INSERT INTO product(name, category, cost_price, selling_price, supplier, quantity, barcode) VALUES ('%s',%d,%.1f,%.1f,%d,%d,'%s')",
							nameBox.getText(), selectedCategory.getID(), Double.parseDouble(costPriceBox.getText()),
							Double.parseDouble(sellingPriceBox.getText()), selectedVendor.getID(),
							Integer.parseInt(quantityBox.getText()), barcodeBox.getText());

//			String query = String.format(
//					"UPDATE product SET name = '%s', category = %d, cost_price = %.1f, selling_price = %.1f, supplier = %d, quantity = %d, barcode = '%s' WHERE (id = %d);",
//					nameBox.getText(), selectedCategory.getID(), Double.parseDouble(costPriceBox.getText()),
//					Double.parseDouble(sellingPriceBox.getText()), selectedVendor.getID(),
//					Integer.parseInt(quantityBox.getText()), barcodeBox.getText(), selectedProduct.getID());
					statement.executeUpdate(query);
					
					@SuppressWarnings("unchecked")
					ArrayStack<Product> products1to4 = (ArrayStack<Product>)nameBox.getUserData();
					@SuppressWarnings("unchecked")
					CircularArrayQueue<Product> products5to7 = (CircularArrayQueue<Product>)categoryBox.getUserData();
					@SuppressWarnings("unchecked")
					UnorderedArrayList<Product> products8to11 = (UnorderedArrayList<Product>)quantityBox.getUserData();
					
					newProduct = new Product(nameBox.getText(), selectedCategory,
							Double.parseDouble(costPriceBox.getText()),
							Double.parseDouble(sellingPriceBox.getText()), selectedVendor,Integer.parseInt(quantityBox.getText()), barcodeBox.getText());
					if (selectedCategory.getID() == 1 || selectedCategory.getID() == 2 || selectedCategory.getID() == 3
							|| selectedCategory.getID() == 4) {
						products1to4.push(newProduct);
					} else if (selectedCategory.getID() == 5 || selectedCategory.getID() == 6 || selectedCategory.getID() == 7) {
						products5to7.enqueue(newProduct);
					} else if (selectedCategory.getID() == 8 || selectedCategory.getID() == 9 || selectedCategory.getID() == 10
							|| selectedCategory.getID() == 11) {
						products8to11.addToRear(newProduct);
					}

					Stage addStage = (Stage) nameBox.getScene().getWindow();
					addStage.close();
				}

			} catch (SQLIntegrityConstraintViolationException e) {
				ErrorMessage.display("Duplicate Product", "A product with the specified name or barcode already exists");
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				ErrorMessage.display("Invalid Fields", "Please make sure all your fields are of the correct type");
			} catch (NullPointerException e) {
				ErrorMessage.display("Invalid Fields", "You can't have empty fields in your product");
			}
		}
	}

	public void populateStack() {
		Statement statement;
		try {
			statement = DB_Connection.connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM product");

			while (result.next()) {
				int category = result.getInt("category");
				if (category == 1 || category == 2 || category == 3 || category == 4) {
					String name = result.getString("name");

					int vendorID = result.getInt("supplier");
					Vendor selectedV = null;
					Category selectedC = null;
					for (Vendor v : vendors) {
						if (v.getID() == (vendorID)) {
							selectedV = v;
						}
					}
					for (Category c : categories) {
						if (c.getID() == category) {
							selectedC = c;
						}
					}
					products1to4.push(new Product(result.getInt("id"), name, selectedC,
							(double) result.getInt("cost_price"), (double) result.getInt("selling_price"), selectedV,
							result.getInt("quantity"), result.getString("barcode")));
				} else if (category == 5 || category == 6 || category == 7) {
					String name = result.getString("name");

					int vendorID = result.getInt("supplier");
					Vendor selectedV = null;
					Category selectedC = null;
					for (Vendor v : vendors) {
						if (v.getID() == (vendorID)) {
							selectedV = v;
						}
					}
					for (Category c : categories) {
						if (c.getID() == category) {
							selectedC = c;
						}
					}
					products5to7.enqueue(new Product(result.getInt("id"), name, selectedC,
							(double) result.getInt("cost_price"), (double) result.getInt("selling_price"), selectedV,
							result.getInt("quantity"), result.getString("barcode")));
				} else if (category == 8 || category == 9 || category == 10 || category == 11) {
					String name = result.getString("name");

					int vendorID = result.getInt("supplier");
					Vendor selectedV = null;
					Category selectedC = null;
					for (Vendor v : vendors) {
						if (v.getID() == (vendorID)) {
							selectedV = v;
						}
					}
					for (Category c : categories) {
						if (c.getID() == category) {
							selectedC = c;
						}
					}
					products8to11.addToRear(new Product(result.getInt("id"), name, selectedC,
							(double) result.getInt("cost_price"), (double) result.getInt("selling_price"), selectedV,
							result.getInt("quantity"), result.getString("barcode")));
				}
			}
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

			products1to4 = new ArrayStack<>();
			products5to7 = new CircularArrayQueue<>();
			products8to11 = new UnorderedArrayList<>();

			this.populateStack();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
