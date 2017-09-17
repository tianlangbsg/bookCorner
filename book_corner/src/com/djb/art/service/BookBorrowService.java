package com.djb.art.service;

import java.util.List;

import com.djb.art.model.BookBorrow;
import com.djb.art.model.BookBorrowOption;

public interface BookBorrowService {

	public List<BookBorrow> getBookBorrows(Integer userId);

	public Integer createBookBorrow(BookBorrow bookBorrow);

	public Integer deleteBookBorrows(BookBorrow bookBorrow);
	
	//用户中心借阅详情
	public List<BookBorrow> getMyBorrow(Integer userId);
	
	//根据图书ID查看借阅历史，带条件查询
	public List<BookBorrow> getBorrowByBookId(BookBorrowOption bookBorrowOption);
	
	//所有的借阅记录查询，带条件
	public List<BookBorrow> getBorrowRecord(BookBorrowOption bookBorrowOption);
	
	//修改计划还书日期
	public Integer updatePlanReturnDate(BookBorrow bookBorrow);
	
	//还书
	public Integer returnBook(BookBorrow bookBorrow);

	//查询指定用户的借阅书单
	public List<BookBorrow> getBorrowsByByUserId(Integer userId);

	//查询用户本书的计划还书时间
	public String getPlanReturnDate(BookBorrowOption bookBorrowOption);
}
