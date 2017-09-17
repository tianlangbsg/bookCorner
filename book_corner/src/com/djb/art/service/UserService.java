package com.djb.art.service;

import java.util.List;

import com.djb.art.model.User;

public interface UserService {

	public Integer createUser(User user);

	public Integer updateUser(User user);

	public Integer deleteUser(Integer id);

	public User getUserById(Integer id);
	
	public User getUserByCode(String code);

	public User getUserByCodeAndFullname(String code,String fullname);

	public List<User> getUser(String name, Integer start, Integer limit);
	
	public List<User> getUsers();
	
	public User selectBlogByUserId(Integer id);
	
	//取得所有成员名单
	public List<User> getAllUsers();
	
	//查询用户中心的收藏、借阅等等的总数
	public User getUserBookInfoById(Integer user_id);

	//取得有效成员名单
	public List<User> getVaildUsers();
	
	//取得失效成员名单
	public List<User> getInvaildUsers();

	//使用户生效
	public Integer enableUser(User user);
	
	//使用户失效
	public Integer disableUser(User user);

	//获取用户借阅排行榜
	public List<User> getUserBorrowchart();

	//用户排行(加入时间升序)
	public List<User> getUserChartJoinAtAsc();
	
	//用户排行(加入时间降序)
	public List<User> getUserChartJoinAtDesc();

	//取得所有具有管理员资格的用户
	public List<User> getBookManagers();

	//取得所有非管理员资格的用户
	public List<User> getNotBookManagers();
	
	//取得用户未还的图书总数
	public Integer getUserStaystillCount(Integer userId);
	
}
