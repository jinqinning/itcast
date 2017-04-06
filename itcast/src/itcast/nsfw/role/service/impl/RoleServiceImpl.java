package itcast.nsfw.role.service.impl;

import itcast.core.service.impl.BaseServiceImpl;
import itcast.nsfw.role.dao.RoleDao;
import itcast.nsfw.role.entity.Role;
import itcast.nsfw.role.service.RoleService;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	
	private RoleDao roleDao;
	
	@Resource
	public void setRoleDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
		this.roleDao = roleDao;
	}
	
	@Override
	public void update(Role role) {
		//1 删除该角色的所有权限
		roleDao.deleteRolePrivilegeByRoleId(role.getRoleId());
		roleDao.update(role);
	}
}
