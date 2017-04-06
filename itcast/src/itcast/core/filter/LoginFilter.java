package itcast.core.filter;

import itcast.core.constant.Constant;
import itcast.core.permission.PermissionCheck;
import itcast.nsfw.user.entity.User;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {

	}
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response=(HttpServletResponse) servletResponse;
		HttpServletRequest request=(HttpServletRequest) servletRequest;
		String uri=request.getRequestURI();
		//判断当前请求地址是否是登录请求
		if(!uri.contains("sys/login_")){
			//非登录请求
			if(request.getSession().getAttribute(Constant.USER)!= null){
				//说明已经登录过
				//判断是否访问纳税服务系统的权限
				if(uri.contains("/nsfw/")){
					User user=(User)request.getSession().getAttribute(Constant.USER);
					WebApplicationContext webApplicationContext=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
					PermissionCheck pc=(PermissionCheck)webApplicationContext.getBean("permissionCheck");
					if(pc.isAccessible(user,"nsfw")){
						//有权限,放行
						chain.doFilter(request, response);
					}else{
						//没有权限,跳转到没有权限的页面
						response.sendRedirect(request.getContextPath()+"/sys/login_toNoPermissionUI.action");
					}
				}else{
					//不是访问纳税服务,直接放行
					chain.doFilter(request, response);
				}
				
			}else{
				//说明没有登录过,跳转到登录界面
				response.sendRedirect(request.getContextPath()+"/sys/login_toLoginUI.action");
			}
		}else{
			//登录请求
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
