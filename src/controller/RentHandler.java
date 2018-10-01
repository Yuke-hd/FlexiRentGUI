package controller;

import customException.DateTimeFormatException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Property;
import view.Popups;
import view.ReturnProperty;
import view.ShowProperty;

public class RentHandler implements EventHandler<ActionEvent> {

	Stage stage;
	Scene scene;
	ScrollPane sc;
	VBox vb;
	GridPane pane;
	Button obj;
	ShowProperty show;
	int buttonNum;

	public RentHandler(ShowProperty show) {
		this.show = show;
		//stage.initModality(Modality.APPLICATION_MODAL);
		stage = new Stage(StageStyle.DECORATED);
		stage.setResizable(false);
		stage.setTitle("Rent Property");
//		stage.setHeight(600);
//		stage.setWidth(420);
		
	}

	@Override
	public void handle(ActionEvent event) {
		obj = (Button) event.getSource();
		buttonNum = (int) obj.getUserData();

		ScrollPane sPane=paneItem();
		scene = new Scene(sPane, 400, 600);
		System.out.println(scene.getWidth()+","+scene.getHeight());
		stage.setScene(scene);
		System.out.println(scene.getWidth()+","+scene.getHeight());
		stage.show();
		System.out.println(scene.getWidth()+","+scene.getHeight()+","+stage.getWidth()+","+stage.getHeight());
		
	}

	private ScrollPane paneItem() {
		sc = new ScrollPane();
//		sc.prefWidthProperty().bind(stage.widthProperty());
//		sc.prefHeightProperty().bind(stage.heightProperty());
		vb = new VBox();
		vb.setAlignment(Pos.TOP_CENTER);
		vb.setSpacing(30);
		Property prop = show.admin.getPropList().get(buttonNum);
		ImageView image = Utility.drawImg(prop.getImgPath(), 400);
		Text records = new Text(prop.getDetails());
		HBox buttonRow = new HBox(30);
		buttonRow.setAlignment(Pos.CENTER);
		Button rentButton = new Button("rent");
		Button retuenButton = new Button("return");
		Button mntButton = new Button();

		pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		// pane.setPadding(new Insets(150, 50, 50, 150));
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

		pane.add(new Label(prop.getPropId()), 1, 0);
		pane.add(new TextField(), 1, 1);
		pane.add(new TextField(), 1, 2);
		pane.add(new TextField(), 1, 3);
		//pane.add(rentButton, 1, 4);

		if (show.admin.getPropList().get(buttonNum).getStat()) {
			rentButton.setDisable(true);
			mntButton.setText("Complete maintenance");
			mntButton.setOnAction(f -> System.out.println(completeMnt()));
			System.out.println("disabled");
		}else {
			rentButton.setDisable(false);
			mntButton.setText("Maintenance");
			mntButton.setOnAction(f -> mnt());
			System.out.println("enabled");
		}

		retuenButton.setDisable(!rentButton.isDisabled());
		retuenButton.setOnAction(t -> returnButtonAction());
		rentButton.setOnAction(t -> rentButtonAction());
		buttonRow.getChildren().addAll(mntButton,rentButton,retuenButton);

		vb.getChildren().add(image);
		vb.getChildren().add(pane);
		vb.getChildren().add(buttonRow);
		vb.getChildren().add(records);
		sc.setContent(vb);
		return sc;
	}

	private void mnt() {
		String string;
		HBox buttonRow = (HBox) vb.getChildren().get(2);
		Button mntButton = (Button) buttonRow.getChildren().get(0);
		mntButton.setText("Complete maintenance");
		buttonRow.getChildren().get(1).setDisable(true);
		show.admin.performMaintenance(buttonNum);
		mntButton.setOnAction(t -> {
			System.out.println(completeMnt());
//			mntButton.setText("Maintenance");
//			mntButton.setOnAction(e -> mnt());
//			buttonRow.getChildren().get(1).setDisable(false);
			stage.setScene(new Scene(paneItem(),400,600));
			//scene=new Scene(paneItem(),400, 600);
		});

	}

	private String completeMnt(){
		Stage mStage = new Stage(StageStyle.DECORATED);
		VBox vb = new VBox(20);
		
		vb.setAlignment(Pos.CENTER);
		TextField textField = new TextField();
		DatePicker dCell = new DatePicker();
		Text text = new Text();
		Button complete = new Button("complete");
		complete.setOnAction(t -> {
			mStage.close();
		});
		vb.getChildren().addAll(textField,dCell,complete);
		Scene s = new Scene(vb, 200, 200);

		mStage.setScene(s);
		mStage.setOnCloseRequest(e -> e.consume());
		mStage.showAndWait();

		String completeDate;
		try {
			completeDate = dCell.getValue().toString();
			completeDate = Utility.reverseDate(completeDate).toString();
			show.admin.completeMaintenance(buttonNum,completeDate);
			return dCell.getValue().toString();
		} catch (NullPointerException e1) {
			new DateTimeFormatException().errorPopup();
			return "error";
		}
	}

	private void rentButtonAction() {
		String custID = ((TextField) pane.getChildren().get(5)).getText();
		String rentDate = ((TextField) pane.getChildren().get(6)).getText();
		int days;
		if (custID.equals("")) {
			Popups.alert("Please enter the customer id");
		} else {
			try {
				days = Integer.parseInt(((TextField) pane.getChildren().get(7)).getText());
				boolean stat = show.admin.rentProp(buttonNum, custID, rentDate, days);
				if (stat) {
					stage.close();
					Popups.info("success!");
				}
			} catch (NumberFormatException e) {
				Popups.alert("days must be a number");
			}
		}
	}

	private void returnButtonAction(){
		Stage mStage = new Stage(StageStyle.DECORATED);
		VBox vb = new VBox(20);
		
		vb.setAlignment(Pos.CENTER);
		DatePicker dCell = new DatePicker();
		Text text = new Text();
		Button retur = new Button("return");
		retur.setOnAction(t -> {
			mStage.close();
			show.admin.returnProp(show.admin.getPropList().get(buttonNum).getPropId(), Utility.reverseDate(dCell.getValue().toString()).toString());
			dCell.getValue();
		});
		vb.getChildren().addAll(dCell,retur);
		Scene s = new Scene(vb, 200, 200);

		mStage.setScene(s);
		mStage.showAndWait();
	}
}
