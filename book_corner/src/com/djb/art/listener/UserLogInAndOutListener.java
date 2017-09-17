package com.djb.art.listener;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.djb.art.model.SystemManager;
import com.djb.art.model.User;

@WebListener
public class UserLogInAndOutListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.err.println("新的用戶創建了連接");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		//取出服务器全局对象
		ServletContext application = session.getServletContext();
		User user = (User) session.getAttribute("user");
		SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
		if(user!=null) {
			@SuppressWarnings("unchecked")
			ConcurrentHashMap<String, String> onlineUserMap = (ConcurrentHashMap<String, String>) application.getAttribute("onlineUserMap");
			if(onlineUserMap!=null) {
				onlineUserMap.remove(user.getUserCode());
			}
			System.err.println("用戶："+user.getUserFullname()+"("+user.getUserCode()+")已退出登錄，退出時間"+(new Date().toString()));
		}
		if(systemManager!=null) {
			application.removeAttribute("systemManager"+systemManager.getManagerId());
			System.err.println("管理员："+systemManager.getManagerName()+"("+systemManager.getManagerId()+")已退出登錄,退出時間"+(new Date().toString()));
		}
	} 

}
