package model;

import java.util.*;

public class FlexiRentSystem {
	Scanner sc = new Scanner(System.in);
	private ArrayList<Property> propList = new ArrayList<Property>();

	public void menu() {
		Scanner sc = new Scanner(System.in);
		FlexiRentSystem admin = new FlexiRentSystem();
		while (true) {
			System.out.println("**** FLEXIRENT SYSTEM MENU **** ");
			System.out.println("Add Property:                  1");
			System.out.println("Rent Property:                 2");
			System.out.println("Return Property:               3");
			System.out.println("Property Maintenance:          4");
			System.out.println("Complete Maintenance:          5");
			System.out.println("Display All Properties:        6");
			System.out.println("Exit Program:                  7");
			System.out.print("Enter your choice: ");
			int x;
			try {
				x = sc.nextInt();
				sc.nextLine();
			} catch (Exception e) {
				sc.nextLine();
				x=7;
			}
			switch (x) {
			case 1:
				admin.addProp();
				break;
			case 2:
				admin.rentProp();
				break;
			case 3:
				admin.returnProp();
				break;
			case 4:
				admin.performMaintenance();
				break;
			case 5:
				admin.completeMaintenance();
				break;
			case 6:
				admin.displayAllProp();
				break;
			case 7:
				System.exit(0);
			case 8: break;
			default:
				break;
			}// sc.close();
		}
	}

	public void addProp() {
		System.out.printf("\n"+"**** add property ****"+"\n"+"Property ID:");
		String propId = sc.nextLine();
		for (int i =0; i<propList.size();i++) {
			if (propId.equals(propList.get(i).getPropId())) 
				{System.out.println("property id already exsits");
				return;}
		}
		String propId0 = propId.substring(0, 2);
		if (!(propId0.equals("A_")||propId0.equals("S_"))) {
			System.out.println("invalid property id, must starts with \"A_\" or \"S_\""+"\n");return;
		}
		System.out.println("Street number:");
		String streetNum = sc.nextLine();
		System.out.println("Street name:");
		String streetName = sc.nextLine();
		System.out.println("Suburb");
		String suburb = sc.nextLine();
		if (propId.startsWith("A_")) {
			int bedNum;
			do {bedNum=0;
				try {
					System.out.println("Bed number(1-3)");
					bedNum = sc.nextInt();
					sc.nextLine();
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("try again");
					continue;
				}
			} while (bedNum>3||bedNum<1);
			Apartment apt = new Apartment(propId, streetNum, streetName, suburb, bedNum,"/");
			propList.add(apt);
		} else if (propId.startsWith("S_")){
			Suite suite = new Suite(propId, streetNum, streetName, suburb);
			System.out.println("Set Maintenance Date: ");
			DateTime MntDate = inputDate();
			suite.setMntDate(MntDate);
			propList.add(suite);
		} 
		System.out.println(propList.get(propList.size()-1).getClass().getSimpleName()+" "+propId+" created. "+"\n");
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
