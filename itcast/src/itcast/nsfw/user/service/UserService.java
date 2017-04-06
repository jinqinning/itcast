package itcast.nsfw.user.service;

import itcast.core.service.BaseService;
import itcast.nsfw.user.entity.User;
import itcast.nsfw.user.entity.UserRole;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

public interface UserService extends BaseService<User>{

	// 导出用户列表
	public void exportExcel(List<User> userList,ServletOutputStream outputStream);

	// 导入用户列表
	public void importExcel(File userExcel, String userExcelFileName);

	// 校验账号的唯一性
	public boolean findUserByAccountAndId(String id, String account);
	//保存用户以及权限
	public void saveUserAndRole(User user, String... roleIds);
	//修改用户及权限
	public void updateUserAndRole(User user, String... roleIds);
	//根据用户id获取对应的所有用户角色
	public List<UserRole> getUserRolesByUserId(String id);
	//根据用户的账号和密码来查询用户列表
	public List<User> findUserByAccountAndPass(String account, String password);
}
