/*
 * MIT License
 * 
 * Copyright (c) 2017 Austin Asmus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.legit.globalrep.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.legit.globalrep.chat.Message;

public class DatabaseConnection {
	
	private final String DB_IP;
	private final int DB_PORT;
	private final String DB_NAME;
	private final String user;
	private final String pass;

	public DatabaseConnection(String databaseIp, int databasePort, String databaseName, String username, String password) {
		DB_IP = databaseIp;
		DB_PORT = databasePort;
		DB_NAME = databaseName;
		user = username;
		pass = password;
	}

	/**
	 * getConnection - used to get a connection to the database
	 * 
	 * @return - returns the open connection
	 */
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			Message.genericErrorSystem(e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			Message.genericErrorSystem(e);
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			Message.genericErrorSystem(e);
			throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME, user, pass);

		} catch (SQLException e) {
			Message.databaseError(e);
		}
		return conn;
	}
	
	/**
	 * checkConnection - used to check if database connection is still open
	 * 
	 * @param connection - the connection being checked
	 * @return - returns open connection
	 */
	public Connection checkConnection(Connection connection) {
		try {
			if(connection == null || connection.isClosed()) {
				 connection = getConnection();
			}
		} catch (SQLException e) {
			Message.databaseError(e);
		}
		return connection;
	}
	
}
