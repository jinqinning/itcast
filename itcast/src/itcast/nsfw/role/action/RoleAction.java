package itcast.nsfw.role.action;

import java.util.HashSet;
import java.util.List;

import itcast.core.action.BaseAction;
import itcast.core.constant.Constant;
import itcast.nsfw.role.entity.Role;
import itcast.nsfw.role.entity.RolePrivilege;
import itcast.nsfw.role.entity.RolePrivilegeId;
import itcast.nsfw.role.service.RoleService;









import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;




public class RoleAction extends BaseAction {
	@Resource 
	private RoleService roleService;
	
	private List<Role> roleList;
	private Role role;
	private String[] privilegeIds;
	
	public String listUI(){
		//加载权限集合
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		roleList = roleService.findObjects();
		return "listUI";
	}
	//跳转到新增页面
	public String addUI(){
		//加载权限集合
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		
		return "addUI";
	}
	//添加新增
	public String add(){
		try {
			if(role != null){
				//处理权限保存
				if(privilegeIds != null){
					HashSet<RolePrivilege> set=new HashSet<RolePrivilege>();
					for(int i=0;i<privilegeIds.length;i++){
						set.add(new RolePrivilege(new RolePrivilegeId(privilegeIds[i],role)));
					}
					role.setRolePrivileges(set);
				}
				roleService.save(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//删除
	public String delete(){
		if(role != null && role.getRoleId() != null){
			roleService.delete(role.getRoleId());
		}
		return "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow != null ){
			for(String id:selectedRow){
				roleService.delete(id);
			}
		}
		return "list";
	}
	//跳转到编辑页面
	public String editUI(){
		//加载权限集合
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		if(role != null && role.getRoleId() != null ){
			role =roleService.findObjectById(role.getRoleId());
			//处理权限回显
			if(role.getRolePrivileges() != null){
				privilegeIds=new String[role.getRolePrivileges().size()];
				int i=0;
				for(RolePrivilege rp:role.getRolePrivileges()){
					privilegeIds[i++]=rp.getId().getCode();
				}
			}
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
			if(role != null ){
				//处理权限保存
				if(privilegeIds != null){
					HashSet<RolePrivilege> set=new HashSet<RolePrivilege>();
					for(int i=0;i<privilegeIds.length;i++){
						set.add(new RolePrivilege(new RolePrivilegeId(privilegeIds[i],role)));
					}
					role.setRolePrivileges(set);
				}
				roleService.update(role);
			}
		
		return "list";
	}
	
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(String[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	
}

