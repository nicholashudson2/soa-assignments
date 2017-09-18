package com.cooksys.connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.HashSet;
//import java.util.Set;

import com.cooksys.entity.Interest;
import com.cooksys.entity.Location;
import com.cooksys.entity.Person;

public class QueryMethod {

	public static String addPerson = "INSERT INTO person (first_name, last_name, age, location_id)VALUES (";
	public static String getPerson = "SELECT * FROM person WHERE person.id=";
	public static String addLocation = "INSERT INTO location VALUES ('";
	public static String getLocation = "SELECT * FROM location WHERE location.id=";
	public static String addInterest = "INSERT INTO interest VALUES ('";
	public static String getInterest = "SELECT * FROM interest WHERE interest.id=";
	public static String getAll = "SELECT * FROM ";

	public static ResultSet executeQuery(String query) {
		ResultSet results = null;
		try {
			Statement statement = ConnectionManager.getConnection().createStatement();
			results = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public static void executeUpdate(String query) {
		try {
			Statement statement = ConnectionManager.getConnection().createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void add(Object object) {
		String query = null;
		if (object instanceof Person) {
			Person p = (Person) object;
			if (p.getId() == null) {
				query = addPerson + " '" + p.getFirstName() + "', '" + p.getLastName() + "', " + p.getAge() + ", " + p.getLocationId() + ")";
				for(Interest i : p.getInterests()) {
					add(i);
				}
			} else {
				query = "UPDATE person SET first_name = '" + p.getFirstName() + "', last_name = '" + p.getLastName() + "', age = '" + p.getAge() + "', location_id ='" + p.getLocationId() + "' WHERE id = '" + p.getId() + "'";
				for(Interest i : p.getInterests()) {
					add(i);
				}
			}
		} else if (object instanceof Location) {
			Location l = (Location) object;
			if (l.getId() == null) {
				query = addLocation + l.getCity() + "', '" + l.getState() + "', '" + l.getCountry() + "')";
			} else {
				query = "UPDATE location SET city = '" + l.getCity() + "', state = '" + l.getState() + "', country = '" + l.getCountry() + "' WHERE id = '" + l.getId() + "'";
			}
		} else if (object instanceof Interest) {
			Interest i = (Interest) object;
			if (i.getId() == null) {
				query = addInterest + i.getTitle() + "')";
			} else {
				query = "UPDATE interest SET title = '" + i.getTitle() + "' WHERE id = '" + i.getId() + "'";
			}
		}
		executeUpdate(query);
	}

	public static ResultSet get(String type, Integer id) {
		ResultSet results = null;
		if (type.equals("Person")) {
			results = executeQuery(getPerson + id);
		} else if (type.equals("Location")) {
			results = executeQuery(getLocation + id);
		} else if (type.equals("Interest")) {
			results = executeQuery(getInterest + id);
		}
		return results;
	}

	public static ResultSet getInterestIds(Integer personId) {
		return executeQuery("SELECT interest_id FROM person_interest WHERE person_interest.person_id=" + personId);
	}

	public static ResultSet getInterestGroup(Integer interestId, Integer locationId) {
			return executeQuery("SELECT person_id FROM person_interest JOIN person ON person.id = person_id WHERE location_id ="+ locationId +"AND interest_id =" + interestId);
	}
	
	//
	// public ResultSet getAll(String type) {
	// ResultSet results = null;
	// try {
	// Statement statement =
	// ConnectionManager.getConnection().createStatement();
	// if (type.equals("Person")) {
	// results = statement.executeQuery(getAll + type + ")");
	// } else if (type.equals("Location")) {
	// results = statement.executeQuery(getAll + type + ")");
	// } else if (type.equals("Interest")) {
	// results = statement.executeQuery(getAll + type + ")");
	// }
	// statement.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return results;
	// }
	//
	// public Set<Person> getAllPeople() {
	// ResultSet results = getAll("Person");
	// Set<Person> people = new HashSet<Person>();
	// try {
	// while (results.next()) {
	// people.add(new Person(results.getInt("id"),
	// results.getString("first_name"),
	// results.getString("last_name"), results.getInt("age"),
	// results.getInt("location_id")));
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return people;
	// }
	//
	// public Set<Location> getAllLocations() {
	// ResultSet results = getAll("Location");
	// Set<Location> locations = new HashSet<Location>();
	// try {
	// while (results.next()) {
	// locations.add(new Location(results.getInt("id"),
	// results.getString("city"), results.getString("state"),
	// results.getString("country")));
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return locations;
	// }
	//
	// public Set<Interest> getAllInterests() {
	// ResultSet results = getAll("Interest");
	// Set<Interest> interests = new HashSet<Interest>();
	// try {
	// while (results.next()) {
	// interests.add(new Interest(results.getInt("id"),
	// results.getString("title")));
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return interests;
	// }

}

// QUERY OUTLINE
// select distinct person.id, first_name, last_name, age, location_id from
// (select person_id from
// (select distinct interest_id from
// person_interest group by interest_id having count(interest_id) > 1) as
// duplicate_interests
// join person_interest on duplicate_interests.interest_id =
// duplicate_interests.interest_id) as matched_sets
// join Person on matched_sets.person_id = Person.id
// join Location on Person.location_id = Location.id where Location.city = 'Los
// Angeles'
