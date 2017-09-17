package com.djb.art.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djb.art.model.BookComment;
import com.djb.art.repository.BookCommentRepository;
import com.djb.art.service.BookCommentService;

@Service
public class BookCommentServiceImpl implements BookCommentService {

	@Autowired
	private BookCommentRepository bookCommentRepository;
	
	@Override
	public List<BookComment> getBookComments(Integer bookId) {
		return bookCommentRepository.selectBookComments(bookId);
	}

	@Override
	public Integer createBookComment(BookComment bookComment) {
		return bookCommentRepository.insertBookComment(bookComment);
	}

	@Override
	public List<BookComment> getMyComment(Integer user_id) {
		return bookCommentRepository.selectMyComment(user_id);
	}

	@Override
	public Integer deleteBookComment(BookComment bookComment) {
		return bookCommentRepository.deleteBookComment(bookComment);
	}

}
