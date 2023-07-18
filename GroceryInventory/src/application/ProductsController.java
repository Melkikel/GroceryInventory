package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import application.dataStructures.ArrayStack;
import application.dataStructures.CircularArrayQueue;
import application.dataStructures.UnorderedArrayList;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductsController implements Initializable {
	public Circle productsCircle;
	public FontIcon productsIcon;
	public Circle vendorsCircle;
	public FontIcon vendorssIcon;
	public Circle billsCircle;
	public FontIcon billsIcon;
	public Circle ordersCircle;
	public FontIcon orderssIcon;
	public TableView<Product> productTable;
	public TableColumn<Product, String> idColumn;
	public TableColumn<Product, String> productColumn;
	public TableColumn<Product, String> categoryColumn;
	public TableColumn<Product, String> priceColumn;
	public TableColumn<Product, String> quantityColumn;
	public Label totalProducts;
	public Label totalItems;
	public Label productName;
	public Label categoryLabel;
	public Label quantityLabel;
	public Label costLabel;
	public Label sellingLabel;
	public ImageView barcode;
	public TextField searchBox;
	public ObservableList<Product> data;
	
	
	public ArrayStack<Product> products1to4;
	public CircularArrayQueue<Product> products5to7;
	public UnorderedArrayList<Product> products8to11;
	
	
	
	public List<Vendor> vendors;
	public List<Category> categories;

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
		addStage.setTitle("Add Product");
		addStage.resizableProperty().setValue(Boolean.FALSE);
		addStage.setMinWidth(600);
		addStage.setMaxWidth(600);
		addStage.setMinHeight(400);
		addStage.setMaxHeight(400);

		try {
//			VBox root = (VBox) FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
			VBox root = (VBox) loader.load();

			AddProductController apc = loader.getController();

			List<String> categoryNames = new ArrayList<>();
			for (Category c : categories) {
				categoryNames.add(c.getName());
			}
			List<String> vendorNames = new ArrayList<>();
			for (Vendor v : vendors) {
				vendorNames.add(v.getName());
			}

			apc.categoryBox.getItems().setAll(categoryNames);
			apc.vendorBox.getItems().setAll(vendorNames);
			
			apc.nameBox.setUserData(products1to4);
			apc.categoryBox.setUserData(products5to7);
			apc.quantityBox.setUserData(products8to11);

			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			addStage.setScene(scene);
			addStage.showAndWait();
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void edit() {
		if (productTable.getSelectionModel().getSelectedItems().size() > 0) {

			Stage addStage = new Stage();
			addStage.initModality(Modality.APPLICATION_MODAL);
			addStage.setTitle("Edit Product");
			addStage.resizableProperty().setValue(Boolean.FALSE);
			addStage.setMinWidth(600);
			addStage.setMaxWidth(600);
			addStage.setMinHeight(400);
			addStage.setMaxHeight(400);

			try {

//				VBox root = (VBox) FXMLLoader.load(getClass().getResource("EditProduct.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProduct.fxml"));
				VBox root = (VBox) loader.load();

				EditProductController epc = loader.getController();
				Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
				Category selectedCategory = null;
				Vendor selectedVendor = null;

				List<String> categoryNames = new ArrayList<>();
				for (Category c : categories) {
					categoryNames.add(c.getName());
					if (selectedProduct.getCategory().getName().equals(c.getName())) {
						selectedCategory = c;
					}
				}
				List<String> vendorNames = new ArrayList<>();
				for (Vendor v : vendors) {
					vendorNames.add(v.getName());
					if (selectedProduct.getVendor().getName().equals(v.getName())) {
						selectedVendor = v;
					}
				}

				epc.categoryBox.getItems().setAll(categoryNames);
				epc.vendorBox.getItems().setAll(vendorNames);

				epc.categoryBox.getSelectionModel().select(selectedCategory.getName());
				epc.vendorBox.getSelectionModel().select(selectedVendor.getName());
				epc.nameBox.setText(selectedProduct.getName());
				epc.costPriceBox.setText(Double.toString(selectedProduct.getBuyingPrice()));
				epc.sellingPriceBox.setText(Double.toString(selectedProduct.getSellingPrice()));
				epc.quantityBox.setText(Integer.toString(selectedProduct.getQuantity()));
				epc.barcodeBox.setText(selectedProduct.getBarcode());
				epc.costPriceBox.setUserData(selectedProduct);
				
				epc.nameBox.setUserData(products1to4);
				epc.categoryBox.setUserData(products5to7);
				epc.quantityBox.setUserData(products8to11);

				Scene scene = new Scene(root, 600, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				addStage.setScene(scene);
				addStage.showAndWait();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void getRow(MouseEvent event) {
		if (event.isPrimaryButtonDown() && productTable.getSelectionModel().getSelectedItems().size() > 0) {
			Product p = productTable.getSelectionModel().getSelectedItem();
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

	public void findButtonClick() {
		data.forEach(p -> {
			if (p.getName().toLowerCase().contains(searchBox.getText().toLowerCase())) {
				productTable.getSelectionModel().select(p);
			}
		});
	}

	public void handleSearchEnter(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			this.findButtonClick();
		}
	}

	public void updateTable() {
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
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		data = FXCollections.observableArrayList();

		int size = products1to4.size();
		for (int i = 0; i < size; i++) {
			Product p = products1to4.pop();
			data.add(p);
		}
		size = products5to7.size();
		for (int i = 0; i < size; i++) {
			Product p = products5to7.dequeue();
			data.add(p);
		}
		size = products8to11.size();
		for (int i = 0; i < size; i++) {
			Product p = products8to11.removeFirst();
			data.add(p);
		}
		

		idColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("ID"));
		productColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
		categoryColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getName()));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("SellingPrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("Quantity"));
		productTable.setItems(data);

		int totalProductsCounter = 0;
		int totalItemsCounter = 0;
		for (Product p : data) {
			totalProductsCounter++;
			totalItemsCounter += p.getQuantity();
		}
		if (totalProductsCounter == 1) {
			totalProducts.setText("1 product");
		} else {
			totalProducts.setText(totalProductsCounter + " produts");
		}
		if (totalItemsCounter == 1) {
			totalItems.setText("1 item");
		} else {
			totalItems.setText(totalItemsCounter + " items");
		}
	}
	
	public ArrayStack<Product> getProducts1to4() {
		return products1to4;
	}
	
	public CircularArrayQueue<Product> getProducts5to7() {
		return products5to7;
	}
	public UnorderedArrayList<Product> getProducts8to11() {
		return products8to11;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.updateTable();

	}

}
