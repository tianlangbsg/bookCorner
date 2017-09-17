package com.djb.art.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djb.art.model.Book;
import com.djb.art.model.BookChartParam;
import com.djb.art.model.BookCount;
import com.djb.art.model.BookOption;
import com.djb.art.repository.BookRepository;
import com.djb.art.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public Integer createBook(Book user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateBook(Book user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteBook(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book getBookById(Integer id) {
		return bookRepository.selectBookById(id);
	}

	@Override
	public Book getBookByType(String type) {
		return bookRepository.selectBookByType(type);
	}

	//分页查询
	@Override
	public List<Book> getBooks(String name, Integer start, Integer limit) {
		return bookRepository.selectBooksByLimit(name,start,limit);
	}

	@Override
	public List<Book> getAllBooks(Integer start) {
		return bookRepository.selectAllBooks(start);
	}

	@Override
	public List<Book> getBookByNameOrBarcode(String bookNameOrBarcode) {
		return bookRepository.selectBookByNameOrBarcode(bookNameOrBarcode);
	}

	@Override
	public List<Book> getBooksByOptions(BookOption bookOption) {
		return bookRepository.selectBooksByOptions(bookOption);
	}

	@Override
	public List<Book> getBooksByOptionsCount(BookOption bookOption) {
		return bookRepository.selectBooksByOptionsCount(bookOption);
	}
	
	@Override
	public List<Book> getBooksByOptionsManager(BookOption bookOption) {
		return bookRepository.selectBooksByOptionsManager(bookOption);
	}
	
	@Override
	public List<Book> getBooksByOptionsCountManager(BookOption bookOption) {
		return bookRepository.selectBooksByOptionsCountManager(bookOption);
	}

	@Override
	public BookCount getBookCount() {
		return bookRepository.selectBookCount();
	}

	@Override
	public List<Book> getLastMonthChartBooks() {
		return bookRepository.selectLastMonthChartBooks();
	}

	@Override
	public List<Book> getBorrowChartBooks() {
		return bookRepository.selectBorrowChartBooks();
	}

	@Override
	public List<Book> getFavoriteChartBooks() {
		return bookRepository.selectFavoriteChartBooks();
	}

	@Override
	public List<Book> getHotChartBooks() {
		return bookRepository.selectHotChartBooks();
	}

	@Override
	public Integer enableBorrowStatus(Integer book_id) {
		return bookRepository.enableBorrowStatus(book_id);
	}

	@Override
	public Integer disableBorrowStatus(Integer book_id) {
		return bookRepository.disableBorrowStatus(book_id);
	}

	@Override
	public List<Book> getMyFavorite(Integer user_id) {
		return bookRepository.selectMyFavorite(user_id);
	}

	@Override
	public List<Book> getMyPraise(Integer user_id) {
		return bookRepository.selectMyPraise(user_id);
	}

	@Override
	public List<Book> getAllChartBooks(BookChartParam bookChartParam) {
		return bookRepository.selectAllChartBooks(bookChartParam);
	}

	@Override
	public BookCount getAllChartBooksCount(BookChartParam bookChartParam) {
		return bookRepository.selectAllChartBooksCount(bookChartParam);
	}

	@Override
	public Book getLastBook() {
		return bookRepository.selectLastBook();
	}

	@Override
	public BookCount getAllBookCount() {
		return bookRepository.selectAllBookCount();
	}

	@Override
	public Book getEditBookById(Integer book_id) {
		return bookRepository.selectEditBookById(book_id);
	}

}
