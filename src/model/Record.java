package model;

public class Record {
	private String _recordID;
	private DateTime _startDate;
	private DateTime _endDate;
	private DateTime _returnDate;
	private double _rentFee;
	private double _lateFee;
	public Record(String recordID, DateTime startDate, DateTime endDate, DateTime returnDate, double rentFee,
			double lateFee) {
		_recordID = recordID; 
		_startDate=startDate;
		_endDate=endDate;
		_returnDate = returnDate;
		_rentFee = rentFee;
		_lateFee = lateFee;
	}

	public Record(String recordID, DateTime startDate, DateTime endDate) {
		_recordID = recordID.replace("/", "");
		_startDate = startDate;
		_endDate = endDate;
	}

	public String getRecordID() {
		return _recordID;
	}
	public DateTime getStartDat() {
		return _startDate;
	}
	public DateTime getEndDat() {
		return _endDate;
	}
	public DateTime getReturnDate() {
		return _returnDate;
	}

	/**
	 * @return the _rentFee
	 */
	public double getRentFee() {
		return _rentFee;
	}

	/**
	 * @return the _lateFee
	 */
	public double getLateFee() {
		return _lateFee;
	}

	@Override
	public String toString() {
		if (_rentFee == 0.0) {
			return _recordID + ":" + _startDate.toString() + ":" + _endDate.toString() + " : none : none : none";
		} else {
			return _recordID + ":" + _startDate.toString() + ":" + _endDate.toString() + ":" + _returnDate.toString()
					+ ":" + String.format("%.2f", _rentFee) + ":" + String.format("%.2f", _lateFee);
		}

	}

	public String getDetails() {
		if (_rentFee == 0.0) {
		return "Record ID:" + "\t" + _recordID + "\n" + 
				"Rent Date:" + "\t" + _startDate.toString() + "\n" + 
				"Estimated Return Date:" + "\t" + _endDate.toString()+ "\n" + 
				"==============================";
		} else {
			return "Record ID:" + "\t" + _recordID + "\n" + 
					"Rent Date:" + "\t" + _startDate.toString() + "\n" + 
					"Estimated Return Date:" + "\t" + _endDate.toString()+"\n" +
					"Actual Return Date:"+ "\t" + _returnDate.toString()+"\n" +
					"Rental Fee:"+ "\t" + String.format("%.2f", _rentFee)+"\n" +
					"Late Fee:"+ "\t" + String.format("%.2f", _lateFee)+ "\n"+
					"==============================";
		}
	}
}
