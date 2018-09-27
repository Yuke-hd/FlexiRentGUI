package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.xml.bind.ParseConversionEvent;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.DateTime;

public class Utility {

	public static String trimString(int pos, String info) {
		String[] sArray = info.split("-");
		String string;
		string = sArray[pos];
		return string;
	}
	
	public static String trimString(int start, int end, String info) {
		String[] sArray = info.split("-");
		String string="";
		for (int i = start; i <= end; i++) {
			string=string.concat(" "+sArray[i]);
		}
		return string;
	}

	public static ImageView drawImg(String filePath, int imgWidth) {
		FileInputStream input;
		ImageView imageBox = null;
		try {
			input = new FileInputStream(filePath);
			Image image = new Image(input);
			imageBox = new ImageView(image);
			imageBox.setFitWidth(imgWidth);
			imageBox.setPreserveRatio(true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return imageBox;
		
	}

	public static void importProp(FlexiRentSystem target,ArrayList<String> result) {
		for (int i = 0; i < result.size(); i++) {

			String[] propInfo = result.get(i).split("-");
			//id, snum, sname, suburb, bednum,imgpath
			String id = propInfo[1];
			String snum = propInfo[2];
			String sname = propInfo[3];
			String suburb = propInfo[4];
			int bednum = Integer.parseInt(propInfo[5]);
			String imgpath = Utility.trimString(8,result.get(i));
			boolean isApt = Boolean.parseBoolean( propInfo[6]);
			boolean isRented = Boolean.parseBoolean( propInfo[7]);
			if (isApt) {
				target.addProp(id, snum, sname, suburb, bednum, isRented,imgpath);
			}else {
				target.addProp(id, snum, sname, suburb, isRented,imgpath);
			}
			
			
		}
		
	}

	public static String convertDate(DateTime date) {
		String sqlDate;
		sqlDate=date.getFormattedDate();
		String[] dateArray = sqlDate.split("/");
		sqlDate=dateArray[2]+"-"+dateArray[1]+"-"+dateArray[0];
		return sqlDate;
	}

	public static DateTime reverseDate(String date) {
		String sqlDate;
		sqlDate=date;
		String[] dateArray = sqlDate.split("-");
		//sqlDate=dateArray[2]+"/"+dateArray[1]+"/"+dateArray[0];
		DateTime recdate = new DateTime(Integer.parseInt(dateArray[2]),Integer.parseInt(dateArray[1]),Integer.parseInt(dateArray[0]));
		return recdate;
		
	}
}
