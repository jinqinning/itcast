package itcast.nsfw.role.dao;

import itcast.core.dao.BaseDao;
import itcast.nsfw.role.entity.Role;

public interface RoleDao extends BaseDao<Role> {
	//根据id删除对应的所有权限
	public void deleteRolePrivilegeByRoleId(String roleId);
	
}
