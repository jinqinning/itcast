package itcast.core.permission;

import itcast.nsfw.user.entity.User;

public interface PermissionCheck {
	//判断用户是否有code对应的权限
	public boolean isAccessible(User user, String code);
	
}
