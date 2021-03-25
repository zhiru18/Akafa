package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

import database.DBConnection;
import database.DataAccessException;

public class DBStatusController {
	/**
	 * This method checks the connection to the database.
	 * @param method
	 */
	public static void checkStatus(Consumer<Boolean> method) {
		boolean res = false;
		while(true) {
			try(Statement s = DBConnection.getInstance().getConnection().createStatement()) {
				Thread.sleep(1000);
				ResultSet rs = s.executeQuery("Select 1");
				res = rs.next();
			} catch (SQLException e) {
				res = false;
			} catch (DataAccessException e) {
				res = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			method.accept(res);
		}
	}
}
