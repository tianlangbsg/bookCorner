package com.djb.art.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djb.art.model.User;
import com.djb.art.repository.UserRepository;
import com.djb.art.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public Integer createUser(User user) {
		return userRepository.insertUser(user);
	}

	@Override
	public Integer deleteUser(Integer id) {
		return userRepository.deleteUserById(id);
	}

	@Override
	public User getUserById(Integer id) {
		return userRepository.selectUserById(id);
	}
	@Override
	public User getUserByCode(String code) {
		return userRepository.selectUserByCode(code);
	}
	@Override
	public List<User> getUser(String partName, Integer start, Integer limit) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("partName", partName);
		conditions.put("start", start);
		conditions.put("limit", limit);
		return userRepository.selectUsers(conditions);
	}
	@Override
	public List<User> getUsers() {
	
		return userRepository.selectAllUsers();
	}

	@Override
	public Integer updateUser(User user) {
		return userRepository.updateUser(user);
	}

	@Override
	public User selectBlogByUserId(Integer id) {
		return userRepository.selectBlogByUserId(id);
	}

	@Override
	public User getUserByCodeAndFullname(String code, String fullname) {
		return userRepository.selectUserByCodeAndFullname(code,fullname);
	}

	@Override
	public User getUserBookInfoById(Integer user_id) {
		return userRepository.selectUserBookInfoById(user_id);
	}

	@Override
	public List<User> getVaildUsers() {
		return userRepository.selectVaildUsers();
	}

	@Override
	public List<User> getInvaildUsers() {
		return userRepository.selectInvaildUsers();
	}

	@Override
	public Integer enableUser(User user) {
		return userRepository.enableUser(user);
	}

	@Override
	public Integer disableUser(User user) {
		return userRepository.disableUser(user);
	}

	@Override
	public List<User> getUserBorrowchart() {
		return userRepository.selectUserBorrowchart();
	}

	@Override
	public List<User> getUserChartJoinAtAsc() {
		return userRepository.selectUserChartJoinAtAsc();
	}

	@Override
	public List<User> getUserChartJoinAtDesc() {
		return userRepository.selectUserChartJoinAtDesc();
	}

	@Override
	public List<User> getBookManagers() {
		return userRepository.selectBookManagers();
	}

	@Override
	public List<User> getNotBookManagers() {
		return userRepository.selectNotBookManagers();
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.selectAllUsers();
	}

	@Override
	public Integer getUserStaystillCount(Integer userId) {
		return userRepository.selectUserStaystillCount(userId);
	}

}
