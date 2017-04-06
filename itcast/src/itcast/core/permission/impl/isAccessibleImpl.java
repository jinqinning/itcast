package itcast.core.permission.impl;

import java.util.List;

import javax.annotation.Resource;

import itcast.core.permission.PermissionCheck;
import itcast.nsfw.role.entity.Role;
import itcast.nsfw.role.entity.RolePrivilege;
import itcast.nsfw.user.entity.User;
import itcast.nsfw.user.entity.UserRole;
import itcast.nsfw.user.service.UserService;

public class isAccessibleImpl implements PermissionCheck {

	@Resource
	private UserService userService;
	@Override
	public boolean isAccessible(User user, String code) {
		//1 获取用户所有的角色
		List<UserRole> list=user.getUserRoles();
		if(list==null){
			list=userService.getUserRolesByUserId(user.getId());
		}
		//2根据每个角色的所有的权限进行对比
		if(list !=null&&list.size()>0){
			for(UserRole ur:list){
				Role role=ur.getId().getRole();
				for(RolePrivilege rp:role.getRolePrivileges()){
					if(code.equals(rp.getId().getCode())){
						//说明有权限,返回true
						return true;
					}
				}
			}
		}
		return false;
	}

}
