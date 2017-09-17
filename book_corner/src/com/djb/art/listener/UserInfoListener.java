package com.djb.art.listener;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.djb.art.model.SystemManager;
import com.djb.art.model.User;

@WebListener
public class UserInfoListener implements HttpSessionAttributeListener{
//
//	@Override
//	public void sessionCreated(HttpSessionEvent event) {
//		System.err.println("连接已经创建");
//	}
//
//	@Override
//	public void sessionDestroyed(HttpSessionEvent event) {
//		HttpSession session = event.getSession();
//		ServletContext application = session.getServletContext();
//		User user = (User) session.getAttribute("user");
//		SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
//		if(user!=null) {
//			application.removeAttribute(user.getUserCode());
//			System.err.println(user.getUserFullname()+"("+user.getUserCode()+")已退出登录"+(new Date().toString()));
//		}
//		if(systemManager!=null) {
//			application.removeAttribute("systemManager"+systemManager.getManagerId());
//			System.err.println(systemManager.getManagerName()+"("+systemManager.getManagerId()+")已退出登录,退出时间"+(new Date().toString()));
//		}
//	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
//		HttpSession session = event.getSession();
//		ServletContext application = session.getServletContext();
//		User user = (User) session.getAttribute("user");
//		SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
//		if(user!=null) {
//			//application.setAttribute(user.getUserCode(), user.getUserCode());
//			System.err.println("用户："+user.getUserFullname()+"("+user.getUserCode()+")已登录,登录时间"+(new Date().toString()));
//		}
//		if(systemManager!=null) {
//			//application.removeAttribute("systemManager"+systemManager.getManagerId());
//			System.err.println("管理员："+systemManager.getManagerName()+"("+systemManager.getManagerId()+")已登录,登录时间"+(new Date().toString()));
//		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		User user = (User) session.getAttribute("user");
		SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
		if(user!=null) {
			//application.setAttribute(user.getUserCode(), user.getUserCode());
			System.err.println("用户："+user.getUserFullname()+"("+user.getUserCode()+")已登录,登录时间"+(new Date().toString()));
		}
		if(systemManager!=null) {
			//application.removeAttribute("systemManager"+systemManager.getManagerId());
			System.err.println("管理员："+systemManager.getManagerName()+"("+systemManager.getManagerId()+")已登录,登录时间"+(new Date().toString()));
		}
	} 

}
