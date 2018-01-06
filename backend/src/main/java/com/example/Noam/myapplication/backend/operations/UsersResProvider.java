package com.example.Noam.myapplication.backend.operations;

import com.example.Noam.myapplication.backend.objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersResProvider {

	private static final String update_sql = "UPDATE users SET name=? WHERE name=?;";

	private static final String select_sql = "SELECT * FROM  users WHERE name=?;";

	private static final String insert_sql = "INSERT INTO users (name) VALUES (?);";

	private static final String delete_sql = "DELETE FROM users WHERE name=?;";

	//private static final String select_all_sql = "SELECT * FROM tumblers;";


	public boolean insertUser(User obj, Connection conn) {

		boolean result = false;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement ps = null;
		PreparedStatement stt = null;

		try {

			String name =obj.getName();

			stt = (PreparedStatement) conn.prepareStatement(select_sql);
			stt.setString(1, name);

			if (stt.execute()) {
				rs1 = stt.getResultSet();
				if (rs1.next()) {
					
					// its execute update
					ps = (PreparedStatement) conn.prepareStatement(update_sql);
					ps.setString(1, name);

					// where
					ps.setString(1, name);
					ps.execute();
					result = true;
				} else {

					// its execute insert
					ps = (PreparedStatement) conn.prepareStatement(insert_sql);

					ps.setString(1, name);

					ps.execute();
					result = true;

				}
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			if (rs1 != null) {
				try {
					rs1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

			if (stt != null) {
				try {
					stt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}


	public boolean deleteTumbler(User obj,
								 Connection conn) throws SQLException {

		boolean result = false;
		PreparedStatement ps = null;


		try {

			if (obj != null) {
				
				String name = obj.getName();
				
				ProductsResProvider itemResProvider = new ProductsResProvider();
				itemResProvider.deleteAllProductsByUser(new User(name), conn);

				ps = (PreparedStatement) conn.prepareStatement(delete_sql);

			
				ps.setString(1, name);
				
				ps.execute();

				result = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}

		return result;
	}



}
