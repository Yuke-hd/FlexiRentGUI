package model;

import controller.SQL;
import customException.RentDayCheckException;

public class Apartment extends Property{

	public Apartment(String propId, String streetNum, String streetName, String suburb, int bedNum,Boolean isRented, String imgpath) {
		super(propId, streetNum, streetName, suburb, bedNum, true, isRented, imgpath);
		// TODO Auto-generated constructor stub
		
	}
	@Override
	public String getDetails() {
		String text;
		text=super.getDetails();
		return text +"\n"+ 
				"RENTAL RECORD"+"\n"+
				super.getRecords()+"\n";
	}
	
	
	/**
	* @param customerId the id of customer
	* @param rentDate start date
	* @param rentDay how many day to rent
	* @return if the apartment is successfully rented
	 * @throws RentDayCheckException 
	*/
	public boolean rent(String customerId, DateTime rentDate, int rentDay) throws RentDayCheckException {
		DateTime returnDay = new DateTime(rentDate, rentDay);
		String recordID = setRecordID(getPropId(), customerId);
		Record record = new Record(recordID, rentDate, returnDay);
		int weekDay = DateTime.calcWeekDay(rentDate);
		if (0 <= weekDay && weekDay <= 4) {	
			if (rentDay < 2 || rentDay > 28) {
				System.out.println("for Monday to Thursday, minimum rent day is 2 days");
				throw new RentDayCheckException("for Monday to Thursday, minimum rent day is 2 days");
				}
		} else if (rentDay < 3 || rentDay > 28) {
			System.out.println("for Friday to Sunday, minimum rent day is 3 days");
			throw new RentDayCheckException("for Friday to Sunday, minimum rent day is 3 days");
		}
		System.out.println(
				customerId + " rent " + this.getPropId() + " on " + rentDate.toString() + " for " + rentDay + " Days");
		for (int i = 9; i > 0; i--) {
			propRecord[i] = propRecord[i - 1];
		}
		propRecord[0]=record;
		SQL.update(true, this.getPropId());
		SQL.insertRecords(this.getPropId(), record);
		System.out.println(propRecord[0].toString());
		return true;
	}
	
	@Override
	public boolean performMaintenance() {
		System.out.println("Apartment "+super.getPropId()+" is now under maintenance");
		return true;
	}

	@Override
	public boolean completeMaintenance(DateTime completionDate) {
		
		return false;
	}
	@Override
	public String toString() {
		return super.getPropId() + super.getStreetNum() + super.getStreetName() + super.getSuburb() + 
				this.getClass().getSimpleName() + super.getBedNum() + super.getTypeName();
	}
}