package com.djb.art.repository;

import java.util.List;
import java.util.Map;

import com.djb.art.cms.annotations.MybatisRepository;
import com.djb.art.model.User;

@MybatisRepository
public interface UserRepository {

	public Integer insertUser(User user);
	
	//更新用户信息
	public Integer updateUser(User user);
	
	public Integer deleteUserById(Integer id);
	
	public List<User> selectUsers(Map<String, Object> conditions);
	
	public User selectUserById(Integer id);
	
	public User selectUserByCode(String code);
	
	public List<User> selectAllUsers();
	
	public User selectBlogByUserId(Integer id);

	public User selectUserByCodeAndFullname(String code,String name);

	public User selectUserBookInfoById(Integer userId);

	//取得有效成员名单
	public List<User> selectVaildUsers();

	//取得失效成员名单
	public List<User> selectInvaildUsers();
	
	//使用户生效
	public Integer enableUser(User user);
	
	//使用户失效
	public Integer disableUser(User user);

	//获取用户借阅排行榜
	public List<User> selectUserBorrowchart();

	//用户排行(加入时间升序)
	public List<User> selectUserChartJoinAtAsc();
	
	//用户排行(加入时间降序)
	public List<User> selectUserChartJoinAtDesc();

	//查询具有管理员资格的用户
	public List<User> selectBookManagers();

	//查询非管理员资格的用户
	public List<User> selectNotBookManagers();

	//查询用户未归还的图书总数
	public Integer selectUserStaystillCount(Integer userId);
 
}
