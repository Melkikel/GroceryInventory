package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddToCartController {
	public Label barcodeLabel;
	public Label nameLabel;
	public Label categoryLabel;
	public Label priceLabel;
	public Label vendorLabel;
	public Label quantityLabel;
	public TextField quantityBox;

	public void add() {
		Product selectedProduct = (Product) nameLabel.getUserData();

		if (selectedProduct.getQuantity() < Integer.parseInt(quantityBox.getText())) {
			ErrorMessage.display("Insufficient Products", "The quantity enterd is greater than the available stock");
		} else if ((selectedProduct.getQuantity() - Integer.parseInt(quantityBox.getText())) < 20) {
			ErrorMessage.display("Stock too low", "This product will run low on stock. Please add more");
		} else {
			Statement statement;
			try {
				statement = DB_Connection.connection.createStatement();

				String query = String.format(
						"INSERT INTO inventory_management_system.order(name, quantity_sold, total_price, supplier, barcode) VALUES ('%s',%d,%.2f,%d,'%s')",
						selectedProduct.getName(), Integer.parseInt(quantityBox.getText()),
						selectedProduct.getSellingPrice() * Double.parseDouble(quantityBox.getText()),
						selectedProduct.getVendor().getID(), selectedProduct.getBarcode());

				statement.executeUpdate(query);

				query = String.format("UPDATE product SET quantity = %d WHERE (id = %d)",
						selectedProduct.getQuantity() - Integer.parseInt(quantityBox.getText()),
						selectedProduct.getID());

				statement.executeUpdate(query);

				Stage addStage = (Stage) nameLabel.getScene().getWindow();
				addStage.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int returnQuantity() {
		if (!quantityBox.getText().isBlank())
			return Integer.parseInt(quantityBox.getText());
		return 0;
	}

	public Order returnOrder() {
		Product selectedProduct = (Product) nameLabel.getUserData();
		Order o = null;
		try {
			Statement statement = DB_Connection.connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM inventory_management_system.order");
			while (result.next()) {
				o = new Order(result.getInt("id"), result.getString("name"), result.getString("barcode"),
						result.getInt("quantity_sold"), result.getDouble("total_price"), selectedProduct.getVendor());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return o;
	}
}
