package itcast.nsfw.user.service.impl;

import itcast.core.service.impl.BaseServiceImpl;
import itcast.core.util.ExcelUtil;
import itcast.nsfw.role.entity.Role;
import itcast.nsfw.user.dao.UserDao;
import itcast.nsfw.user.dao.impl.UserDaoImpl;
import itcast.nsfw.user.entity.User;
import itcast.nsfw.user.entity.UserRole;
import itcast.nsfw.user.entity.UserRoleId;
import itcast.nsfw.user.service.UserService;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.springframework.stereotype.Service;

import com.sun.org.apache.regexp.internal.recompile;
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	
	private UserDao userDao;
	
	@Resource
	public void setUserDao(UserDao userDao) {
		super.setBaseDao(userDao);
		this.userDao = userDao;
	}

	@Override
	public void delete(Serializable id) {
		userDao.delete(id);
		//删除用户对应的权限
		userDao.deleteUserRoleById(id);
	}

	@Override
	public void exportExcel(List<User> userList,ServletOutputStream outputStream) {
		ExcelUtil.exportUserExcel(userList, outputStream);
	}

	@Override
	public void importExcel(File userExcel, String userExcelFileName) {
		List<User> userList=ExcelUtil.importExcel(userExcel, userExcelFileName);
		for(int i=0;i<userList.size();i++){
			save(userList.get(i));
		}
	}

	@Override
	public boolean findUserByAccountAndId(String id, String account) {
		List<User> userList=userDao.findUserByAccountAndId(id, account);
		if(userList.size()<=0){
			return true;
		}
		return false;
	}

	@Override
	public void saveUserAndRole(User user, String... roleIds) {
		//1 保存用户
		save(user);
		//2保存用户角色
		if(roleIds != null){
			for(String roleId :roleIds){
				userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId),user.getId())));
			}
		}
	}

	@Override
	public void updateUserAndRole(User user, String... roleIds) {
		//1 根据用户删除用户所有角色
		userDao.deleteUserRoleById(user.getId());
		//2 更新用户
		update(user);
		//3 保存用户对应的角色
		if(roleIds != null){
			for(String roleId :roleIds){
				userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId),user.getId())));
			}
		}
	}

	@Override
	public List<UserRole> getUserRolesByUserId(String id) {
		return userDao.getUserRolesByUserId(id);
	}

	@Override
	public List<User> findUserByAccountAndPass(String account, String password) {
		return userDao.findUserByAccountAndPass(account, password);
	}
}
