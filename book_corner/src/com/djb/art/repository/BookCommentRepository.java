package com.djb.art.repository;

import java.util.List;

import com.djb.art.cms.annotations.MybatisRepository;
import com.djb.art.model.BookComment;

@MybatisRepository
public interface BookCommentRepository {

	public List<BookComment> selectBookComments(Integer bookId);

	public Integer insertBookComment(BookComment bookComment);

	public List<BookComment> selectMyComment(Integer userId);

	public Integer deleteBookComment(BookComment bookComment);

}
