package test.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import test.dao.PersonDao;
import test.entity.Person;
import test.service.PersonService;
@Service("personService")
public class PersonServiceImpl implements PersonService {
	@Resource
	private PersonDao personDao;
	
	@Override
	public void save(Person person) {
		personDao.save(person);

	}

	@Override
	public void update(Person person) {
		personDao.update(person);

	}

	@Override
	public void delete(Serializable id) {
		personDao.delete(id);

	}

	@Override
	public Person findObjectById(Serializable id) {
		
		return personDao.findObjectById(id);
	}

	@Override
	public List<Person> findObjects() {
		
		return personDao.findObjects();
	}

}
