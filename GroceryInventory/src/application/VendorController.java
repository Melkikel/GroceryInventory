package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import application.dataStructures.ArrayStack;
import application.dataStructures.CircularArrayQueue;
import application.dataStructures.UnorderedArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VendorController implements Initializable {

	public ObservableList<Vendor> names;
	public ListView<String> vendorList;
	public ListView<String> productsList;
	public TextField searchBox;
	public Label totalVendors;
	public List<Vendor> vendors;
	public HashMap<Integer, Vendor> myVendors;
	public List<Category> categories;
	public ArrayStack<Product> products1to4;
	public CircularArrayQueue<Product> products5to7;
	public UnorderedArrayList<Product> products8to11;

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

	public void add() {
		Stage addStage = new Stage();
		addStage.initModality(Modality.APPLICATION_MODAL);
		addStage.setTitle("Add Category");
		addStage.resizableProperty().setValue(Boolean.FALSE);
		addStage.setMinWidth(600);
		addStage.setMaxWidth(600);
		addStage.setMinHeight(400);
		addStage.setMaxHeight(400);

		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("AddVendor.fxml"));
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			addStage.setScene(scene);
			addStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit() {
		if (vendorList.getSelectionModel().getSelectedItems().size() > 0) {

			Stage addStage = new Stage();
			addStage.initModality(Modality.APPLICATION_MODAL);
			addStage.setTitle("Edit Category");
			addStage.resizableProperty().setValue(Boolean.FALSE);
			addStage.setMinWidth(600);
			addStage.setMaxWidth(600);
			addStage.setMinHeight(400);
			addStage.setMaxHeight(400);

			try {
//				VBox root = (VBox) FXMLLoader.load(getClass().getResource("EditVendor.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("EditVendor.fxml"));
				VBox root = (VBox) loader.load();

				EditVendorController evc = loader.getController();

				Vendor selectedVendor = null;
				for (Vendor v : vendors) {
					if (v.getName().equals(vendorList.getSelectionModel().getSelectedItem())) {
						selectedVendor = v;
					}
				}
				evc.nameBox.setText(selectedVendor.getName());
				evc.phoneBox.setText(selectedVendor.getPhone());
				evc.emailBox.setText(selectedVendor.getEmail());
				evc.nameBox.setUserData(selectedVendor);

				Scene scene = new Scene(root, 600, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				addStage.setScene(scene);
				addStage.showAndWait();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void getVendor(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			String vendorName = vendorList.getSelectionModel().getSelectedItem();
			List<String> products = new LinkedList<>();

			names.forEach(ve -> {
				if (ve.getName().equals(vendorName)) {
					ve.getProductsOffered().forEach(p -> {
						products.add(p.getName());
					});
				}
			});

			productsList.getItems().setAll(products);
		}
	}

	public void findButtonClick() {
		names.forEach(ve -> {
			if (ve.getName().toLowerCase().contains(searchBox.getText().toLowerCase())) {
				vendorList.getSelectionModel().select(ve.getName());
			}
		});
	}

	public void handleSearchEnter(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			this.findButtonClick();
		}
	}

	public void updateList() {
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

			products1to4 = new ArrayStack<>();
			products5to7 = new CircularArrayQueue<>();
			products8to11 = new UnorderedArrayList<>();

			result = statement.executeQuery("SELECT * FROM product");
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
				}else if(category == 5 || category == 6 || category == 7) {
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
				} else if(category == 8 || category == 9 || category == 10 || category == 11) {
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
			names = FXCollections.observableArrayList();
//			for (Vendor v : vendors) {
//				Vector<Product> vendorProducts = new Vector<>();
//				int size = products1to4.size();
//				System.out.println(size);
//				for (int i = 0; i <= size; i++) {
//					Product current = products1to4.pop();
//					if (current.getVendor().getID() == v.getID()) {
//						vendorProducts.add(current);
//					}
//				}
//				v.setProductsOffered(vendorProducts);
//				names.add(v);
//			}
			
			int size = products1to4.size();
			for(int i = 0; i < size; i++) {
				Product current = products1to4.pop();
				for(Vendor v : vendors) {
					if(current.getVendor().getID()==v.getID()) {
						v.getProductsOffered().add(current);
					}
					if(!names.contains(v)) {
						names.add(v);
					}
				}
			}
			size = products5to7.size();
			for(int i = 0; i < size; i++) {
				Product current = products5to7.dequeue();
				for(Vendor v : vendors) {
					if(current.getVendor().getID()==v.getID()) {
						v.getProductsOffered().add(current);
					}
					if(!names.contains(v)) {
						names.add(v);
					}
				}
			}
			size = products8to11.size();
			for(int i = 0; i < size; i++) {
				Product current = products8to11.removeLast();
				for(Vendor v : vendors) {
					if(current.getVendor().getID()==v.getID()) {
						v.getProductsOffered().add(current);
					}
					if(!names.contains(v)) {
						names.add(v);
					}
				}
			}
			

			List<String> nameList = new LinkedList<>();
			names.forEach(v -> {
				nameList.add(v.getName());
			});
			vendorList.getItems().setAll(nameList);
			if (names.size() == 1) {
				totalVendors.setText("1 vendor");
			} else {
				totalVendors.setText(names.size() + " vendors");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.updateList();
	}
}
