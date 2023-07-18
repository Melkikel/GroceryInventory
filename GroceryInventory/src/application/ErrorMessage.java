package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorMessage {
	
	public static void display(String title, String message) {
		Stage Error = new Stage();
		Error.initModality(Modality.APPLICATION_MODAL);
		Error.setTitle(title);
		Error.resizableProperty().setValue(Boolean.FALSE);
		Error.setMinWidth(300);
		Error.setMaxWidth(300);
		Error.setMinHeight(150);
		Error.setMaxHeight(150);
		
		Label prompt = new Label(message);
		Button ok = new Button("OK");
		ok.setOnAction(e->Error.close());
		VBox layout = new VBox(4);
		layout.getChildren().addAll(prompt, ok);
		layout.setAlignment(Pos.CENTER);
		Scene dialog = new Scene(layout);
		Error.setScene(dialog);
		Error.showAndWait();
	}

}
