package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.*;
import java.sql.Statement;

public class Payment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/paf?useTimezone=true&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayment(String Payment_amount, String Payment_purpose, String Ruser_ID,
			String med_ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
// create a prepared statement
			String query = " insert into payments(`Payment_ID`,`Payment_amount`,`Payment_purpose`,`Ruser_ID`,`med_ID`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, Integer.parseInt(Payment_amount));
			preparedStmt.setString(3, Payment_purpose);
			preparedStmt.setInt(4, Integer.parseInt(Ruser_ID));
			preparedStmt.setInt(5, Integer.parseInt(med_ID));
// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPayments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Payment Amount</th><th>Payment Purpose</th><th>Ruser_ID</th><th>med_ID</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from payments";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
// iterate through the rows in the result set
			while (rs.next()) {
				String payment_ID = Integer.toString(rs.getInt("payment_ID"));
				String payment_amount = Integer.toString(rs.getInt("payment_amount"));
				String payment_purpose = rs.getString("payment_purpose");
				String Ruser_ID = Integer.toString(rs.getInt("Ruser_ID"));
				String med_ID = Integer.toString(rs.getInt("med_ID"));
// Add into the html table
				output += "<tr><td>" + payment_amount + "</td>";
				output += "<td>" + payment_purpose + "</td>";
				output += "<td>" + Ruser_ID + "</td>";
				output += "<td>" + med_ID + "</td>";
// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"items.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
						+ "<input name=\"itemID\" type=\"hidden\" value=\"" + payment_ID + "\">" + "</form></td></tr>";
			}
			con.close();
// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String paymentid, String paymentamount, String paymentpurpose, String Ruser_ID,
			String med_ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
// create a prepared statement
			String query = "UPDATE payments SET payment_amount=?,payment_purpose=?,Ruser_ID=?,med_ID=?WHERE payment_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setString(1, paymentamount);
			preparedStmt.setString(2, paymentpurpose);
			preparedStmt.setString(3, Ruser_ID);
			preparedStmt.setString(4, med_ID);
			preparedStmt.setInt(5, Integer.parseInt(paymentid));
// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePayment(String payment_ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
// create a prepared statement
			String query = "delete from payments where payment_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setInt(1, Integer.parseInt(payment_ID));
// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the payment.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
