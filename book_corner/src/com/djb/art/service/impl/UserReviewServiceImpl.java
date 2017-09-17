package com.djb.art.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djb.art.model.User;
import com.djb.art.model.UserReview;
import com.djb.art.repository.UserReviewRepository;
import com.djb.art.service.UserReviewService;

@Service
public class UserReviewServiceImpl implements UserReviewService {

	@Autowired
	private UserReviewRepository userReviewRepository;

	@Override
	public UserReview selectUserReviewStatus(UserReview userReview) {
		return userReviewRepository.selectUserReviewById(userReview);
	}

	@Override
	public Integer createUserReview(UserReview userReview) {
		return userReviewRepository.insertUserReview(userReview);
	}

	@Override
	public List<UserReview> getNotReviewUsers() {
		return userReviewRepository.selectNotReviewUsers();
	}

	@Override
	public List<UserReview> getAdoptReviewUsers() {
		return userReviewRepository.selectAdoptReviewUsers();
	}

	@Override
	public List<UserReview> getRejectReviewUsers() {
		return userReviewRepository.selectRejectReviewUsers();
	}

	@Override
	public Integer adoptReviewUser(int reviewId) {
		return userReviewRepository.adoptReviewUser(reviewId);
	}

	@Override
	public Integer rejectReviewUser(int reviewId) {
		return userReviewRepository.rejectReviewUser(reviewId);
	}

	@Override
	public UserReview getUserByCode(String code) {
		return userReviewRepository.selectUserByCode(code);
	}

	@Override
	public UserReview getUserById(Integer id) {
		return userReviewRepository.selectUserById(id);
	}
	

}
