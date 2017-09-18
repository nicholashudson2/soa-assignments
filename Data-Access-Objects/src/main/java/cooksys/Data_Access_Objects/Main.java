package cooksys.Data_Access_Objects;

import com.cooksys.entity.InterestDao;
import com.cooksys.entity.LocationDao;
import com.cooksys.entity.Person;
import com.cooksys.entity.PersonDao;

public class Main {

	public static void main(String[] args) {
		Person person = new Person(null, "Nick", "Hudson", 29, 5, LocationDao.get(5), InterestDao.getInterestSet(5));
		PersonDao.save(person);
	}

}
