package customException;

import view.Popups;

public class DateTimeFormatException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateTimeFormatException() {
		
	}
	
	public void errorPopup() {
		String errormsg = "invalid date format (dd/MM/yyyy)";
		Popups.alert(errormsg);
	}

}
