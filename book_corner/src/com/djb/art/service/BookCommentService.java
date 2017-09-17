package com.djb.art.service;

import java.util.List;

import com.djb.art.model.BookComment;

public interface BookCommentService {

	public List<BookComment> getBookComments(Integer bookId);

	public Integer createBookComment(BookComment bookComment);
	
	//用户中心的书评列表
	public List<BookComment> getMyComment(Integer user_id);

	//删除用户评论
	public Integer deleteBookComment(BookComment bookComment);
}
