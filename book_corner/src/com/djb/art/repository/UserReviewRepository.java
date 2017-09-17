package com.djb.art.repository;

import java.util.List;

import com.djb.art.cms.annotations.MybatisRepository;
import com.djb.art.model.User;
import com.djb.art.model.UserReview;

@MybatisRepository
public interface UserReviewRepository {

	public UserReview selectUserReviewById(UserReview userReview);

	public Integer insertUserReview(UserReview userReview);

	//查询未审核的用户列表
	public List<UserReview> selectNotReviewUsers();

	//查询通过审核的用户列表
	public List<UserReview> selectAdoptReviewUsers();

	//查询申请被拒绝的用户列表
	public List<UserReview> selectRejectReviewUsers();

	//通过用户审核
	public Integer adoptReviewUser(Integer reviewId);
	
	//拒绝用户审核
	public Integer rejectReviewUser(Integer reviewId);

	//在用户审核表中根据user_code查询用户
	public UserReview selectUserByCode(String code);

	//根据记录ID查询待审核用户信息
	public UserReview selectUserById(Integer id);
	
}
