package com.djb.art.service;

import java.util.List;

import com.djb.art.model.Book;
import com.djb.art.model.BookChartParam;
import com.djb.art.model.BookCount;
import com.djb.art.model.BookOption;

public interface BookService {

	public Integer createBook(Book user);

	public Integer updateBook(Book user);

	public Integer deleteBook(Integer id);
	
	//取得上架图书数目
	public BookCount getBookCount();

	//取得所有图书数目（包括上架和下架）
	public BookCount getAllBookCount();

	public Book getBookById(Integer id);

	//图书信息编辑页面的信息查询
	public Book getEditBookById(Integer id);
	
	public Book getBookByType(String type);

	public List<Book> getBookByNameOrBarcode(String bookNameOrBarcode);

	//分页查询得到图书列表
	public List<Book> getBooks(String name, Integer start, Integer limit);
	
	//取得所有图书列表
	public List<Book> getAllBooks(Integer start);

	//取得上月排行榜
	public List<Book> getLastMonthChartBooks();
	
	//取得借阅排行榜
	public List<Book> getBorrowChartBooks();
	
	//取得收藏排行榜
	public List<Book> getFavoriteChartBooks();
	
	//取得人气排行榜
	public List<Book> getHotChartBooks();

	//取得所有的综合排行榜
	public List<Book> getAllChartBooks(BookChartParam bookChartParam);

	//取得所有的综合排行榜总数
	public BookCount getAllChartBooksCount(BookChartParam bookChartParam);
	
	//取得条件查询图书列表
	public List<Book> getBooksByOptions(BookOption bookOption);

	//取得条件查询图书列表
	public List<Book> getBooksByOptionsCount(BookOption bookOption);
	
	//取得条件查询图书列表
	public List<Book> getBooksByOptionsManager(BookOption bookOption);
	
	//取得条件查询图书列表
	public List<Book> getBooksByOptionsCountManager(BookOption bookOption);

	//图书状态变为可借
	public Integer enableBorrowStatus(Integer book_id);
	
	//图书状态变为待还
	public Integer disableBorrowStatus(Integer book_id);

	//取得用户的收藏
	public List<Book> getMyFavorite(Integer user_id);

	//取得用户的点赞
	public List<Book> getMyPraise(Integer user_id);
	
	//取得最新的一本书
	public Book getLastBook();
}
