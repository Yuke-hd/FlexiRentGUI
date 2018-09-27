package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Apartment;
import model.Property;
import model.Record;

public class SQL {
	final static String driver = "org.hsqldb.jdbc.JDBCDriver";
	final static String dbPath = "jdbc:hsqldb:file:database/RENTAL_PROPERTY";
	final static String dbPath1 = "jdbc:hsqldb:file:database/RENTAL_RECORDS";

	public static ArrayList<String> ViewData() {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		ArrayList<String> s1 = new ArrayList<>();

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dbPath, "SA", "");
			stmt = con.createStatement();
			result = stmt.executeQuery("SELECT * FROM PROPERTY");
			ResultSetMetaData resultSetMetaData = result.getMetaData();

			while (result.next()) {
				String string = " ";
				for (int i=1; i<= resultSetMetaData.getColumnCount();i++) {
					string = string +"-"+result.getString(i);
					
				}
				System.out.println(string);
				s1.add(string);
//				s1.add(result.getString(8));
//				s1.add(result.getString(1) + "-" + result.getString(2) + "-" + result.getString(3) + "-"
//						+ result.getString(4) + "-" + result.getString(5) + "-" + result.getString(6)+ "-" + result.getString(7));
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return s1;
	}

	public static void update(boolean stat, String propId) {
		String sql = "UPDATE Property SET isrented = ? where id = ?";
		try (Connection con = DriverManager.getConnection(dbPath, "SA", "");
				PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setBoolean(1, stat);
			stmt.setString(2, propId);
			stmt.executeUpdate();

			System.out.println("Database updated successfully ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertData(Property prop) {
		Connection con = null;
		// Statement pstmt = null;
		String sql = "INSERT INTO Property (id, snum, sname, suburb, bednum, isapt, isrented, imgpath) "
				+ " Values (?, ?, ?, ?, ?,?,?,?)";
		Statement stmt = null;
		int result = 0;
		String id = prop.getPropId();
		String snum = prop.getStreetNum();
		String sname = prop.getStreetName();
		String suburb = prop.getSuburb();
		int bednum = prop.getBedNum();
		boolean isapt = prop.getType();
		String imgpath = prop.getImgPath();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dbPath, "SA", "");
			stmt = con.createStatement();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, snum);
			pstmt.setString(3, sname);
			pstmt.setString(4, suburb);
			pstmt.setInt(5, bednum);
			pstmt.setBoolean(6, isapt);
			pstmt.setBoolean(7, false);
			pstmt.setString(8, imgpath);
			result = pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		System.out.println(result + " rows effected");
		System.out.println("Rows inserted successfully");
	}

	public static void insertRecords(String propid,Record rec) {
		Connection con = null;
		// Statement pstmt = null;
		
		Statement stmt = null;
		int result = 0;
		String sql;
		String recID=rec.getRecordID();
		String sdate=Utility.convertDate(rec.getStartDat());
		String edate=Utility.convertDate(rec.getEndDat());
		String rdate="";
		double rentfee= rec.getRentFee();
		double latefee=0.0;
		
		if (rentfee>0.0) {
			rdate=Utility.convertDate(rec.getReturnDate());
			rentfee= rec.getRentFee();
			latefee= rec.getLateFee();
			sql = "INSERT INTO Records (id, recordid, startDate, endDate , returnDate , rentFee , lateFee) "
					+ " Values (?, ?, ?, ?, ?,?,?)";
		} else {
			sql = "INSERT INTO Records (id, recordid, startDate, endDate) "
					+ " Values (?, ?, ?, ?)";
		}

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dbPath1, "SA", "");
			stmt = con.createStatement();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, propid);
			pstmt.setString(2, recID);
			pstmt.setString(3, sdate);
			pstmt.setString(4, edate);
			if (rentfee>0.0) {
				pstmt.setString(5, rdate);
				pstmt.setDouble(6, rentfee);
				pstmt.setDouble(7, latefee);
			}
			result = pstmt.executeUpdate();
			con.commit();
			System.out.println(result + " rows effected");
			System.out.println("Rows inserted successfully");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		
	}

	public static ArrayList<String> viewRecords(String id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		ArrayList<String> s1 = new ArrayList<>();

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dbPath1, "SA", "");
			stmt = con.prepareStatement("SELECT * FROM RECORDS WHERE id = ?");
			stmt.setString(1,id);
			result = stmt.executeQuery();
			ResultSetMetaData resultSetMetaData = result.getMetaData();

			while (result.next()) {
				String string = " ";
				for (int i=1; i<= resultSetMetaData.getColumnCount();i++) {
					string = string +"/"+result.getString(i);
					
				}
				System.out.println(string);
				s1.add(string);
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return s1;
	}

}
