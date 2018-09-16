package view;

import java.awt.print.Pageable;
import java.io.File;
import java.io.IOException;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;

import com.sun.javafx.event.EventQueue;

import controller.SQL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Apartment;
import model.FlexiRentSystem;

public class Main extends Application {
	Pane contents = new Pane();

	FlexiRentSystem admin = new FlexiRentSystem();

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("JavaFX App");
			VBox menu = new VBox(addMenu());
			VBox content = new VBox(contents);
			VBox root = new VBox(menu, content);
			Scene scene = new Scene(root, 600, 600);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private MenuBar addMenu() {
		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("Menu 1");
		MenuItem menuItem1 = new MenuItem("Item 1");
		Menu menu2 = new Menu("Menu 2");
		MenuItem menuItem2_1 = new MenuItem("Item 2");
		menuBar.getMenus().add(menu1);
		menuBar.getMenus().add(menu2);
		menu1.getItems().add(menuItem1);
		menu2.getItems().add(menuItem2_1);
		menuItem1.setOnAction(new MenuItem1Handler());
		menuItem2_1.setOnAction(new MenuItem2_1Handler());
		return menuBar;
	}

	class MenuItem1Handler implements EventHandler<ActionEvent> {
		@Override // Override the handle method
		public void handle(ActionEvent e) {

			System.out.println("Menu Item 1 Selected");
			Pane container = contents;
			container.getChildren().clear();
			GridPane pane = new GridPane();
			pane.setPadding(new Insets(100, 50, 50, 50));
			pane.setHgap(10);
			pane.setVgap(6);
			pane.getColumnConstraints().add(new ColumnConstraints(150));
			pane.getColumnConstraints().add(new ColumnConstraints(150));

			pane.add(new Label("Property ID:"), 0, 0);
			pane.add(new Label("Street Number:"), 0, 1);
			pane.add(new Label("Street Name:"), 0, 2);
			pane.add(new Label("Suburb:"), 0, 3);
			pane.add(new Label("Bedroom number:"), 0, 4);

			Text a = new Text();
			pane.add(new TextField(), 1, 0);
			pane.add(new TextField(), 1, 1);
			pane.add(new TextField(), 1, 2);
			pane.add(new TextField(), 1, 3);
			pane.add(new TextField(), 1, 4);

			pane.add(new Label("Photo:"), 0, 5);
			Button openButton = new Button("Open a Picture...");
			pane.add(openButton, 1, 5);
			pane.add(a, 1, 6);
			FileChooser photo = new FileChooser();
			Stage stage = new Stage();
			stage.setTitle("File Chooser Sample");
			openButton.setOnAction((final ActionEvent t) -> {
				File file = photo.showOpenDialog(stage);
				if (file != null) {
					String cwd = System.getProperty("user.dir");
					a.setText(new File(cwd).toURI().relativize(file.toURI()).getPath());
				}
			});
			Button addProp = new Button("add");
			addProp.setOnAction(new addPropHandler());
			addProp.setLayoutX(200);
			addProp.setLayoutY(300);
			container.getChildren().add(addProp);
			container.getChildren().add(pane);
			// admin.addProp();

			contents = container;
		}
	}

	class MenuItem2_1Handler implements EventHandler<ActionEvent> {
		@Override // Override the handle method
		public void handle(ActionEvent e) {
			System.out.println("Menu Item 2_1 Selected");
			controller.SQL.ViewData();
			// VBox
			Pane container = contents;
			container.getChildren().clear();
			VBox vb = new VBox();
			vb.setPadding(new Insets(10, 50, 50, 50));
			vb.setSpacing(10);

			Label lbl = new Label("PROPERTY LIST");
			lbl.setFont(Font.font("PROPERTY LIST", FontWeight.BOLD, 24));
			vb.getChildren().add(lbl);

			// Buttons
			for (int i = 0; i < admin.getPropList().size(); i++) {
				Text propDetails = new Text(admin.getPropList().get(i).getDetails());
				vb.getChildren().add(propDetails);
			}
			container.getChildren().add(vb);
			contents = container;
		}
	}

	class addPropHandler implements EventHandler<ActionEvent> {
		@Override // Override the handle method
		public void handle(ActionEvent e) {
			String id, snum, sname, suburb;
			int bednum;
			Pane pane = (Pane) contents.getChildren().get(1);
			id = ((TextField) pane.getChildren().get(5)).getText();
			snum = ((TextField) pane.getChildren().get(6)).getText();
			sname = ((TextField) pane.getChildren().get(7)).getText();
			suburb = ((TextField) pane.getChildren().get(8)).getText();
			bednum = Integer.parseInt(((TextField) pane.getChildren().get(9)).getText());
			Apartment apt = new Apartment(id, snum, sname, suburb, bednum);
			SQL.insertData(apt);

			try {
				System.out.println(id + " " + snum + " " + sname + " " + suburb + " " + bednum);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}