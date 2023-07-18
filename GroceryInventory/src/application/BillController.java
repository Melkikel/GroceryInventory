package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import org.json.JSONArray;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BillController implements Initializable {
	public ObservableList<Bill> bills;
	public TableView<Bill> billTable;
	public TableColumn<Bill, String> idColumn;
	public TableColumn<Bill, String> quantityColumn;
	public TableColumn<Bill, String> priceColumn;
	public TableColumn<Bill, String> dateColumn;
	public Label totalItems;
	public Label totalBills;
	public ListView<String> productList;
	public ListView<Integer> quantityList;
	public Label productName;
	public Label categoryLabel;
	public Label quantityLabel;
	public Label costLabel;
	public Label sellingLabel;
	public ImageView barcode;
	public List<Category> categories;
	public List<Vendor> vendors;
	public List<Product> products;
	public List<Order> orders;
	public List<Bill> myBills;

	public void toProducts(MouseEvent event) {

		Stage productStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Products.fxml"));
			Scene scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			productStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void toVendors(MouseEvent event) {

		Stage vendorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Vendors.fxml"));
			Scene scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			vendorStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void toOrders(MouseEvent event) {
		Stage saleStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Sales.fxml"));
			Scene scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			saleStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void toReports(MouseEvent event) {
		Stage reportStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Reports.fxml"));
			Scene scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			reportStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getRow(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			Bill b = billTable.getSelectionModel().getSelectedItem();
			
			List<String> productNames = new ArrayList<>();
			List<Integer> productQuantities = new ArrayList<>();
			for (Order o : b.getProductsPurchased()) {
				productNames.add(o.getProductName());
				productQuantities.add(o.getQuantitySold());
			}
			productList.getItems().setAll(productNames);
			quantityList.getItems().setAll(productQuantities);

		}
	}

	public void productSelected(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			quantityList.getSelectionModel().select(productList.getSelectionModel().getSelectedIndex());
			for (Product p : products) {
				if (p.getName().equals(productList.getSelectionModel().getSelectedItem())) {
					productName.setText(p.getName());
					categoryLabel.setText(p.getCategory().getName());
					quantityLabel.setText(Integer.toString(p.getQuantity()));
					costLabel.setText(Double.toString(p.getBuyingPrice()));
					sellingLabel.setText(Double.toString(p.getSellingPrice()));
					try {
						barcode.setImage(BarcodeGenerator.generate(p.getBarcode()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void quantitySelected(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			productList.getSelectionModel().select(quantityList.getSelectionModel().getSelectedIndex());
			productSelected(event);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			Statement statement = DB_Connection.connection.createStatement();
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

			products = new ArrayList<>();

			result = statement.executeQuery("SELECT * FROM product");
			while (result.next()) {
				int category = result.getInt("category");
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
				products.add(new Product(result.getInt("id"), name, selectedC, (double) result.getInt("cost_price"),
						(double) result.getInt("selling_price"), selectedV, result.getInt("quantity"),
						result.getString("barcode")));
			}

			orders = new ArrayList<>();
			result = statement.executeQuery("SELECT * FROM inventory_management_system.order");
			while (result.next()) {
				orders.add(new Order(result.getInt("id"), result.getString("name"), result.getString("barcode"),
						result.getInt("quantity_sold"), result.getDouble("total_price")));
			}
			
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			myBills = new ArrayList<>();
			result = statement.executeQuery("SELECT * FROM order_list");
			while (result.next()) {
				
				JSONArray arr = new JSONArray(result.getString("orders"));
				List<Integer> orderIDs = new ArrayList<>();
				Vector<Order> productsPurchased = new Vector<>();
				arr.forEach(i -> {
					orderIDs.add((int)i);
				});
				orderIDs.forEach(id->{
					orders.forEach(order->{
						if(order.getID()==id) {
							productsPurchased.add(order);
						}
					});
					
				});
				
				try {
					myBills.add(new Bill(result.getInt("id"), productsPurchased, format1.parse(result.getString("date")), result.getInt("total_price"),
							result.getInt("total_quantity")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			bills = FXCollections.observableArrayList();
			for(Bill b : myBills) {
				bills.add(b);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

//		Order o = new Order(1, 1, "Coke","",8 2);
		Vector<Order> orders = new Vector<>();
//		for (int i = 0; i < 5; i++) {
//			orders.add(o);
//		}

//		for (Bill b : bills) {
//			b.setProductsPurchased(orders);
//			b.setTotalQuantity(b.getProductsPurchased().size());
//		}
		SimpleDateFormat format2 = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a");
		idColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("ID"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("TotalQuantity"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("TotalCost"));
		dateColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(format2.format(cellData.getValue().getDate())));
		billTable.setItems(bills);

//		products = new ArrayList<>();
//		products.add(new Product(1, "Coke", new Category(1, "Beverage"), 20d, 30d, new Vendor(1, "Coca-Cola"), 300,
//				"204458274890"));
//		products.add(new Product(2, "Apple", new Category(2, "Fruits"), 8d, 15d, new Vendor(2, "Fruitella"), 30,
//				"480467434460"));

		int itemCounter = 0;

		for (Bill b : bills) {
			for (Order ord : b.getProductsPurchased()) {
				itemCounter++;
			}
		}

		if (itemCounter == 1) {
			totalItems.setText("1 item");
		} else {
			totalItems.setText(itemCounter + " items");
		}
		if (bills.size() == 1) {
			totalBills.setText("1 bills");
		} else {
			totalBills.setText(bills.size() + " bills");
		}

	}

}
