package test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import test.entity.Person;
import test.service.PersonService;

public class testSpring {
	ClassPathXmlApplicationContext ctx;

	@Before
	public void loadCtx() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	@Test
	public void test1(){
		PersonService personService=(PersonService)ctx.getBean("personService");
		Person person=new Person("test");
		personService.save(person);
	}
}
