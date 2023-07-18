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
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PushProductController implements Initializable {

	public ComboBox<String> categoryBox;
	public ComboBox<String> vendorBox;
	public TextField nameBox;
	public TextField quantityBox;
	public TextField costPriceBox;
	public TextField sellingPriceBox;
	public TextField barcodeBox;
	public ArrayStack<Product> products1to4;
	public List<Category> categories;
	public List<Vendor> vendors;
	public Product newProduct;

	public void push() {

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
					ArrayStack<Product> products1to4 = (ArrayStack<Product>) nameBox.getUserData();

					newProduct = new Product(nameBox.getText(), selectedCategory,
							Double.parseDouble(costPriceBox.getText()), Double.parseDouble(sellingPriceBox.getText()),
							selectedVendor, Integer.parseInt(quantityBox.getText()), barcodeBox.getText());
					
					if (selectedCategory.getID() == 1 || selectedCategory.getID() == 2 || selectedCategory.getID() == 3
							|| selectedCategory.getID() == 4) {
						products1to4.push(newProduct);
					}

					Stage pushStage = (Stage) nameBox.getScene().getWindow();
					pushStage.close();
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
				if (id == 1 || id == 2 || id == 3 || id == 4) {
					categories.add(new Category(result.getInt("id"), result.getString("name")));
				}
			}

			result = statement.executeQuery("SELECT * FROM supplier");
			vendors = new ArrayList<>();
			while (result.next()) {
				vendors.add(new Vendor(result.getInt("id"), result.getString("name"), result.getString("phone"),
						result.getString("email")));
			}

			products1to4 = new ArrayStack<>();

			this.populateStack();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
