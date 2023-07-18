package application;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditVendorController {
	public TextField nameBox;
	public TextField phoneBox;
	public TextField emailBox;
	public Vendor selectedVendor;
	
	public void edit() {
		selectedVendor = (Vendor) nameBox.getUserData();
		try {
			Statement statement = DB_Connection.connection.createStatement();
			
			String query = String.format(
					"UPDATE supplier SET name = '%s', phone = '%s', email = '%s' WHERE (id = %d);",
					nameBox.getText(), phoneBox.getText(), emailBox.getText(), selectedVendor.getID());
			System.out.println(statement.executeUpdate(query));
			Stage editStage = (Stage) nameBox.getScene().getWindow();
			editStage.close();
		
		}catch(SQLIntegrityConstraintViolationException e) {
			ErrorMessage.display("Duplicate Vendor", "A vendor with the specified name already exists");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete() {
		selectedVendor = (Vendor) nameBox.getUserData();
		try {
			Statement statement = DB_Connection.connection.createStatement();
			String query = String.format("DELETE FROM supplier WHERE (id = %d);", selectedVendor.getID());
			statement.executeUpdate(query);
			
			Stage editStage = (Stage) nameBox.getScene().getWindow();
			editStage.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
