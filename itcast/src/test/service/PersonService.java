package test.service;

import java.io.Serializable;
import java.util.List;

import test.entity.Person;

public interface PersonService {
	//新增
	public void save(Person person);
	//更新
	public void update(Person person);
	//根据id删除
	public void delete(Serializable id);
	//根据id查找
	public Person findObjectById(Serializable id);
	//查找列表
	public List<Person> findObjects();
}
