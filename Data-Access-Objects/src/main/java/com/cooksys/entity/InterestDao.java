package com.cooksys.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.cooksys.connection.QueryMethod;

public class InterestDao {
	public static Interest get(Integer id) {
		ResultSet results = QueryMethod.get("Interest", id);
		Interest interest = null;
		try {
			while (results.next()) {
				interest = new Interest(results.getInt("id"), results.getString("title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return interest;
	}
	
	public void save(Interest interest) {
		QueryMethod.add(interest);
	}
	
	public static Set<Interest> getInterestSet(Integer personId) {
		ResultSet results = QueryMethod.getInterestIds(personId);
		Set<Interest> interestSet = new HashSet<>();
		try {
			while (results.next()) {
				interestSet.add(get(results.getInt("interest_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return interestSet;
	}
}
