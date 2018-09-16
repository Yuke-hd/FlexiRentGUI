package model;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;

public class DateTime
{
	
	private long advance;
	private long time;
	
	public DateTime()
	{
		time = System.currentTimeMillis();
	}
	
	public DateTime(int setClockForwardInDays)
	{
		advance = ((setClockForwardInDays * 24L + 0) * 60L) * 60000L;
		time = System.currentTimeMillis() + advance;
	}
	
	public DateTime(DateTime startDate, int setClockForwardInDays)
	{
		advance = ((setClockForwardInDays * 24L + 0) * 60L) * 60000L;
		time = startDate.getTime() + advance;
	}
	
	public DateTime(int day, int month, int year)
	{
		setDate(day, month, year);
	}
	
	public long getTime()
	{
		return time;
	}
	
	public String toString()
	{
//		long currentTime = getTime();
//		Date gct = new Date(currentTime);
//		return gct.toString();
		return getFormattedDate();
	}
	
	
	
	public static String getCurrentTime()
	{
		Date date = new Date(System.currentTimeMillis());  // returns current Date/Time
		return date.toString();
	}
	
	public String getFormattedDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		long currentTime = getTime();
		Date gct = new Date(currentTime);
		
		return sdf.format(gct);
	}
	
	public String getEightDigitDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		long currentTime = getTime();
		Date gct = new Date(currentTime);
		
		return sdf.format(gct);
	}
	
	// returns difference in days
	public static int diffDays(DateTime endDate, DateTime startDate)
	{
		final long HOURS_IN_DAY = 24L;
		final int MINUTES_IN_HOUR = 60;
		final int SECONDS_IN_MINUTES = 60;
		final int MILLISECONDS_IN_SECOND = 1000;
		long convertToDays = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTES * MILLISECONDS_IN_SECOND;
		long hirePeriod = endDate.getTime() - startDate.getTime();
		double difference = (double)hirePeriod / (double)convertToDays;
		int round = (int)Math.round(difference);
		return round;
	}
	
	private void setDate(int day, int month, int year)
	{
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0);   

		java.util.Date date = calendar.getTime();

		time = date.getTime();
	}
	
	// Advances date/time by specified days, hours and mins for testing purposes
		public void setAdvance(int days, int hours, int mins)
		{
			advance = ((days * 24L + hours) * 60L) * 60000L;
		}
	
		/**
		 * calculate the week day of a give date
		 * @param Date dd/mm/yyyy
		* @return weekDay 1-6 (0 for Sunday)
		*/
	public static int calcWeekDay(DateTime Date) {
		String day =Date.getFormattedDate();
		String[] datePart = day.split("/");
		int d = Integer.parseInt(datePart[0]);
		int m = Integer.parseInt(datePart[1]);
		int y = Integer.parseInt(datePart[2]);
		int weekDay = -1;
		if (m == 1 || m == 2) {
			m += 12;
			y--;
		}
		int iWeek = (d + 2 * m + 3 * (m + 1) / 5 + y + y / 4 - y / 100 + y / 400) % 7;
		switch (iWeek) {
		case 0:
			weekDay = 1;
			break;
		case 1:
			weekDay = 2;
			break;
		case 2:
			weekDay = 3;
			break;
		case 3:
			weekDay = 4;
			break;
		case 4:
			weekDay = 5;
			break;
		case 5:
			weekDay = 6;
			break;
		case 6:
			weekDay = 0;
			break;
		}
		return weekDay;
	}
}
