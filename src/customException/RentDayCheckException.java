package customException;

import view.Popups;

public class RentDayCheckException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3816060515617663173L;
	String errormsg;

	public RentDayCheckException(String errormsg) {
		this.errormsg = errormsg;
	}

	public void errorPopup() {
		Popups.alert(errormsg);
	}
}
