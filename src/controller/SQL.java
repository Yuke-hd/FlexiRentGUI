package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import model.Apartment;

public class SQL {
	static String driver = "org.hsqldb.jdbc.JDBCDriver";
	static String dbPath = "jdbc:hsqldb:file:/database/RENTAL_PROPERTY";

	public static void ViewData() {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dbPath, "SA", "");
			stmt = con.createStatement();
			result = stmt.executeQuery("SELECT * FROM PROPERTY");

			while (result.next()) {
				System.out.println(result.getString("id"));
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}

	public static void addProperty() {

	}

	public static void insertData(Apartment prop) {
		Connection con = null;
		//Statement pstmt = null;
		String sql = "INSERT INTO Property (id, snumber, sname, suburb, bednum) " + " Values (?, ?, ?, ?, ?)";
		Statement stmt = null;
		int result = 0;
		String id = prop.getPropId();
		String snum = prop.getStreetNum();
		String sname = prop.getStreetName();
		String suburb = prop.getSuburb();
		int bednum = prop.getBedNum();
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
			result = pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		System.out.println(result + " rows effected");
		System.out.println("Rows inserted successfully");
	}

}
