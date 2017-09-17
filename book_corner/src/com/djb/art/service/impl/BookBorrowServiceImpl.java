package com.djb.art.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djb.art.model.BookBorrow;
import com.djb.art.model.BookBorrowOption;
import com.djb.art.repository.BookBorrowRepository;
import com.djb.art.service.BookBorrowService;

@Service
public class BookBorrowServiceImpl implements BookBorrowService {

	@Autowired
	private BookBorrowRepository bookBorrowRepository;

	@Override
	public List<BookBorrow> getBookBorrows(Integer userId) {
		return bookBorrowRepository.selectBookBorrows(userId);
	}

	@Override
	public Integer createBookBorrow(BookBorrow bookBorrow) {
		return bookBorrowRepository.insertBookBorrow(bookBorrow);
	}

	@Override
	public Integer deleteBookBorrows(BookBorrow bookBorrow) {
		return bookBorrowRepository.deleteBookBorrow(bookBorrow);
	}

	@Override
	public List<BookBorrow> getMyBorrow(Integer userId) {
		return bookBorrowRepository.selectMyBorrow(userId);
	}

	@Override
	public Integer updatePlanReturnDate(BookBorrow bookBorrow) {
		return bookBorrowRepository.updatePlanReturnDate(bookBorrow);
	}

	@Override
	public Integer returnBook(BookBorrow bookBorrow) {
		return bookBorrowRepository.returnBook(bookBorrow);
	}

	@Override
	public List<BookBorrow> getBorrowByBookId(BookBorrowOption bookBorrowOption) {
		return bookBorrowRepository.selectBorrowByBookId(bookBorrowOption);
	}

	@Override
	public List<BookBorrow> getBorrowRecord(BookBorrowOption bookBorrowOption) {
		return bookBorrowRepository.selectBorrowRecord(bookBorrowOption);
	}

	@Override
	public List<BookBorrow> getBorrowsByByUserId(Integer userId) {
		return bookBorrowRepository.selectBorrowsByByUserId(userId);
	}

	@Override
	public String getPlanReturnDate(BookBorrowOption bookBorrowOption) {
		return bookBorrowRepository.selectPlanReturnDate(bookBorrowOption);
	}


	


}
