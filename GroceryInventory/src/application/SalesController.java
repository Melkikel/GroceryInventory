package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.json.JSONArray;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SalesController implements Initializable {
	public ObservableList<Order> sales;
	public List<Product> products;
	public TableView<Order> saleTable;
	public TableColumn<Order, String> barcodeColumn;
	public TableColumn<Order, String> productColumn;
	public TableColumn<Order, String> quantityColumn;
	public TableColumn<Order, String> priceColumn;
	public Label subtotalLabel;
	public Label taxLabel;
	public Label totalLabel;
	public TextField searchBox;
	public List<Order> orders;
	public Map<Integer, List<Order>> productSales;
	public List<Category> categories;
	public List<Vendor> vendors;
	public int tax = 10;

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

	public void toBills(MouseEvent event) {
		Stage billStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Bills.fxml"));
			Scene scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			billStage.setScene(scene);

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

	public void addButtonClick() {
		if (products.size() > 0) {

			for (Product p : products) {
				if (!searchBox.getText().isBlank()
						&& (p.getBarcode().equals(searchBox.getText()) || p.getName().equals(searchBox.getText()))) {
					Stage addStage = new Stage();
					addStage.initModality(Modality.APPLICATION_MODAL);
					addStage.setTitle("Add To Cart");
					addStage.resizableProperty().setValue(Boolean.FALSE);
					addStage.setMinWidth(600);
					addStage.setMaxWidth(600);
					addStage.setMinHeight(400);
					addStage.setMaxHeight(400);

					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("AddToCart.fxml"));
						VBox root = (VBox) loader.load();

						AddToCartController atcc = loader.getController();
						atcc.barcodeLabel.setText(p.getBarcode());
						atcc.nameLabel.setText(p.getName());
						atcc.categoryLabel.setText(p.getCategory().getName());
						atcc.priceLabel.setText(Double.toString(p.getSellingPrice()));
						atcc.vendorLabel.setText(p.getVendor().getName());
						atcc.quantityLabel.setText(Integer.toString(p.getQuantity()));
						atcc.nameLabel.setUserData(p);

						Scene scene = new Scene(root, 600, 400);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						addStage.setScene(scene);
						addStage.showAndWait();

//						orders.add(new Order(p.getName(), p.getBarcode(), atcc.returnQuantity(),
//								p.getSellingPrice() * atcc.returnQuantity()));
						orders.add(atcc.returnOrder());
						productSales.put(p.getID(), (new ArrayList<>()));
						productSales.get(p.getID()).add(new Order(p.getID(), p.getName(), p.getBarcode(),
								atcc.returnQuantity(), p.getSellingPrice() * atcc.returnQuantity()));

						sales = FXCollections.observableArrayList();
						for (Order o : orders) {
							sales.add(o);
							System.out.println(o.getProductName());
						}
						barcodeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("ProductBarcode"));
						productColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("ProductName"));
						quantityColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("QuantitySold"));
						priceColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("TotalPrice"));
						saleTable.setItems(sales);
						taxLabel.setText(Integer.toString(tax));
						calculateTotal();

					} catch (Exception e) {
						e.printStackTrace();
					}
					calculateTotal();
				}
			}
		}
	}

	public void handleSearchEnter(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			this.addButtonClick();
		}
	}

	public void removeButtonClick() {
		Order selectedOrder = saleTable.getSelectionModel().getSelectedItem();

		try {
			Statement statement = DB_Connection.connection.createStatement();
			String query = String.format("DELETE FROM inventory_management_system.order WHERE (id = %d);",
					selectedOrder.getID());
			statement.executeUpdate(query);
			updateTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		calculateTotal();
	}

	public void makeOrderButtonClick() {
		List<Integer> ordersIDList = new ArrayList<>();
		Statement statement;
		int total_quantity = 0;
		double total_price = 0;
		for (Order o : orders) {
			ordersIDList.add(o.getID());
			total_quantity += o.getQuantitySold();
			total_price += o.getTotalPrice();
		}
		total_price += tax;
		System.out.println(total_price);
		try {
			statement = DB_Connection.connection.createStatement();
//			ResultSet result = statement.executeQuery("SELECT * FROM inventory_management_system.order");
//			while (result.next()) {
//				int id = result.getInt("id");
//				int quantity_sold = result.getInt("quantity_sold");
//				double totalPrice = result.getDouble("total_price");
//				ordersIDList.add(id);
//				orders.add(new Order(result.getInt("id"), result.getString("name"), result.getString("barcode"),
//						result.getInt("quantity_sold"), result.getDouble("total_price")));

//			}
			JSONArray orderJSON = new JSONArray(ordersIDList);

			String query = String.format(
					"INSERT INTO order_list (orders, total_quantity,total_price, date) VALUES ('%s', %d, %.2f, '%s')",
					orderJSON, total_quantity, total_price + tax, LocalDateTime.now());
			statement.executeUpdate(query);

			ErrorMessage.display("Order Placed", "Order has been placed successfully");

			
			
			sales = FXCollections.observableArrayList();
			saleTable.setItems(sales);
			orders = new ArrayList<>();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void calculateTotal() {
		double priceCounter = 0;

		for (Order o : sales) {
			priceCounter += o.getTotalPrice();
		}

		subtotalLabel.setText(Double.toString(priceCounter));
		totalLabel.setText(Double.toString(priceCounter + 10));
	}

	public void updateTable() {
		Statement statement;
		try {
			statement = DB_Connection.connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM supplier");
			vendors = new ArrayList<>();

			while (result.next()) {
				vendors.add(new Vendor(result.getInt("id"), result.getString("name"), result.getString("phone"),
						result.getString("email")));
			}

//			orders = new ArrayList<>();
//			result = statement.executeQuery("SELECT * FROM inventory_management_system.order");
//			while (result.next()) {
//
//				int vendorID = result.getInt("supplier");
//				for (Vendor v : vendors) {
//					if (v.getID() == vendorID) {
//						orders.add(new Order(result.getInt("id"), result.getString("name"), result.getString("barcode"),
//								result.getInt("quantity_sold"), result.getDouble("total_price"), v));
//					}
//				}
//
//			}

			result = statement.executeQuery("SELECT * FROM category");
			categories = new ArrayList<>();

			while (result.next()) {
				categories.add(new Category(result.getInt("id"), result.getString("name")));
			}

			result = statement.executeQuery("SELECT * FROM product");
			products = new ArrayList<>();

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

		} catch (SQLException e) {
			e.printStackTrace();
		}

		sales = FXCollections.observableArrayList();
//		for (Order o : orders) {
//			sales.add(o);
//		}

//		barcodeColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("ProductBarcode"));
//		productColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("ProductName"));
//		quantityColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("QuantitySold"));
//		priceColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("TotalPrice"));
//		saleTable.setItems(sales);
//		taxLabel.setText(Integer.toString(tax));
//		calculateTotal();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		updateTable();
		orders = new ArrayList<>();
		productSales = new HashMap<>();

	}
}
