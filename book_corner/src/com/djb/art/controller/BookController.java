package com.djb.art.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.djb.art.model.Book;
import com.djb.art.model.BookBorrow;
import com.djb.art.model.BookBorrowOption;
import com.djb.art.model.BookChartParam;
import com.djb.art.model.BookComment;
import com.djb.art.model.BookCount;
import com.djb.art.model.BookFavorite;
import com.djb.art.model.BookOption;
import com.djb.art.model.BookPraise;
import com.djb.art.model.BookType;
import com.djb.art.model.User;
import com.djb.art.repository.BookRepository;
import com.djb.art.service.BookBorrowService;
import com.djb.art.service.BookCommentService;
import com.djb.art.service.BookFavoriteService;
import com.djb.art.service.BookPraiseService;
import com.djb.art.service.BookService;
import com.djb.art.service.BookTypeService;
import com.djb.art.service.SystemManagerService;
import com.djb.art.service.UserService;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private BookTypeService bookTypeService;

	@Autowired
	private BookCommentService bookCommentService;

	@Autowired
	private BookFavoriteService bookFavoriteService;

	@Autowired
	private BookPraiseService bookPraiseService;

	@Autowired
	private BookBorrowService bookBorrowService;
	
	@Autowired
	private SystemManagerService systemManagerService;

	@Autowired
	private BookRepository bookRepository;

	// 基础分页查询
	@RequestMapping(value = "/homeBookLimit")
	public ModelAndView getBookLimit(@RequestParam("page") Integer page, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 初始化图书类型下拉框
			List<BookType> bookTypes = bookTypeService.getBookTypes();
			ret.addObject("bookTypes", bookTypes);
			// 获取图书列表
			List<Book> books = bookService.getAllBooks(page * 10 - 10);
			// 获取有效图书数目
			BookCount book_count = bookRepository.selectBookCount();
			ret.addObject("page_count", book_count.getCount() / 10 + 1);
			ret.addObject("page", page);
			ret.addObject("book_count", book_count.getCount());
			ret.addObject("books", books);
			ret.setViewName("home_book");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "登录失败");
			ret.setViewName("login");
		}
		return ret;
	}

	// 跳转到图书详情
	@RequestMapping(value = "/bookDetail")
	public ModelAndView getDetail(@RequestParam("book_id") Integer book_id,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取出图书详情
			Book book = bookService.getBookById(book_id);
			ret.addObject("book", book);
			// 取出书评
			List<BookComment> bookComments = bookCommentService.getBookComments(book_id);
			ret.addObject("bookComments", bookComments);
			// 判断当前用户是否收藏了该书
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user != null) {
				BookFavorite bookFavorite = new BookFavorite();
				bookFavorite.setBookId(book_id);
				bookFavorite.setUserId(user.getUserId());
				BookFavorite ifFav = bookFavoriteService.selectFavorite(bookFavorite);
				if (ifFav != null) {
					if (ifFav.getDelFlag().equals("1")) {
						ret.addObject("favorite", true);
					}
				}
			}
			// 判断当前用户是否点赞了该书
			if (user != null) {
				BookPraise bookPraise = new BookPraise();
				bookPraise.setBookId(book_id);
				bookPraise.setUserId(user.getUserId());
				BookPraise ifPraise = bookPraiseService.selectPraise(bookPraise);
				if (ifPraise != null) {
					ret.addObject("praise", true);
				}
			}
			ret.setViewName("book_detail");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("book_detail");
		}
		return ret;
	}
	
	// 跳转到管理员图书详情
	@RequestMapping(value = "/bookDetailManage")
	public ModelAndView bookDetailManage(@RequestParam("book_id") Integer book_id,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取出图书详情
			Book book = bookService.getBookById(book_id);
			ret.addObject("book", book);
			ret.addObject("del_flag", book.getDelFlag());
			// 取出书评
			List<BookComment> bookComments = bookCommentService.getBookComments(book_id);
			ret.addObject("bookComments", bookComments);
			ret.setViewName("book_detail_manage");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("book_detail");
		}
		return ret;
	}

	
	// 跳转到图片详情
		@RequestMapping(value = "/bookPicList")
		public ModelAndView getPics(@RequestParam("book_id") Integer book_id, 
				Model model, HttpServletRequest request, HttpServletResponse response) {
			ModelAndView ret = new ModelAndView();
			try {
				// 取出图书详情
				book_id = Integer.parseInt(request.getParameter("book_id"));
				Book book = bookService.getBookById(book_id);
				ret.addObject("book", book);
				ret.setViewName("book_pic_list");
			} catch (Exception e) {
				System.out.print(e.toString());
				ret.addObject("message", "查询失败");
				List<Book> books = bookService.getAllBooks(0);
				ret.addObject("books", books);
				ret.setViewName("book_detail");
			}
			return ret;
		}
	
	// 主页页面创建借书记录
	@RequestMapping(value = "/homeBorrowBook")
	public ModelAndView homeBorrowBook(@RequestParam("book_id") Integer book_id,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			List<BookType> bookTypes = bookTypeService.getBookTypes();
			ret.addObject("bookTypes", bookTypes);
			BookOption bookOption = new BookOption();
			//将主页关键数据取出来
			HttpSession session = request.getSession();
			//查询到的图书数量
			Integer book_count = (Integer) session.getAttribute("book_count");
			//页码数量
			//Integer page_count = (Integer) session.getAttribute("page_count");
			//当前页码
			Integer page = (Integer) session.getAttribute("page");
			//借阅次数升降序
			Integer borrow_times = (Integer) session.getAttribute("borrow_times");
			//录入时间升降序
			Integer created_time =(Integer) session.getAttribute("created_time");
			//图书类型选择
			Integer book_type = (Integer) session.getAttribute("book_type");
			//模糊查询文本框
			String bookNameOrBarcode = (String) session.getAttribute("bookNameOrBarcode");
			//对取出的主页参数进行初始化
			if (bookNameOrBarcode!= null) {
				bookOption.setBookNameOrBarcode(bookNameOrBarcode.trim());
				request.setAttribute("bookNameOrBarcode", bookNameOrBarcode.trim());
			} else {
				request.setAttribute("bookNameOrBarcode", " ");
			}
			if (borrow_times != null) {
				bookOption.setBorrowTimes(borrow_times.intValue());
				request.setAttribute("borrow_times", borrow_times.intValue());
			} else {
				request.setAttribute("borrow_times", 0);
			}
			if (created_time != null) {
				bookOption.setCreatedTime(created_time.intValue());
				request.setAttribute("created_time", created_time.intValue());
			} else {
				request.setAttribute("created_time", 0);
			}
			if (book_type != null) {
				bookOption.setBookType(book_type.intValue());
				request.setAttribute("book_type", book_type.intValue());
			} else {
				request.setAttribute("book_type", 0);
			}
			if (page != null) {
				//bookOption的page是记录startIndex而不是具体的页码数，需要转换
				bookOption.setPage(page*10-10);
				request.setAttribute("page", page);
			} else {
				request.setAttribute("page", 1);
			}
			// 取出图书详情
			Book book = bookService.getBookById(book_id);
			book.getBorrowFlag();
			User user = (User) session.getAttribute("user");
			if(user==null) {
				ret.addObject("message","请先登录后再操作！");
				ret.setViewName("login");
				return ret;
			}
			//查询当前用户已经借阅的数目
			int staystillCount = userService.getUserStaystillCount(user.getUserId());
			//查询系统参数中的最大借阅本数
			int maxBorrowCount = Integer.parseInt(systemManagerService.getMaxBorrowCount().trim());
			if(staystillCount>=maxBorrowCount) {
				ret.addObject("message", "每人最多借阅"+maxBorrowCount+"本哦！您已经借了"+staystillCount+"本了，请先把手头的看完再来借书吧");
				//执行查询
				int userId = 0;
				if(user!=null) {
					userId = user.getUserId();
				}
				bookOption.setUserId(userId);
				List<Book> books = bookService.getBooksByOptions(bookOption);
				List<Book> books_count = bookService.getBooksByOptionsCount(bookOption);
				book_count = books_count.size();
				ret.addObject("books", books);
				ret.addObject("book_count", book_count);
				ret.addObject("page_count", book_count / 10 + 1);
				
				ret.setViewName("home_book");
				return ret;
			}
			
			//如果用户有效，且图书状态可接，则进行借书操作
			if (user != null && book.getBorrowFlag().equals("1")) {
				BookBorrow bookBorrow = new BookBorrow();
				bookBorrow.setBookId(book_id);
				bookBorrow.setUserId(user.getUserId());
				Integer create_result = bookBorrowService.createBookBorrow(bookBorrow);
				Integer borrow_status = bookService.disableBorrowStatus(book_id);
				if (create_result != null && borrow_status != null) {
					//从数据库中查找新生成的借书记录
					BookBorrowOption bookBorrowOption = new BookBorrowOption();
					bookBorrowOption.setUserId(user.getUserId());
					bookBorrowOption.setBookId(book_id);
					//取出用户的本书还书日期
					String planReturnDate = bookBorrowService.getPlanReturnDate(bookBorrowOption);
					ret.addObject("message", "借书成功!记得在"+planReturnDate+"前归还");
				}
			}
			//执行查询
			int userId = 0;
			if(user!=null) {
				userId = user.getUserId();
			}
			bookOption.setUserId(userId);
			List<Book> books = bookService.getBooksByOptions(bookOption);
			List<Book> books_count = bookService.getBooksByOptionsCount(bookOption);
			book_count = books_count.size();
			ret.addObject("books", books);
			ret.addObject("book_count", book_count);
			ret.addObject("page_count", book_count / 10 + 1);
			
			ret.setViewName("home_book");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "登录失败");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 主页页面还书
	@RequestMapping(value = "/homeReturnBook")
	public ModelAndView homeReturnBook(@RequestParam("book_id") Integer book_id,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			List<BookType> bookTypes = bookTypeService.getBookTypes();
			ret.addObject("bookTypes", bookTypes);
			BookOption bookOption = new BookOption();
			//将主页关键数据取出来
			HttpSession session = request.getSession();
			//查询到的图书数量
			Integer book_count = (Integer) session.getAttribute("book_count");
			//页码数量
			//Integer page_count = (Integer) session.getAttribute("page_count");
			//当前页码
			Integer page = (Integer) session.getAttribute("page");
			//借阅次数升降序
			Integer borrow_times = (Integer) session.getAttribute("borrow_times");
			//录入时间升降序
			Integer created_time =(Integer) session.getAttribute("created_time");
			//图书类型选择
			Integer book_type = (Integer) session.getAttribute("book_type");
			//模糊查询文本框
			String bookNameOrBarcode = (String) session.getAttribute("bookNameOrBarcode");
			//对取出的主页参数进行初始化
			if (bookNameOrBarcode!= null) {
				bookOption.setBookNameOrBarcode(bookNameOrBarcode.trim());
				request.setAttribute("bookNameOrBarcode", bookNameOrBarcode.trim());
			} else {
				request.setAttribute("bookNameOrBarcode", " ");
			}
			if (borrow_times != null) {
				bookOption.setBorrowTimes(borrow_times.intValue());
				request.setAttribute("borrow_times", borrow_times.intValue());
			} else {
				request.setAttribute("borrow_times", 0);
			}
			if (created_time != null) {
				bookOption.setCreatedTime(created_time.intValue());
				request.setAttribute("created_time", created_time.intValue());
			} else {
				request.setAttribute("created_time", 0);
			}
			if (book_type != null) {
				bookOption.setBookType(book_type.intValue());
				request.setAttribute("book_type", book_type.intValue());
			} else {
				request.setAttribute("book_type", 0);
			}
			if (page != null) {
				//bookOption的page是记录startIndex而不是具体的页码数，需要转换
				bookOption.setPage(page*10-10);
				request.setAttribute("page", page);
			} else {
				request.setAttribute("page", 1);
			}
			// 取出图书详情
			Book book = bookService.getBookById(book_id);
			book.getBorrowFlag();
			User user = (User) session.getAttribute("user");
			//如果用户有效，且图书状态待还，则进行还书操作
			if (user != null && book.getBorrowFlag().equals("0")) {
				Integer user_id = user.getUserId();
				BookBorrow bookBorrow = new BookBorrow();
				bookBorrow.setBookId(book_id);
				bookBorrow.setUserId(user_id);
				// 更新借书记录
				Integer returnResult = bookBorrowService.returnBook(bookBorrow);
				// 更新图书状态
				Integer changeStatusResult = bookService.enableBorrowStatus(book_id);
				if (returnResult != null && changeStatusResult != null) {
					ret.addObject("message", "还书成功！");
				}else{
					ret.addObject("message", "还书失败！");
				}
				bookOption.setUserId(user_id);;
			}
			// 执行查询
			List<Book> books = bookService.getBooksByOptions(bookOption);
			List<Book> books_count = bookService.getBooksByOptionsCount(bookOption);
			book_count = books_count.size();
			ret.addObject("books", books);
			ret.addObject("book_count", book_count);
			ret.addObject("page_count", book_count / 10 + 1);
			
			ret.setViewName("home_book");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "登录失败");
			ret.setViewName("login");
		}
		return ret;
	}

	// 图书详情页面创建借书记录
	@RequestMapping(value = "/borrowBook")
	public ModelAndView borrowbook(@RequestParam("book_id") Integer book_id,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取出图书详情
			Book book = bookService.getBookById(book_id);
			ret.addObject("book", book);
			book.getBorrowFlag();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			//查询当前用户已经借阅的数目
			int staystillCount = userService.getUserStaystillCount(user.getUserId());
			//查询系统参数中的最大借阅本数
			int maxBorrowCount = Integer.parseInt(systemManagerService.getMaxBorrowCount().trim());
			if(staystillCount>=maxBorrowCount) {
				ret.addObject("message", "每人最多借阅"+maxBorrowCount+"本哦！您已经借了"+staystillCount+"本了，请先把手头的看完再来借书吧");
				// 取出书评
				List<BookComment> bookComments = bookCommentService.getBookComments(book_id);
				ret.addObject("bookComments", bookComments);
				// 判断当前用户是否收藏了该书
				if (user != null) {
					BookFavorite bookFavorite = new BookFavorite();
					bookFavorite.setBookId(book_id);
					bookFavorite.setUserId(user.getUserId());
					BookFavorite ifFav = bookFavoriteService.selectFavorite(bookFavorite);
					if (ifFav != null) {
						if (ifFav.getDelFlag().equals("1")) {
							ret.addObject("favorite", true);
						}
					}
				}
				// 判断当前用户是否点赞了该书
				if (user != null) {
					BookPraise bookPraise = new BookPraise();
					bookPraise.setBookId(book_id);
					bookPraise.setUserId(user.getUserId());
					BookPraise ifPraise = bookPraiseService.selectPraise(bookPraise);
					if (ifPraise != null) {
						ret.addObject("praise", true);
					}
				}
				ret.setViewName("book_detail");
				return ret;
			}
			if (user != null && book.getBorrowFlag().equals("1")) {
				BookBorrow bookBorrow = new BookBorrow();
				bookBorrow.setBookId(book_id);
				bookBorrow.setUserId(user.getUserId());
				Integer create_result = bookBorrowService.createBookBorrow(bookBorrow);
				Integer borrow_status = bookService.disableBorrowStatus(book_id);
				if (create_result != null && borrow_status != null) {
					//从数据库中查找新生成的借书记录
					BookBorrowOption bookBorrowOption = new BookBorrowOption();
					bookBorrowOption.setUserId(user.getUserId());
					bookBorrowOption.setBookId(book_id);
					//取出用户的本书还书日期
					String planReturnDate = bookBorrowService.getPlanReturnDate(bookBorrowOption);
					ret.addObject("message", "借书成功!记得在"+planReturnDate+"前归还");
					// 取出图书详情
					book = bookService.getBookById(book_id);
					ret.addObject("book", book);
					//取出图书评论
					List<BookComment> bookComments = bookCommentService.getBookComments(book_id);
					ret.addObject("bookComments", bookComments);
					// 判断当前用户是否收藏了该书
					if (user != null) {
						BookFavorite bookFavorite = new BookFavorite();
						bookFavorite.setBookId(book_id);
						bookFavorite.setUserId(user.getUserId());
						BookFavorite ifFav = bookFavoriteService.selectFavorite(bookFavorite);
						if (ifFav != null) {
							if (ifFav.getDelFlag().equals("1")) {
								ret.addObject("favorite", true);
							}
						}
					}
					// 判断当前用户是否点赞了该书
					if (user != null) {
						BookPraise bookPraise = new BookPraise();
						bookPraise.setBookId(book_id);
						bookPraise.setUserId(user.getUserId());
						BookPraise ifPraise = bookPraiseService.selectPraise(bookPraise);
						if (ifPraise != null) {
							ret.addObject("praise", true);
						}
					}
					ret.setViewName("book_detail");
					return ret;
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "登录失败");
			ret.setViewName("login");
		}
		// return ret;
		ret.addObject("message", "请先登录！");
		ret.setViewName("start");
		//return new ModelAndView("forward:bookDetail?book_id=" + book_id, null);
		return ret;
	}

	// 图书详情页面的收藏操作和更新收藏操作
	@RequestMapping(value = "/bookFavorite")
	public ModelAndView doFavorite(@RequestParam("book_id") Integer book_id,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			// 判断是否有过收藏记录
			BookFavorite bookFavorite = new BookFavorite();
			bookFavorite.setBookId(book_id);
			bookFavorite.setUserId(user.getUserId());
			BookFavorite ifFav = bookFavoriteService.selectFavorite(bookFavorite);
			// 对收藏记录进行更新（加入收藏）
			if (ifFav != null) {
				// 进行更新收藏操作
				bookFavorite = new BookFavorite();
				bookFavorite.setBookId(book_id);
				bookFavorite.setUserId(user.getUserId());
				bookFavorite.setDelFlag("1");
				Integer result = bookFavoriteService.updateFavorite(bookFavorite);
				if (result.intValue() == 1) {
					ret.addObject("message", "更新收藏成功");
					ret.addObject("favorite", true);
				} else {
					ret.addObject("message", "更新收藏失败");
				}
			} else {
				// 进行创建收藏操作
				bookFavorite = new BookFavorite();
				bookFavorite.setBookId(book_id);
				bookFavorite.setUserId(user.getUserId());
				bookFavorite.setDelFlag("1");
				Integer result = bookFavoriteService.createFavorite(bookFavorite);
				if (result != null) {
					ret.addObject("message", "创建收藏成功");
					ret.addObject("favorite", true);
				} else {
					ret.addObject("message", "创建收藏失败");
				}
			}
			// 取出图书详情
			Book book = bookService.getBookById(book_id);
			ret.addObject("book", book);
			// 取出书评
			List<BookComment> bookComments = bookCommentService.getBookComments(book_id);
			ret.addObject("bookComments", bookComments);
			// 判断当前用户是否点赞了该书
			if (user != null) {
				BookPraise bookPraise = new BookPraise();
				bookPraise.setBookId(book_id);
				bookPraise.setUserId(user.getUserId());
				BookPraise ifPraise = bookPraiseService.selectPraise(bookPraise);
				if (ifPraise != null) {
					ret.addObject("praise", true);
				}
			}
			ret.setViewName("book_detail");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "收藏失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.addObject("favorite", true);
			ret.setViewName("book_detail");
		}
		return ret;
	}

	// 图书详情页面的取消收藏操作
	@RequestMapping(value = "/bookCancelFavorite")
	public ModelAndView cancelFavorite(@RequestParam("book_id") Integer book_id, 
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			// 判断是否有过收藏记录
			BookFavorite bookFavorite = new BookFavorite();
			bookFavorite.setBookId(book_id);
			bookFavorite.setUserId(user.getUserId());
			BookFavorite ifFav = bookFavoriteService.selectFavorite(bookFavorite);
			// 对收藏记录进行更新（取消收藏）
			if (ifFav != null) {
				bookFavorite = new BookFavorite();
				bookFavorite.setBookId(book_id);
				bookFavorite.setUserId(user.getUserId());
				bookFavorite.setDelFlag("0");
				Integer result = bookFavoriteService.cancelFavorite(bookFavorite);
				if (result != null) {
					ret.addObject("message", "取消收藏成功");
					ret.addObject("favorite", false);
				} else {
					ret.addObject("message", "取消收藏失败");
				}
			} else {
				// 该收藏记录不存在
				ret.addObject("message", "收藏不存在！");
			}
			// 取出图书详情
			Book book = bookService.getBookById(book_id);
			ret.addObject("book", book);
			// 取出书评
			List<BookComment> bookComments = bookCommentService.getBookComments(book_id);
			ret.addObject("bookComments", bookComments);
			// 判断当前用户是否点赞了该书
			if (user != null) {
				BookPraise bookPraise = new BookPraise();
				bookPraise.setBookId(book_id);
				bookPraise.setUserId(user.getUserId());
				BookPraise ifPraise = bookPraiseService.selectPraise(bookPraise);
				if (ifPraise != null) {
					ret.addObject("praise", true);
				}
			}
			ret.setViewName("book_detail");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "收藏失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("book_detail");
		}
		return ret;
	}

	// 图书详情页面的点赞操作
	@RequestMapping(value = "/bookPraise")
	public ModelAndView doPraise(@RequestParam("book_id") Integer book_id, 
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			BookPraise bookPraise = new BookPraise();
			// 进行插入点赞操作
			bookPraise = new BookPraise();
			bookPraise.setBookId(book_id);
			bookPraise.setUserId(user.getUserId());
			Integer result = bookPraiseService.createPraise(bookPraise);
			if (result != null) {
				ret.addObject("message", "创建点赞成功");
			} else {
				ret.addObject("message", "创建点赞失败");
			}
			// 取出图书详情
			Book book = bookService.getBookById(book_id);
			ret.addObject("book", book);
			// 取出书评
			List<BookComment> bookComments = bookCommentService.getBookComments(book_id);
			ret.addObject("bookComments", bookComments);
			ret.addObject("praise", true);
			// 判断当前用户是否收藏了该书
			if (user != null) {
				BookFavorite bookFavorite = new BookFavorite();
				bookFavorite.setBookId(book_id);
				bookFavorite.setUserId(user.getUserId());
				BookFavorite ifFav = bookFavoriteService.selectFavorite(bookFavorite);
				if (ifFav!=null) {
					if (ifFav.getDelFlag().equals("1")) {
						ret.addObject("favorite", true);
					} 
				}
			}
			ret.setViewName("book_detail");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "点赞失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.addObject("praise", true);
			ret.setViewName("book_detail");
		}
		return ret;
	}

	// 取消图书详情页面的点赞操作
	@RequestMapping(value = "/bookCancelPraise")
	public ModelAndView cancelPraise(@RequestParam("book_id") Integer book_id, 
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			BookPraise bookPraise = new BookPraise();
			// 进行取消点赞操作
			bookPraise = new BookPraise();
			bookPraise.setBookId(book_id);
			bookPraise.setUserId(user.getUserId());
			Integer result = bookPraiseService.cancelPraise(bookPraise);
			if (result != null) {
				ret.addObject("message", "取消点赞成功");
			} else {
				ret.addObject("message", "取消点赞失败");
			}
			// 取出图书详情
			Book book = bookService.getBookById(book_id);
			ret.addObject("book", book);
			// 取出书评
			List<BookComment> bookComments = bookCommentService.getBookComments(book_id);
			ret.addObject("bookComments", bookComments);
			ret.addObject("praise", false);
			// 判断当前用户是否收藏了该书
			if (user != null) {
				BookFavorite bookFavorite = new BookFavorite();
				bookFavorite.setBookId(book_id);
				bookFavorite.setUserId(user.getUserId());
				BookFavorite ifFav = bookFavoriteService.selectFavorite(bookFavorite);
				if (ifFav!=null) {
					if (ifFav.getDelFlag().equals("1")) {
						ret.addObject("favorite", true);
					} 
				}
			}
			ret.setViewName("book_detail");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "取消点赞失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.addObject("Praise", false);
			ret.setViewName("book_detail");
		}
		return ret;
	}

	// 跳转到发表图书评论页面
	@RequestMapping(value = "/bookComment")
	public ModelAndView toBookComment(@RequestParam("book_id") String book_id, Model model,
			@RequestParam("page") String page, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			ret.addObject("book_id", book_id);
			ret.addObject("page", page);
			ret.setViewName("book_comment");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("home_book");
		}
		return ret;
	}

	// 模糊查询
	@RequestMapping(value = "/selectBookByNameOrBarcode") // , method=RequestMethod.POST
	public ModelAndView getBooks(@RequestParam("bookNameOrBarcode") String bookNameOrBarcode, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 初始化图书类型下拉框
			List<BookType> bookTypes = bookTypeService.getBookTypes();
			ret.addObject("bookTypes", bookTypes);
			List<Book> books = bookService.getBookByNameOrBarcode(bookNameOrBarcode);
			ret.addObject("books", books);
			ret.setViewName("home_book");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("home_book");
		}
		return ret;
	}

	// 跳转到排行榜
	@RequestMapping(value = "/bookChart")
	public ModelAndView bookChart(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			List<Book> books = bookService.getLastMonthChartBooks();
			ret.addObject("books", books);
			ret.setViewName("book_chart");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("book_chart");
		}
		return ret;
	}

	// 上月排行榜
	@RequestMapping(value = "/getLastMonthChart")
	public ModelAndView getLastMonthChart(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			List<Book> books = bookService.getLastMonthChartBooks();
			ret.addObject("books", books);
			ret.setViewName("book_chart");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("book_chart");
		}
		return ret;
	}

	// 借阅排行榜
	@RequestMapping(value = "/getBorrowChart")
	public ModelAndView getBorrowChart(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			List<Book> books = bookService.getBorrowChartBooks();
			ret.addObject("books", books);
			ret.setViewName("book_chart");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("book_chart");
		}
		return ret;
	}

	// 收藏排行榜
	@RequestMapping(value = "/getFavoriteChart")
	public ModelAndView getFavoriteChart(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			List<Book> books = bookService.getFavoriteChartBooks();
			ret.addObject("books", books);
			ret.setViewName("book_chart");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("book_chart");
		}
		return ret;
	}

	// 人气排行榜
	@RequestMapping(value = "/getPraiseChart")
	public ModelAndView getPraiseChart(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			List<Book> books = bookService.getHotChartBooks();
			ret.addObject("books", books);
			ret.setViewName("book_chart");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("book_chart");
		}
		return ret;
	}

	// 条件查询带分页
	@RequestMapping(value = "/homeBookQuery")
	public ModelAndView getBooksByOptions(@RequestParam("borrow_times") Integer borrow_times,
			@RequestParam("created_time") Integer created_time, @RequestParam("book_type") Integer book_type,
			@RequestParam("page") Integer page, @RequestParam("bookNameOrBarcode") String bookNameOrBarcode,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//创建session对象
			HttpSession session = request.getSession();
			//取出当前用户对象
			User user = (User) session.getAttribute("user");
			int userId = 0;
			if (user!=null) {
				userId = user.getUserId();
			}
			List<BookType> bookTypes = bookTypeService.getBookTypes();
			ret.addObject("bookTypes", bookTypes);
			BookOption bookOption = new BookOption();
			//对取出的主页参数进行初始化
			bookOption.setUserId(userId);
			if (bookNameOrBarcode.trim() != null) {
				bookOption.setBookNameOrBarcode(bookNameOrBarcode.trim());
				request.setAttribute("bookNameOrBarcode", bookNameOrBarcode.trim());
			} else {
				request.setAttribute("bookNameOrBarcode", " ");
			}
			if (borrow_times != null) {
				bookOption.setBorrowTimes(borrow_times);
				request.setAttribute("borrow_times", borrow_times);
			} else {
				request.setAttribute("borrow_times", 0);
			}
			if (created_time != null) {
				bookOption.setCreatedTime(created_time);
				request.setAttribute("created_time", created_time);
			} else {
				request.setAttribute("created_time", 0);
			}
			if (book_type != null) {
				bookOption.setBookType(book_type);
				request.setAttribute("book_type", book_type);
			} else {
				request.setAttribute("book_type", 0);
			}
			if (page != null) {
				bookOption.setPage(page*10-10);
				request.setAttribute("page", page);
			} else {
				request.setAttribute("page", 1);
			}
			List<Book> books = bookService.getBooksByOptions(bookOption);
			List<Book> books_count = bookService.getBooksByOptionsCount(bookOption);
			int book_count = books_count.size();
			ret.addObject("books", books);
			ret.addObject("book_count", book_count);
			ret.addObject("page_count",(book_count%10==0?book_count/10:book_count / 10 + 1));			
			//将主页关键数据存入到session中
			//查询到的图书数量
			session.setAttribute("book_count", book_count);
			//页码数量
			session.setAttribute("page_count",  book_count/10+1);
			//当前页码
			session.setAttribute("page", page);
			//借阅次数升降序
			session.setAttribute("borrow_times", borrow_times);
			//录入时间升降序
			session.setAttribute("created_time", created_time);
			//图书类型选择
			session.setAttribute("book_type", book_type);
			//模糊查询文本框
			session.setAttribute("bookNameOrBarcode", bookNameOrBarcode);
			ret.setViewName("home_book");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("home_book");
		}
		return ret;
	}

	// 排行榜查询带分页
	@RequestMapping(value = "/bookChartQuery")
	public ModelAndView bookChartQuery(
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//取出排行榜基本参数并且初始化
			int chart_type = Integer.parseInt(""+(request.getParameter("chart_type")==null?1:request.getParameter("chart_type")));
			int chart_page = Integer.parseInt(""+(request.getParameter("chart_page")==null?1:request.getParameter("chart_page")));
			//查询出图书列表
			BookChartParam bookChartParam = new BookChartParam();
			bookChartParam.setStart(chart_page*10-10);
			bookChartParam.setType(chart_type);
			List<Book> books = bookService.getAllChartBooks(bookChartParam);
			ret.addObject("books", books);
			//取出排行榜图书总数
			int chart_book_count = bookService.getAllChartBooksCount(bookChartParam).getCount();
			//算出排行榜页数
			int chart_page_count = chart_book_count%10==0?chart_book_count/10:chart_book_count/10+1;
			//页码相关参数传到前台
			ret.addObject("chart_type",chart_type);			
			ret.addObject("chart_page",chart_page);			
			ret.addObject("chart_page_count",chart_page_count);			
			ret.addObject("chart_book_count",chart_book_count);			
			//将主页关键数据存入到session中
			HttpSession session = request.getSession();
			//查询到的排行榜图书数量
			session.setAttribute("chart_book_count", chart_book_count);
			//排行榜页码数量
			session.setAttribute("chart_page_count",  chart_page_count/10+1);
			//当前页码
			session.setAttribute("chart_page", chart_page);
			//排行类型
			session.setAttribute("chart_type", chart_type);
			ret.setViewName("book_chart2");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("home_book");
		}
		return ret;
	}
	
		// 从其他页面跳回到主页时，保持原样
		@RequestMapping(value = "/backHomeBookQuery")
		public ModelAndView backHomeBookQuery(Model model, HttpServletRequest request, HttpServletResponse response) {
			ModelAndView ret = new ModelAndView();
			try {
				List<BookType> bookTypes = bookTypeService.getBookTypes();
				ret.addObject("bookTypes", bookTypes);
				BookOption bookOption = new BookOption();
				//将主页关键数据取出来
				HttpSession session = request.getSession();
				//查询到的图书数量
				Integer book_count = (Integer) session.getAttribute("book_count");
				//页码数量
				//Integer page_count = (Integer) session.getAttribute("page_count");
				//当前页码
				Integer page = (Integer) session.getAttribute("page");
				//借阅次数升降序
				Integer borrow_times = (Integer) session.getAttribute("borrow_times");
				//录入时间升降序
				Integer created_time =(Integer) session.getAttribute("created_time");
				//图书类型选择
				Integer book_type = (Integer) session.getAttribute("book_type");
				//模糊查询文本框
				String bookNameOrBarcode = (String) session.getAttribute("bookNameOrBarcode");
				//对取出的主页参数进行初始化
				if (bookNameOrBarcode != null) {
					bookOption.setBookNameOrBarcode(bookNameOrBarcode.trim());
					request.setAttribute("bookNameOrBarcode", bookNameOrBarcode.trim());
				} else {
					request.setAttribute("bookNameOrBarcode", " ");
				}
				if (borrow_times != null) {
					bookOption.setBorrowTimes(borrow_times.intValue());
					request.setAttribute("borrow_times", borrow_times.intValue());
				} else {
					request.setAttribute("borrow_times", 0);
				}
				if (created_time != null) {
					bookOption.setCreatedTime(created_time.intValue());
					request.setAttribute("created_time", created_time.intValue());
				} else {
					request.setAttribute("created_time", 0);
				}
				if (book_type != null) {
					bookOption.setBookType(book_type.intValue());
					request.setAttribute("book_type", book_type.intValue());
				} else {
					request.setAttribute("book_type", 0);
				}
				if (page != null) {
					//bookOption的page是记录startIndex而不是具体的页码数，需要转换
					bookOption.setPage(page*10-10);
					request.setAttribute("page", page);
				} else {
					request.setAttribute("page", 1);
				}
				//判断当前用户是否登录，具备还书资格
				Integer userId = 0;
				User user = (User) session.getAttribute("user");
				if (user!=null) {
					userId = user.getUserId();
					bookOption.setUserId(userId);
				}
				//执行查询
				List<Book> books = bookService.getBooksByOptions(bookOption);
//				List<Book> books_count = bookService.getBooksByOptionsCount(bookOption);
//				int book_count = books_count.size();
				ret.addObject("books", books);
				
				if (book_count!=null) {
					ret.addObject("book_count", book_count);
					ret.addObject("page_count", (book_count % 10 == 0 ? book_count / 10 : book_count / 10 + 1));
				}else if(book_count==null){
					//取得所有上架图书总数
					book_count = bookService.getBookCount().getCount();
					ret.addObject("book_count", book_count);
					ret.addObject("page_count", (book_count % 10 == 0 ? book_count / 10 : book_count / 10 + 1));
				}
				ret.setViewName("home_book");
			} catch (Exception e) {
				System.out.print(e.toString());
				ret.addObject("message", "查询失败");
				List<Book> books = bookService.getAllBooks(0);
				ret.addObject("books", books);
				ret.setViewName("home_book");
			}
			return ret;
		}
	
	// 插入评论
	@RequestMapping(value = "/attachComment")
	public ModelAndView attachComment(@RequestParam("book_id") Integer book_id,
			@RequestParam("book_comment") String book_comment, @RequestParam("comment_grade") String comment_grade,
			@RequestParam("page") Integer page, Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			String[] result = request.getParameterValues("anonymou_flag");
			String anonymou_flag = null;
			if (result == null) {
				anonymou_flag = "0";
			} else {
				anonymou_flag = result[0];
			}
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			ret.addObject("favorite", false);
			// 判断用户是否为空，若已登录，进行插入评论操作
			if (user != null) {
				BookComment bookComment = new BookComment();
				bookComment.setBookId(book_id);
				bookComment.setUserId(user.getUserId());
				bookComment.setComment(book_comment);
				bookComment.setAnonymouFlag(anonymou_flag);
				bookComment.setCommentGrade(comment_grade);
				Integer ifDoComment = bookCommentService.createBookComment(bookComment);
				if (ifDoComment != null) {
					ret.addObject("comment_flag", true);
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "登录失败");
			ret.setViewName("login");
		}
		return new ModelAndView("forward:bookDetail?page=" + page + "&book_id=" + book_id, null);
	}

}
