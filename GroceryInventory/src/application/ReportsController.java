package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.dataStructures.ArrayStack;
import application.dataStructures.CircularArrayQueue;
import application.dataStructures.UnorderedArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReportsController implements Initializable {

	public Label stack;
	public Label queue;
	public Label list;
	public ArrayStack<Product> products1to4;
	public CircularArrayQueue<Product> products5to7;
	public UnorderedArrayList<Product> products8to11;

	public List<Vendor> vendors;
	public List<Category> categories;
	public Product selectedProduct;

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

	public void pushButtonClick() {

		Stage pushStage = new Stage();
		pushStage.initModality(Modality.APPLICATION_MODAL);
		pushStage.setTitle("Push Product");
		pushStage.resizableProperty().setValue(Boolean.FALSE);
		pushStage.setMinWidth(600);
		pushStage.setMaxWidth(600);
		pushStage.setMinHeight(400);
		pushStage.setMaxHeight(400);

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PushProduct.fxml"));
			VBox root = (VBox) loader.load();

			PushProductController ppc = loader.getController();

			List<String> categoryNames = new ArrayList<>();
			for (Category c : categories) {
				if (c.getID() == 1 || c.getID() == 2 || c.getID() == 3 || c.getID() == 4) {
					categoryNames.add(c.getName());
				}
			}
			List<String> vendorNames = new ArrayList<>();
			for (Vendor v : vendors) {
				vendorNames.add(v.getName());
			}

			ppc.categoryBox.getItems().setAll(categoryNames);
			ppc.vendorBox.getItems().setAll(vendorNames);

			ppc.nameBox.setUserData(products1to4);

			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			pushStage.setScene(scene);
			pushStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void popButtonClick(ActionEvent event) {
		selectedProduct = products1to4.pop();

		try {
			Statement statement = DB_Connection.connection.createStatement();
			String query = String.format("DELETE FROM product WHERE (id = %d);", selectedProduct.getID());
			statement.executeUpdate(query);
			update();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void enqueueButtonClick(ActionEvent event) {
		Stage enqueueStage = new Stage();
		enqueueStage.initModality(Modality.APPLICATION_MODAL);
		enqueueStage.setTitle("Push Product");
		enqueueStage.resizableProperty().setValue(Boolean.FALSE);
		enqueueStage.setMinWidth(600);
		enqueueStage.setMaxWidth(600);
		enqueueStage.setMinHeight(400);
		enqueueStage.setMaxHeight(400);

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EnqueueProduct.fxml"));
			VBox root = (VBox) loader.load();

			EnqueueProductController epc = loader.getController();

			List<String> categoryNames = new ArrayList<>();
			for (Category c : categories) {
				if (c.getID() == 5 || c.getID() == 6 || c.getID() == 7) {
					categoryNames.add(c.getName());
				}
			}
			List<String> vendorNames = new ArrayList<>();
			for (Vendor v : vendors) {
				vendorNames.add(v.getName());
			}

			epc.categoryBox.getItems().setAll(categoryNames);
			epc.vendorBox.getItems().setAll(vendorNames);

			epc.nameBox.setUserData(products5to7);

			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			enqueueStage.setScene(scene);
			enqueueStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dequeueButtonClick(ActionEvent event) {
		selectedProduct = products5to7.dequeue();

		try {
			Statement statement = DB_Connection.connection.createStatement();
			String query = String.format("DELETE FROM product WHERE (id = %d);", selectedProduct.getID());
			statement.executeUpdate(query);
			update();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addButtonClick(ActionEvent event) {
		Stage addStage = new Stage();
		addStage.initModality(Modality.APPLICATION_MODAL);
		addStage.setTitle("Push Product");
		addStage.resizableProperty().setValue(Boolean.FALSE);
		addStage.setMinWidth(600);
		addStage.setMaxWidth(600);
		addStage.setMinHeight(400);
		addStage.setMaxHeight(400);

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct2.fxml"));
			VBox root = (VBox) loader.load();

			AddProductController2 apc2 = loader.getController();

			List<String> categoryNames = new ArrayList<>();
			for (Category c : categories) {
				if (c.getID() == 8 || c.getID() == 9 || c.getID() == 10 || c.getID() == 11) {
					categoryNames.add(c.getName());
				}
			}
			List<String> vendorNames = new ArrayList<>();
			for (Vendor v : vendors) {
				vendorNames.add(v.getName());
			}

			apc2.categoryBox.getItems().setAll(categoryNames);
			apc2.vendorBox.getItems().setAll(vendorNames);

			apc2.nameBox.setUserData(products8to11);

			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			addStage.setScene(scene);
			addStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeButtonClick() {
		Stage removeStage = new Stage();
		removeStage.initModality(Modality.APPLICATION_MODAL);
		removeStage.setTitle("Push Product");
		removeStage.resizableProperty().setValue(Boolean.FALSE);
		removeStage.setMinWidth(600);
		removeStage.setMaxWidth(600);
		removeStage.setMinHeight(400);
		removeStage.setMaxHeight(400);

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RemoveProduct.fxml"));
			VBox root = (VBox) loader.load();

			RemoveProductController rpc = loader.getController();
			
			rpc.idBox.setUserData(products8to11);
			
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			removeStage.setScene(scene);
			removeStage.showAndWait();
			
			
			try {
				Statement statement = DB_Connection.connection.createStatement();
				String query = String.format("DELETE FROM product WHERE (id = %d);", Integer.parseInt(rpc.idBox.getText()));
				statement.executeUpdate(query);
				update();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void removeLastButtonClick(ActionEvent event) {
		selectedProduct = products8to11.removeLast();

		try {
			Statement statement = DB_Connection.connection.createStatement();
			String query = String.format("DELETE FROM product WHERE (id = %d);", selectedProduct.getID());
			statement.executeUpdate(query);
			update();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeFirstButtonClick(ActionEvent event) {
		selectedProduct = products8to11.removeFirst();

		try {
			Statement statement = DB_Connection.connection.createStatement();
			String query = String.format("DELETE FROM product WHERE (id = %d);", selectedProduct.getID());
			statement.executeUpdate(query);
			update();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update() {
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

			String productStack = "";
			String productQueue = "";
			String productList = "";

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

					String temp = result.getInt("id") + ". " + name + "\n" + productStack;
					productStack = temp;

				} else if (category == 5 || category == 6 || category == 7) {
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

					String temp = result.getInt("id") + ". " + name + "\n" + productQueue;
					productQueue = temp;

				} else if (category == 8 || category == 9 || category == 10 || category == 11) {
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

					String temp = result.getInt("id") + ". " + name + "\n" + productList;
					productList = temp;

				}
				stack.setText(productStack);
				queue.setText(productQueue);
				list.setText(productList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		update();
	}
}
