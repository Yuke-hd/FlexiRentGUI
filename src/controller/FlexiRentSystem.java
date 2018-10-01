package controller;

import java.time.DateTimeException;
import java.util.*;

import customException.DateTimeFormatException;
import customException.RentDayCheckException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import model.*;

public class FlexiRentSystem {
	Scanner sc = new Scanner(System.in);
	private ArrayList<Property> propList = new ArrayList<Property>();

	public void addProp(String id, String snum, String sname, String suburb, int bednum, boolean isRented,
			String imgpath) {

		Apartment prop = new Apartment(id, snum, sname, suburb, bednum, isRented, imgpath);
		propList.add(prop);
		System.out.println("APTARTMENT");
		System.out
				.println(propList.get(propList.size() - 1).getClass().getSimpleName() + " " + id + " created. " + "\n");
	}

	public void addProp(String id, String snum, String sname, String suburb, boolean isRented, String imgpath,
			DateTime mntDate) {

		Suite prop = new Suite(id, snum, sname, suburb, isRented, imgpath, mntDate);
		propList.add(prop);
		System.out.println("SUITE");
		System.out
				.println(propList.get(propList.size() - 1).getClass().getSimpleName() + " " + id + " created. " + "\n");
	}

	boolean rentProp(int propNum, String custID, String rentDate, int days) {
		System.out.printf("\n" + "**** rent property ****" + "\n");
		int objNum = propNum;
		if (objNum < 0 || propList.get(objNum).getStat()) {
			System.out.println("Invalid property ID" + "\n");
			return false;
		}
		System.out.println("Customer id: " + custID);

		DateTime startDate;
		try {
			startDate = inputDate(rentDate);
			System.out.println("Rent date (dd/mm/yyyy): " + rentDate);
			try {
				propList.get(objNum).setStat1(propList.get(objNum).rent(custID, startDate, days));
			} catch (RentDayCheckException e) {
				e.errorPopup();
				return false;
			}
		} catch (DateTimeFormatException e) {
			e.errorPopup();
			return false;
		}

		System.out.println("How many days?: " + days);

		return true;
	}

	/**
	 * input a date method for rentProp() and returnProp()
	 * 
	 * @return dd/mm/yyyy format date
	 * @see FlexiRentSystem.rentProp
	 * @see FlexiRentSystem.returnProp
	 */
	private DateTime inputDate(String date) throws DateTimeFormatException {
		String inputDate;
		int day, month, year;

		inputDate = date;
		String[] datePart = inputDate.split("/");
		if (datePart.length < 3) {
			throw new DateTimeFormatException();
		}
		day = Integer.parseInt(datePart[0]);
		month = Integer.parseInt(datePart[1]);
		year = Integer.parseInt(datePart[2]);

		DateTime Date = new DateTime(day, month, year);
		return Date;
	}

	public void returnProp(String propId, String Date) {
		System.out.printf("\n" + "**** return property ****" + "\n");
		int objNum = inputPropID(propId);
		if (objNum < 0 || !propList.get(objNum).getStat()) {
			System.out.println("property does not exsit or not rented" + "\n");
			return;
		}
		DateTime returnDate;
		try {
			returnDate = inputDate(Date);
			propList.get(objNum).setStat1(!propList.get(objNum).returnProperty(returnDate));
		} catch (DateTimeFormatException e) {
			e.errorPopup();
		}
		
		return;
	}

	/**
	 * input property id method for rentProp() and returnProp()
	 * 
	 * @return the corresponding property No. of the user inputed property id
	 * @see FlexiRentSystem.rentProp
	 * @see FlexiRentSystem.returnProp
	 */
	public int inputPropID(String id) {
		System.out.println("Please input property ID:MARK1");
		String _propId = id;
		int objNum = -1;
		for (int i = 0; i < propList.size(); i++) {
			if (_propId.equals(propList.get(i).getPropId())) {
				objNum = i;
			}
		}
		return objNum;
	}

	private void displayAllProp() {
		System.out.println("==================================");
		for (int i = 0; i < propList.size(); i++) {
			// TODO add getDetails() in Property
			System.out.println(propList.get(i).getDetails());
			System.out.println("==================================");
		}
	}

	public void performMaintenance(int objNum) {
		System.out.printf("\n" + "**** Maintenance ****" + "\n");
		//int objNum = inputPropID();
		if (objNum < 0 || propList.get(objNum).getStat()) {
			System.out.println("property does not exsit or is rented" + "\n");
			return;
		}
		boolean stat = propList.get(objNum).performMaintenance();
		propList.get(objNum).setStat1(stat);
		SQL.update(stat, propList.get(objNum).getPropId());
		return;
	}

	public void completeMaintenance(int objNum, String completeDate) {
		System.out.printf("\n" + "**** Complete Maintenance ****" + "\n");
		System.out.println(propList.get(objNum).getPropId());
		//int objNum = inputPropID();
		if (objNum < 0 || !propList.get(objNum).getStat()) {
			System.out.println("property does not exsit or not under maintenance" + "\n");
			return;
		}
		System.out.println("Maintenance completion date (dd/mm/yyyy): ");
		DateTime cmptDate = null;
		try {
			cmptDate = inputDate(completeDate);
		} catch (DateTimeFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (propList.get(objNum) instanceof Suite) {
			if (DateTime.diffDays(cmptDate, ((Suite) propList.get(objNum)).getMntDate()) > 10) {
				System.out.println("Last maintenance date: " + ((Suite) propList.get(objNum)).getMntDate().toString()
						+ ", Maintenance interval for suites must be less than 10 days");
				return;
			}
		}
		boolean stat=propList.get(objNum).completeMaintenance(cmptDate);
		propList.get(objNum).setStat1(stat);
		SQL.update(stat, propList.get(objNum).getPropId());
	}

	public ArrayList<Property> getPropList() {
		return this.propList;

	}

}
