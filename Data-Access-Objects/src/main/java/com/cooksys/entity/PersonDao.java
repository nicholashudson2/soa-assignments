package com.cooksys.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.cooksys.connection.QueryMethod;

public class PersonDao {

	public static Person get(Integer id) {
		ResultSet results = QueryMethod.get("Person", id);
		Person person = null;
		try {
			while (results.next()) {
				person = new Person(results.getInt("id"), results.getString("first_name"),
						results.getString("last_name"), results.getInt("age"), results.getInt("location_id"), LocationDao.get(results.getInt("location_id")), InterestDao.getInterestSet(id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return person;
	}
	
	public static void save(Person person) {
		QueryMethod.add(person);
	}
	
	public Set<Person> findInterestGroups(Interest interest, Location location) {
		Set<Person> interestGroup = new HashSet<Person>();
		ResultSet results = QueryMethod.getInterestGroup(interest.getId(), location.getId());
		try {
			while (results.next()) {
				interestGroup.add(get(results.getInt("person_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return interestGroup;
	}
}
