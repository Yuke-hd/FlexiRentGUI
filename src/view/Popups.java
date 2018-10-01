package view;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class Popups {

	public Popups() {
		// TODO Auto-generated constructor stub
	}

	public static void alert(String errormsg) {
		System.out.println(errormsg);
		Alert error = new Alert(Alert.AlertType.ERROR, errormsg);
		Button err = new Button();
		err.setOnAction((ActionEvent e1) -> {
			//error.showAndWait();
		});
		error.show();
	}

	public static void info(String infomsg) {
		Alert information = new Alert(Alert.AlertType.INFORMATION, infomsg);
		Button infor = new Button("show Information");
		infor.setOnAction((ActionEvent) -> {
			
		});
		information.showAndWait();
	}
}
