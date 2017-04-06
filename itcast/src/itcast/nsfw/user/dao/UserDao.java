package itcast.nsfw.user.dao;

import java.io.Serializable;
import java.util.List;

import itcast.core.dao.BaseDao;
import itcast.nsfw.user.entity.User;
import itcast.nsfw.user.entity.UserRole;

public interface UserDao extends BaseDao<User> {
	//校验唯一性
	public List<User> findUserByAccountAndId(String id, String account);
	//保存用户角色
	public void saveUserRole(UserRole userRole);
	//根据用户id删除该用户角色
	public void deleteUserRoleById(Serializable id);
	//根据用户id获取对应的所有用户角色
	public List<UserRole> getUserRolesByUserId(String id);
	//通过账户和密码查询用户
	public List<User> findUserByAccountAndPass(String account, String password);

}
