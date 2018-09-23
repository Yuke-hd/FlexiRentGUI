package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.Apartment;
import model.Property;

public class SQL {
	final static String driver = "org.hsqldb.jdbc.JDBCDriver";
	final static String dbPath = "jdbc:hsqldb:file:database/RENTAL_PROPERTY";
	//final static String dbPath = "jdbc:hsqldb:file:/database/RENTAL_PROPERTY";
	
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

			while (result.next()) {
				System.out.println(result.getString("imgpath"));
				s1.add(result.getString(8));
				s1.add(result.getString(1)+"-"+result.getString(2)+"-"+result.getString(3)+"-"+result.getString(4)+"-"+result.getString(5)+"-"+result.getString(6));
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return s1;
	}

	public static void addProperty() {

	}

	public static void insertData(Property prop) {
		Connection con = null;
		//Statement pstmt = null;
		String sql = "INSERT INTO Property (id, snum, sname, suburb, bednum, isapt, isrented, imgpath) " + 
		" Values (?, ?, ?, ?, ?,?,?,?)";
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

}
