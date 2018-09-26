package model;

public class Suite extends Property {
	DateTime _mntDate;

	public Suite(String propId, String streetNum, String streetName, String suburb,Boolean isRented,String imgpath) {
		super(propId, streetNum, streetName, suburb, 3, false, isRented,imgpath);

	}

	@Override
	public String getDetails() {
		String text;
		text=super.getDetails();

		return text + 
				"Last maintenance:" + _mntDate + "\n" + 
				"RENTAL RECORD" + "\n" + 
				super.getRecords() + "\n";
	}

	public void setMntDate(DateTime MntDate) {
		_mntDate = MntDate;
	}

	public DateTime getMntDate() {
		return _mntDate;
	}
	
	@Override
	public boolean performMaintenance() {
		System.out.println("Suite " + super.getPropId() + " is now under maintenance");
		return true;
	}

	@Override
	public boolean completeMaintenance(DateTime completionDate) {
		_mntDate = completionDate;
		return false;
	}

	@Override
	public String toString() {
		return super.getPropId() + super.getStreetNum() + super.getStreetName() + super.getSuburb()
				+ this.getClass().getSimpleName() + super.getBedNum() + super.getTypeName() + this._mntDate;
	}

	/**
	 * @param customerId the id of customer
	 * @param rentDate   start date
	 * @param rentDay    how many day to rent
	 * @return if the suite is successfully rented
	 */
	public boolean rent(String customerId, DateTime rentDate, int rentDay) {
		DateTime returnDate = new DateTime(rentDate, rentDay);
		DateTime lastMntDay = new DateTime(_mntDate, 10);
		if (DateTime.diffDays(lastMntDay, returnDate) <= 0 || DateTime.diffDays(rentDate, _mntDate) <= 0) {
			System.out.println("can not rent due to maintenance restrictions");
			return false;
		}
		String recordID = setRecordID(getPropId(), customerId);
		Record record = new Record(recordID, rentDate, returnDate);
		System.out.println(
				customerId + " rent " + this.getPropId() + " on " + rentDate.toString() + " for " + rentDay + " Days");
		for (int i = 9; i > 0; i--) {
			propRecord[i] = propRecord[i - 1];
		}
		propRecord[0] = record;
		System.out.println(propRecord[0].toString());
		return true;
	}

}