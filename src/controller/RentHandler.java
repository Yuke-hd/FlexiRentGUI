package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Property;
import view.ShowProperty;

public class RentHandler implements EventHandler<ActionEvent> {

	Button obj;
	ShowProperty show;
	int buttonNum;

	
	public RentHandler(ShowProperty show) {
		this.show=show;
	}
	
	@Override
	public void handle(ActionEvent event) {
		obj = (Button) event.getSource();
		buttonNum = (int) obj.getUserData();
		
		GridPane root = new GridPane();
		Scene scene = new Scene(paneItem(),400,800);
		Stage stage = new Stage();
		
		stage.setTitle("Rent Property");
		stage.setScene(scene);
		stage.show();
		
	}

	private VBox paneItem() {
		VBox vb = new VBox();vb.setAlignment(Pos.TOP_CENTER);
		HBox hb = new HBox();
		Property prop = show.admin.getPropList().get(buttonNum);
		ImageView image= Utility.drawImg(prop.getImgPath(), 400);
		Text records = new Text(prop.getDetails());
		
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		//pane.setPadding(new Insets(150, 50, 50, 150));
		pane.setHgap(10);
		pane.setVgap(6);
		pane.getColumnConstraints().add(new ColumnConstraints(150));
		pane.getColumnConstraints().add(new ColumnConstraints(150));

		// right column labels
		pane.add(new Label("Property ID:"), 0, 0);
		pane.add(new Label("Customer ID:"), 0, 1);
		pane.add(new Label("Rent Date:"), 0, 2);
		pane.add(new Label("Day:"), 0, 3);

		// left column textfields
		Text a = new Text();
		//pane.add(new TextField( (String) ((Button) obj).getUserData() ), 1, 0);
		pane.add(new Label(show.admin.getPropList().get(buttonNum).getPropId()), 1, 0);
		pane.add(new TextField(), 1, 1);
		pane.add(new TextField(), 1, 2);
		pane.add(new TextField(), 1, 3);
		vb.getChildren().add(image);
		vb.getChildren().add(pane);
		vb.getChildren().add(records);
		return vb;
	}
}
