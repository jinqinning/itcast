package test.action;

import javax.annotation.Resource;

import test.service.PersonService;

import com.opensymphony.xwork2.ActionSupport;

public class PersonAction extends ActionSupport {
	@Resource
	private PersonService personService;
	public String save(){
		System.out.println("调试成功");
		return SUCCESS;
	}
}
