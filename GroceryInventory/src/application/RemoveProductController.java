package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.dataStructures.UnorderedArrayList;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoveProductController implements Initializable {
	
	public TextField idBox;
	UnorderedArrayList<Product> products8to11;

	public List<Category> categories;
	public List<Vendor> vendors;

	public void remove() {
		
		if(idBox.getText().isBlank()) {
			ErrorMessage.display("Invalid Field", "Please enter a valid id");
		} else {
			int size = products8to11.size();
			boolean found = false;
			for(int i = 0; i < size; i++) {
				Product p = products8to11.removeLast();
				if(Integer.parseInt(idBox.getText()) == p.getID()) {
					found = true;
					break;
				}
			}
			if(found) {				
				Stage removeStage = (Stage) idBox.getScene().getWindow();
				removeStage.close();
			} else {
				ErrorMessage.display("ID not found", "The id was not found");
			}
		}
		populateStack();
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
