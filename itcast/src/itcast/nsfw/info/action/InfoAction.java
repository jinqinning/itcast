package itcast.nsfw.info.action;

import itcast.core.action.BaseAction;
import itcast.core.util.QueryHelper;
import itcast.nsfw.info.entity.Info;
import itcast.nsfw.info.service.InfoService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class InfoAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Resource 
	private InfoService infoService;
	
	private List<Info> infoList;
	private Info info;
	private String[] privilegeIds;
	
	public String listUI(){
		//加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		QueryHelper queryHelper=new QueryHelper(Info.class, "i");
		List<Object> patameters = new ArrayList<Object>();
		if(info != null){
			if(StringUtils.isNotBlank(info.getTitle())){
				queryHelper.addCondition("i.title like ?", "%"+info.getTitle()+"%");
			}
			queryHelper.addCondition("i.state = ?", "1");
		}
		queryHelper.addCondition("i.createTime", QueryHelper.ORDER_BY_DESC);
		infoList = infoService.findObjects(queryHelper);
		return "listUI";
	}
	//跳转到新增页面
	public String addUI(){
		//加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		info=new Info();
		info.setCreateTime(new Timestamp(new Date().getTime()));
		return "addUI";
	}
	//添加新增
	public String add(){
		try {
			if(info != null){
				
				infoService.save(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//删除
	public String delete(){
		if(info != null && info.getInfoId() != null){
			infoService.delete(info.getInfoId());
		}
		return "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow != null ){
			for(String id:selectedRow){
				infoService.delete(id);
			}
		}
		return "list";
	}
	//跳转到编辑页面
	public String editUI(){
		//加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		if(info != null && info.getInfoId() != null ){
			info =infoService.findObjectById(info.getInfoId());
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
			if(info != null ){
				
				infoService.update(info);
			}
		
		return "list";
	}
	//异步发布信息
	public void publicInfo(){
		try {
			if(info != null){
				//1 更新信息状态
				Info tem=infoService.findObjectById(info.getInfoId());
				tem.setState(info.getState());
				infoService.update(tem);
				//2输出跟新结果
				HttpServletResponse response=ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream=response.getOutputStream();
				outputStream.write("更新状态成功".getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public List<Info> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public String[] getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(String[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

}
