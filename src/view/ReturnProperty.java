package view;

import java.util.ArrayList;

import controller.FlexiRentSystem;
import customException.DateTimeFormatException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Property;

public class ReturnProperty implements EventHandler<ActionEvent> {

	Stage stage = new Stage();
	int buttonNum;
FlexiRentSystem admin = new FlexiRentSystem();
	ArrayList<Property> propList = new ArrayList<>();

	public ReturnProperty(int buttonNum) {
		this.buttonNum=buttonNum;

	}

	@Override
	public void handle(ActionEvent event) {
		Scene scene = new Scene(paneItem(), 400, 600);

		stage.setTitle("Return Property");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}

	private GridPane paneItem() {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		// pane.setPadding(new Insets(150, 50, 50, 150));
		pane.setHgap(10);
		pane.setVgap(6);
		pane.getColumnConstraints().add(new ColumnConstraints(150));
		pane.getColumnConstraints().add(new ColumnConstraints(150));

		Text q = new Text("qqq");

		// right column labels
		pane.add(new Label("Property ID:"), 0, 0);
		pane.add(new Label("Return Date:"), 0, 1);

		// left column textfields
		ComboBox<String> cm = new ComboBox<>();
		cm.setPromptText("Select property");
		ArrayList<Integer> a = getRentedProp();
		for (int i = 0; i < a.size(); i++) {
			int j = a.get(i);
			cm.getItems().add(propList.get(j).getPropId());
		}
		cm.setOnAction(t -> q.setText(propList.get(getRecord(cm.getValue())).getDetails()));

		Button returnButton = new Button();
		returnButton.setOnAction(new ReturnHandler(pane, admin));

		pane.add(cm, 1, 0);
		pane.add(new TextField(), 1, 1);
		pane.add(returnButton, 1, 2);

		pane.add(q, 0, 4);

		return pane;
	}

	private ArrayList<Integer> getRentedProp() {
		ArrayList<Integer> propNumList = new ArrayList<>();
		for (int i = 0; i < propList.size(); i++) {
			if (propList.get(i).getStat()) {
				propNumList.add(i);
			}
		}
		for (int j = 0; j < propNumList.size(); j++) {
			System.out.println(propNumList.get(j));
		}
		return propNumList;
	}

	private int getRecord(String id) {
		int num = 0;
		for (int i = 0; i < propList.size(); i++) {
			if (id.equals(propList.get(i).getPropId())) {
				num = i;
			}
		}
		return num;
	}

	private void returnProp() {

	}
}

class ReturnHandler implements EventHandler<ActionEvent> {
	GridPane gp = new GridPane();
	FlexiRentSystem admin = new FlexiRentSystem();

	public ReturnHandler(GridPane gp, FlexiRentSystem admin) {
		this.gp = gp;
		this.admin = admin;
	}

	@Override
	public void handle(ActionEvent event) {
		ComboBox<String> cBox = ((ComboBox) gp.getChildren().get(2));
		String date = ((TextField) gp.getChildren().get(3)).getText();

		try {
			admin.returnProp(cBox.getValue(), date);
		} catch (Exception e) {
			Popups.alert("nothing to return");
		}

	}
}
