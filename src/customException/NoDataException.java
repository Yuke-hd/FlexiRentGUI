package customException;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import view.Popups;

public class NoDataException extends SQLException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoDataException() {
		String errormsg = "Database not found or currently locked";
		Popups.alert(errormsg);
	}

}
