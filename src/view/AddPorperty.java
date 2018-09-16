package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddPorperty extends Application {

	public void start(Stage primaryStage) {
		primaryStage.setTitle("VBox Test");

		// VBox
		VBox vb = new VBox();
		vb.setPadding(new Insets(10, 50, 50, 50));
		vb.setSpacing(10);

		// Buttons
		Button btn1 = new Button();
		btn1.setText("Button1");
		vb.getChildren().add(btn1);

		Button btn2 = new Button();
		btn2.setText("Button2");
		vb.getChildren().add(btn2);

		Button btn3 = new Button();
		btn3.setText("Button3");
		vb.getChildren().add(btn3);

		Button btn4 = new Button();
		btn4.setText("Button4");
		vb.getChildren().add(btn4);

		// Adding VBox to the scene
		Scene scene = new Scene(vb);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
