package model;

public abstract class Property {
	private String _propId;
	private String _streetNum;
	private String _streetName;
	private String _suburb;
	private int _bedNum;
	private boolean _isApt = false;
	private boolean _isRented = false;
	private String _imgPath;
	// LinkedList<Record> propRecord = new LinkedList<Record>();
	Record[] propRecord = new Record[10];

	public Property(String propId, String streetNum, String streetName, String suburb, int bedNum, boolean isApt,
			boolean isRented, String imgPath) {
		_propId = propId;
		_streetNum = streetNum;
		_streetName = streetName;
		_suburb = suburb;
		_bedNum = bedNum;
		_isApt = isApt;
		_isRented = false;_imgPath = imgPath;

	}

	public String getImgPath() {
		return _imgPath;
	}
	
	public boolean getType() {
		return _isApt;
	}

	public String getTypeName() {
		if (_isApt) {
			return "Apartment";
		} else
			return "Suite";
	}

	public boolean getStat() {
		return _isRented;
	}

	public void setStat1(boolean stat) {
		_isRented = stat;
	}

	public void setStat0() {
		_isRented = false;
	}

	public String getPropId() {
		return _propId;
	}

	public String getStreetNum() {
		return _streetNum;
	}

	public String getStreetName() {
		return _streetName;
	}

	public String getSuburb() {
		return _suburb;
	}

	public int getBedNum() {
		return _bedNum;
	}

	/**
	 * Set a record id 
	 * @param propID property id
	 * @param customerId customer id
	 * @return X_propertyid_customerid_date format record id
	 */
	public String setRecordID(String propID, String customerId) {
		DateTime today = new DateTime();
		return propID + "_" + customerId + "_" + today.toString();
	}

	public abstract boolean rent(String customerId, DateTime rentDate, int numOfRentDay);

	public boolean returnProperty(DateTime returnDate) {
		double fee = this.getFee(this._isApt, propRecord[0].getStartDat(), propRecord[0].getEndDat(), returnDate);
		double lateFee = this.getLateFee(this._isApt, propRecord[0].getEndDat(), returnDate);
		Record record = new Record(propRecord[0].getRecordID(), propRecord[0].getStartDat(), propRecord[0].getEndDat(),
				returnDate, fee, lateFee);
		propRecord[0] = record;
		System.out.println(propRecord[0].toString());
		return true;
	}

	/**
	 * Calculate rental fee according to rent days and property type
	 * @param isApt apartment or suite
	 * @param startDate rent date
	 * @param endDate estimated return date
	 * @param returnDate actual return date
	 * @return rental fee (late fee excluded)
	 */
	private double getFee(boolean isApt, DateTime startDate, DateTime endDate, DateTime returnDate) {
		double fee = 0.0;
		int diffDays;
		if (DateTime.diffDays(returnDate, endDate) > 0) {
			diffDays = DateTime.diffDays(endDate, startDate);
		} else {
			diffDays = DateTime.diffDays(returnDate, startDate);
		}
		if (isApt) {
			int x = this.getBedNum();
			switch (x) {
			case 1:
				fee = diffDays * 143.0;
			case 2:
				fee = diffDays * 210.0;
			case 3:
				fee = diffDays * 319.0;
			default:
				break;
			}
		} else {
			fee = diffDays * 554.0;
		}
		return fee;
	}

	/**
	 * Calculate late fee if apply (actual return date - estimated return date > 0)
	 * @param isApt apartment or suite
	 * @param endDate estimated return date
	 * @param returnDate actual return date
	 * @return late fee
	 */
	private double getLateFee(boolean isApt, DateTime endDate, DateTime returnDate) {
		double lateFee = 0.0;
		int lateDays = DateTime.diffDays(returnDate, endDate);
		if (lateDays <= 0) {
			lateFee = 0.0;
		} else {
			if (isApt) {
				int x = this.getBedNum();
				switch (x) {
				case 1:
					lateFee = lateDays * 1.15 * 143.0;
				case 2:
					lateFee = lateDays * 1.15 * 210.0;
				case 3:
					lateFee = lateDays * 1.15 * 319.0;
				}
			} else
				lateFee = lateDays * 662;
		}
		return lateFee;
	}

	public abstract boolean performMaintenance();

	public abstract boolean completeMaintenance(DateTime completionDate);

	public String getDetails() {
		String status;
		if (getStat()) {
			status = "Not Available";
		} else
			status = "Available";
		return "Property ID:" + "\t" + getPropId() + "\n" + "Address:" + "\t" + getStreetNum() + " "
		+ getStreetName() + " " + getSuburb() + "\n" + "Type:" + "\t"
		+ this.getClass().getSimpleName() + "\n" + "Bedroom:" + "\t" + getBedNum() + "\n" + "Status:"
		+ "\t" + status + "\n";
	}

	public String getRecords() {
		String rec = " empty ";
		int lastRecord = 0;
		if (propRecord[0] == null) {
			return rec;
		}else {
			rec = "";
		}
		for (int i = 0; i < 10; i++) {
			if (propRecord[i] != null) {
				lastRecord = i;
				System.out.println(lastRecord);
			}
		}
		for (int j = 0; j <= lastRecord; j++) {
			rec += propRecord[j].getDetails() + "\n";
		}
		return rec;
	}
}
