package com.cooksys.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cooksys.connection.QueryMethod;

public class LocationDao {
	public static Location get(Integer id) {
		ResultSet results = QueryMethod.get("Location", id);
		Location location = null;
		try {
			while (results.next()) {
				location = new Location(results.getInt("id"), results.getString("city"), results.getString("state"),
						results.getString("country"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return location;
	}
	
	public void save(Location location) {
		QueryMethod.add(location);
	}
}
