package application;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ApplicationController implements Initializable {
	public TextField emailBox;
	public TextField passwordBox;
	public Button loginButton;

	public void login(ActionEvent event) {

		MessageDigest digest;
		String password = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(passwordBox.getText().getBytes(StandardCharsets.UTF_8));

			StringBuilder hexString = new StringBuilder(2 * hash.length);
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			password = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		try {
			Statement statement = DB_Connection.connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM user");
			String boxEmail = null;
			String boxPassword = null;

			boolean userFound = false;

			while (result.next()) {
				boxEmail = result.getString("email");
				boxPassword = result.getString("password");
				if (!(emailBox.getText().equals(boxEmail) && password.equals(boxPassword))) {
					userFound = false;
				} else {

					userFound = true;
					break;

				}
			}

			if (userFound) {
				Stage productStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

				try {
					BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Products.fxml"));
					Scene scene = new Scene(root, 1280, 720);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					productStage.setScene(scene);

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				ErrorMessage.display("Invalid User", "The user you entered is invalid");
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void handleEnterLogin(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			loginButton.fire();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DB_Connection.open();
	}
}
