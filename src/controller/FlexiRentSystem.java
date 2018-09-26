package model;

import java.util.*;

public class FlexiRentSystem {
	Scanner sc = new Scanner(System.in);
	private ArrayList<Property> propList = new ArrayList<Property>();



	public void addProp(String id,String snum,String sname,String suburb,int bednum,String imgpath) {
		

			Apartment prop = new Apartment(id, snum, sname, suburb, bednum,imgpath);
			propList.add(prop);
			System.out.println("APTARTMENT");
		System.out.println(propList.get(propList.size()-1).getClass().getSimpleName()+" "+id+" created. "+"\n");
	}
	
	public void addProp(String id,String snum,String sname,String suburb,String imgpath) {
		

		Suite prop = new Suite(id, snum, sname, suburb,imgpath);
		propList.add(prop);
		System.out.println("SUITE");
	System.out.println(propList.get(propList.size()-1).getClass().getSimpleName()+" "+id+" created. "+"\n");
}

	private void rentProp() {
		System.out.printf("\n"+"**** rent property ****"+"\n");
		int objNum = inputPropID();
		if (objNum < 0 || propList.get(objNum).getStat()) {
			System.out.println("Invalid property ID"+"\n");
			return;
		}
		System.out.println("Customer id:");
		String custID = sc.nextLine();
		System.out.println("Rent date (dd/mm/yyyy):");
		DateTime startDate = inputDate();
		System.out.println("How many days?:");
		int rentDay = sc.nextInt();sc.nextLine();
		propList.get(objNum).setStat1(propList.get(objNum).rent(custID, startDate, rentDay));
		System.out.println();
		return;
	}
	
	/**
	 * input a date method for rentProp() and returnProp()
	* @return dd/mm/yyyy format date
	* @see FlexiRentSystem.rentProp
	* @see FlexiRentSystem.returnProp
	*/
	private DateTime inputDate() {
		String inputDate;
		int day,month,year;
		do {
			try {
				inputDate = sc.nextLine();
				String[] datePart = inputDate.split("/");
				day = Integer.parseInt(datePart[0]);
				month = Integer.parseInt(datePart[1]);
				year = Integer.parseInt(datePart[2]);
				break;
			} catch (Exception e) {
				System.out.println("not a valid date, try again.");
				continue;
			}
		} while (true);
		DateTime Date = new DateTime(day, month, year);
		return Date;
	}
	
	private void returnProp() {
		System.out.printf("\n"+"**** return property ****"+"\n");
		int objNum = inputPropID();
		if (objNum < 0||!propList.get(objNum).getStat()) {
			System.out.println("property does not exsit or not rented"+"\n");
			return;
		}
		System.out.println("Return date (dd/mm/yyyy):");
		DateTime returnDate = inputDate();
		propList.get(objNum).setStat1(!propList.get(objNum).returnProperty(returnDate));
		return;
	}
	/**
	 * input property id method for rentProp() and returnProp()
	* @return the corresponding property No. of the user inputed property id 
	* @see FlexiRentSystem.rentProp
	* @see FlexiRentSystem.returnProp
	*/
	private int inputPropID() {
		System.out.println("Please input property ID:");
		String _propId = sc.nextLine();
		int objNum = -1;
		for (int i = 0; i < propList.size(); i++) {
			if (_propId.equals(propList.get(i).getPropId())) {
				objNum = i;
				// System.out.println("valid property ID");
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

	private void performMaintenance() {
		System.out.printf("\n"+"**** Maintenance ****"+"\n");
		int objNum = inputPropID();
		if (objNum < 0 || propList.get(objNum).getStat()) {
			System.out.println("property does not exsit or is rented"+"\n");
			return;
		}
		propList.get(objNum).setStat1(propList.get(objNum).performMaintenance());
		return;
	}
	
	private void completeMaintenance() {
		System.out.printf("\n"+"**** Complete Maintenance ****"+"\n");
		int objNum = inputPropID();
		if (objNum < 0 || !propList.get(objNum).getStat()) {
			System.out.println("property does not exsit or not under maintenance"+"\n");
			return;
		}
		System.out.println("Maintenance completion date (dd/mm/yyyy): ");
		DateTime cmptDate = inputDate();
		if (propList.get(objNum) instanceof Suite) {
			if (DateTime.diffDays(cmptDate, ((Suite) propList.get(objNum)).getMntDate())>10) {
				System.out.println("Last maintenance date: "+((Suite) propList.get(objNum)).getMntDate().toString()+", Maintenance interval for suites must be less than 10 days");
				return;
			}
			
		};
		propList.get(objNum).setStat1(propList.get(objNum).completeMaintenance(cmptDate));
	}

	public ArrayList<Property> getPropList() {
		return propList;
		
	}
}
