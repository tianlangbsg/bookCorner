package com.djb.art.repository;

import java.util.List;
import java.util.Map;

import com.djb.art.cms.annotations.MybatisRepository;
import com.djb.art.model.Book;
import com.djb.art.model.BookChartParam;
import com.djb.art.model.BookCount;
import com.djb.art.model.BookOption;

@MybatisRepository
public interface BookRepository {

	public Integer insertBook(Book book);
	
	public Integer updateBook(Book book);
	
	public Integer deleteBookById(Integer id);
		
	public List<Book> selectBooks(Map<String, Object> conditions);
	
	public Book selectBookById(Integer id);
	
	public Book selectBookByType(String type);

	public List<Book> selectBookByNameOrBarcode(String bookNameOrBarcode);
	
	public List<Book> selectAllBooks(Integer start);

	public List<Book> selectBooksByOptions(BookOption bookOption);

	public List<Book> selectBooksByOptionsCount(BookOption bookOption);
	
	public List<Book> selectBooksByOptionsManager(BookOption bookOption);
	
	public List<Book> selectBooksByOptionsCountManager(BookOption bookOption);

	public List<Book> selectBooksByLimit(String name, Integer start, Integer limit);

	public BookCount selectBookCount();

	public List<Book> selectLastMonthChartBooks();

	public List<Book> selectBorrowChartBooks();

	public List<Book> selectFavoriteChartBooks();

	public List<Book> selectHotChartBooks();

	public Integer enableBorrowStatus(Integer bookId);

	public Integer selectHotChartBooks(Integer bookId);

	public Integer disableBorrowStatus(Integer bookId);

	public List<Book> selectMyFavorite(Integer userId);

	public List<Book> selectMyPraise(Integer userId);

	public List<Book> selectAllChartBooks(BookChartParam bookChartParam);

	public BookCount selectAllChartBooksCount(BookChartParam bookChartParam);

	public Integer insertBook();

	public Book selectLastBook();

	public BookCount selectAllBookCount();

	public Book selectEditBookById(Integer book_id);


}
