package itcast.nsfw.user.action;

import itcast.core.action.BaseAction;
import itcast.nsfw.role.service.RoleService;
import itcast.nsfw.user.entity.User;
import itcast.nsfw.user.entity.UserRole;
import itcast.nsfw.user.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;

import com.opensymphony.xwork2.ActionContext;



public class UserAction extends BaseAction {
	@Resource 
	private UserService userService;
	@Resource
	private RoleService roleService;
	private List<User> userList;
	private User user;
	
	
	//保存图片
	private File headImg;
	private String headImgContentType;
	private String headImgFileName;
	//导入文档
	private File userExcel;
	private String userExcelContentType;
	private String userExcelFileName;
	
	private String[] userRoleIds;
	//列表页面
	public String listUI(){
		userList=userService.findObjects();
		return "listUI";
	}
	//跳转到新增页面
	public String addUI(){
		//加载角色列表
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		return "addUI";
	}
	//添加
	public String add(){
		try {
			if(user != null){
				if(headImg != null ){
					//1 保存到upload/user
					//获取保存路径的绝对地址
					String filePath=ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName=UUID.randomUUID().toString().replace("-", "")+headImgFileName.substring(headImgFileName.lastIndexOf("."));
					//复制文件
					FileUtil.copyFile(headImg, new File(filePath, fileName));
					//2 设置图像路径
					user.setHeadImg("user/"+fileName);
					
				}
				userService.saveUserAndRole(user,userRoleIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//删除
	public String delete(){
		if(user != null && user.getId() != null){
			userService.delete(user.getId());
		}
		return "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow != null ){
			for(String id:selectedRow){
				userService.delete(id);
			}
		}
		return "list";
	}
	//跳转到编辑页面
	public String editUI(){
		//加载角色列表
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		if(user != null && user.getId() != null ){
			user =userService.findObjectById(user.getId());
			//处理角色回显
			List<UserRole> list=userService.getUserRolesByUserId(user.getId());
			if(list != null && list.size()>0){
				userRoleIds=new String[list.size()];
				for(int i=0;i<list.size();i++){
					userRoleIds[i]=list.get(i).getId().getRole().getRoleId();
				}
			}
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		try {
			if(user != null ){
				if(headImg != null ){
					//1 保存到upload/user
					//获取保存路径的绝对地址
					String filePath=ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName=UUID.randomUUID().toString().replace("-", "")+headImgFileName.substring(headImgFileName.lastIndexOf("."));
					//复制文件
					FileUtil.copyFile(headImg, new File(filePath, fileName));
					//2 设置图像路径
					user.setHeadImg("user/"+fileName);
				}
				userService.updateUserAndRole(user,userRoleIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//导出用户列表
	public void exportExcel(){
		try {
			//1 查找用户列表
			userList=userService.findObjects();
			//2 导出
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("application/x-execl");
			response.setHeader("Content-Disposition", "attachment;filename="+new String("用户列表.xlsx".getBytes(),"ISO-8859-1"));
			ServletOutputStream outputStream=response.getOutputStream();
			userService.exportExcel(userList,outputStream);
			if(outputStream !=null){
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//导入用户列表
	public String importExcel(){
		//1 获取excel文件
		if(userExcel != null){
			//是否是excel文件
			if(userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
				//导入
				userService.importExcel(userExcel,userExcelFileName);
			}
		}
		return "list";
	}
	//检验账号的唯一性
	public void verifyAccount(){
		try {
			if(user != null && StringUtils.isNotBlank(user.getAccount())){
				String strResult="false";
				if(userService.findUserByAccountAndId(user.getId(),user.getAccount())){
					strResult="true";
				}
				HttpServletResponse response=ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream=response.getOutputStream();
				System.out.println("返回给ajax的值为"+strResult);
				outputStream.write(strResult.getBytes());
				outputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public File getHeadImg() {
		return headImg;
	}
	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}
	public String getHeadImgContentType() {
		return headImgContentType;
	}
	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}
	public String getHeadImgFileName() {
		return headImgFileName;
	}
	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}
	public File getUserExcel() {
		return userExcel;
	}
	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}
	public String getUserExcelContentType() {
		return userExcelContentType;
	}
	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}
	public String getUserExcelFileName() {
		return userExcelFileName;
	}
	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}
	public String[] getUserRoleIds() {
		return userRoleIds;
	}
	public void setUserRoleIds(String[] userRoleIds) {
		this.userRoleIds = userRoleIds;
	}
	
}
