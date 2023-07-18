package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.dataStructures.UnorderedArrayList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductController2 implements Initializable {
	
	public ComboBox<String> categoryBox;
	public ComboBox<String> vendorBox;
	public TextField nameBox;
	public TextField quantityBox;
	public TextField costPriceBox;
	public TextField sellingPriceBox;
	public TextField barcodeBox;
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
					statement.executeUpdate(query);

					@SuppressWarnings("unchecked")
					UnorderedArrayList<Product> products8to11 = (UnorderedArrayList<Product>) nameBox.getUserData();

					newProduct = new Product(nameBox.getText(), selectedCategory,
							Double.parseDouble(costPriceBox.getText()), Double.parseDouble(sellingPriceBox.getText()),
							selectedVendor, Integer.parseInt(quantityBox.getText()), barcodeBox.getText());
					
					if (selectedCategory.getID() == 8 || selectedCategory.getID() == 9 || selectedCategory.getID() == 10 || selectedCategory.getID() == 11) {
						products8to11.addToRear(newProduct);
					}

					Stage enqueueStage = (Stage) nameBox.getScene().getWindow();
					enqueueStage.close();
				}

			} catch (SQLIntegrityConstraintViolationException e) {
				ErrorMessage.display("Duplicate Product",
						"A product with the specified name or barcode already exists");
			} catch (SQLException e) {
				e.printStackTrace();
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
				if (category == 8 || category == 9 || category == 10 || category == 11) {
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
				int id = result.getInt("id");
				if (id == 8 || id == 9 || id == 10 || id == 11) {
					categories.add(new Category(result.getInt("id"), result.getString("name")));
				}
			}

			result = statement.executeQuery("SELECT * FROM supplier");
			vendors = new ArrayList<>();
			while (result.next()) {
				vendors.add(new Vendor(result.getInt("id"), result.getString("name"), result.getString("phone"),
						result.getString("email")));
			}

			products8to11 = new UnorderedArrayList<>();

			this.populateStack();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
