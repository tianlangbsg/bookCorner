package com.djb.art.repository;

import java.util.List;

import com.djb.art.cms.annotations.MybatisRepository;
import com.djb.art.model.BookBorrow;
import com.djb.art.model.BookBorrowOption;

@MybatisRepository
public interface BookBorrowRepository {

	public Integer insertBookBorrow(BookBorrow bookBorrow);

	public Integer deleteBookBorrow(BookBorrow bookBorrow);

	public List<BookBorrow> selectBookBorrows(Integer userId);

	public List<BookBorrow> selectMyBorrow(Integer userId);

	public Integer updatePlanReturnDate(BookBorrow bookBorrow);

	public Integer returnBook(BookBorrow bookBorrow);

	public List<BookBorrow> selectBorrowByBookId(BookBorrowOption bookBorrowOption);

	public List<BookBorrow> selectBorrowRecord(BookBorrowOption bookBorrowOption);

	//查找指定用户的借阅书单
	public List<BookBorrow> selectBorrowsByByUserId(Integer userId);

	//取出用户的本书的还书时间
	public String selectPlanReturnDate(BookBorrowOption bookBorrowOption);

}
