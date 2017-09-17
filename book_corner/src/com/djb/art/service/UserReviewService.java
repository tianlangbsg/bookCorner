package com.djb.art.service;

import java.util.List;

import com.djb.art.model.User;
import com.djb.art.model.UserReview;

public interface UserReviewService {

	//查询申请用户的申请状态
	public UserReview selectUserReviewStatus(UserReview userReview);

	//插入申请记录
	public Integer createUserReview(UserReview userReview);

	//查询未审核的用户列表
	public List<UserReview> getNotReviewUsers();

	//查询通过审核的用户列表
	public List<UserReview> getAdoptReviewUsers();

	//查询申请被拒绝的用户列表
	public List<UserReview> getRejectReviewUsers();

	//通过用户审核
	public Integer adoptReviewUser(int reviewId);
	
	//拒绝用户审核
	public Integer rejectReviewUser(int reviewId);

	//根据user_code查询用户审核表中是否存在用户
	public UserReview getUserByCode(String code);

	//根据记录ID取出待审核用户信息
	public UserReview getUserById(Integer id);
}
