package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddVendorController implements Initializable {

	public TextField nameBox;
	public TextField phoneBox;
	public TextField emailBox;
	public List<Vendor> vendors;
	public HashMap<Integer, Vendor> myVendors;

	public void add() {
		
		if (nameBox.getText().isBlank()) {
			ErrorMessage.display("Invalid Fields", "You can't have empty or invalid fields in your product");
		} else {
			try {
				Statement statement = DB_Connection.connection.createStatement();
				String query = String.format("INSERT INTO supplier(name, phone, email) VALUES ('%s','%s','%s')",
						nameBox.getText(), phoneBox.getText(), emailBox.getText());

				statement.executeUpdate(query);
				Stage addStage = (Stage) nameBox.getScene().getWindow();
				addStage.close();

			}catch (SQLIntegrityConstraintViolationException e) {				
				ErrorMessage.display("Duplicate Vendor", "A vendor with the specified name already exists");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		myVendors = new HashMap<>();
		vendors = new ArrayList<>();
		try {
			Statement statement = DB_Connection.connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM supplier");
			while (result.next()) {
				vendors.add(new Vendor(result.getInt("id"), result.getString("name"), result.getString("phone"),
						result.getString("email")));
				
				myVendors.put(result.getInt("id"), new Vendor(result.getInt("id"), result.getString("name"), result.getString("phone"),
						result.getString("email")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
