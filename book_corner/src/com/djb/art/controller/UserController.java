package com.djb.art.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
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
import com.djb.art.model.BookComment;
import com.djb.art.model.BookCount;
import com.djb.art.model.BookOption;
import com.djb.art.model.BookType;
import com.djb.art.model.SystemManager;
import com.djb.art.model.User;
import com.djb.art.model.UserReview;
import com.djb.art.repository.BookRepository;
import com.djb.art.service.BookBorrowService;
import com.djb.art.service.BookCommentService;
import com.djb.art.service.BookService;
import com.djb.art.service.BookTypeService;
import com.djb.art.service.SystemManagerService;
import com.djb.art.service.UserReviewService;
import com.djb.art.service.UserService;
 
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserReviewService userReviewService;

	@Autowired
	private BookService bookService;

	@Autowired
	private BookBorrowService bookBorrowService;
	
	@Autowired
	private BookCommentService bookCommentService;

	@Autowired
	private SystemManagerService systemManagerService;

	@Autowired
	private BookTypeService bookTypeService;

	@Autowired
	private BookRepository bookRepository;

	@RequestMapping(value = "users/info") // , method=RequestMethod.POST
	public String getUserString() {
		return "fc test ok!";
	}

	// 用户登录验证
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login")
	public ModelAndView getUser(@RequestParam("userCode") String code, @RequestParam("userFullname") String fullname,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//取出服务器全局对象
			ServletContext application = request.getServletContext();
			String userCode = "DJB"+String.format("%03d", Integer.parseInt(code.toUpperCase().trim().replace("DJB", "")));
			ConcurrentHashMap<String, String> onlineUserMap = (ConcurrentHashMap<String, String>) application.getAttribute("onlineUserMap");
			if(onlineUserMap==null) {
				onlineUserMap = new ConcurrentHashMap<String, String>();
			}
			String onlineUserInfo = onlineUserMap.get(userCode);
			if(onlineUserInfo!=null) {
				String currentIp = request.getRemoteAddr();
				String[] infoStr = onlineUserInfo.split("_");
				String logInIp = infoStr[1];
				if(!currentIp.equals(logInIp)) {
					ret.addObject("message", "该员工已登录！无法同时在线！");
					ret.addObject("userName", fullname);
					ret.addObject("userCode", userCode.replace("DJB", ""));
					ret.setViewName("login");
					return ret;
				}
			}
			User user = userService.getUserByCode(userCode);
			if (user == null) {
				UserReview userReview = userReviewService.getUserByCode(userCode);
				if(userReview==null) {
					ret.addObject("message", "员工号不存在");
					ret.addObject("userCode", userCode.replace("DJB", ""));
					ret.addObject("userName",fullname );
					ret.setViewName("login");
				}else if(userReview!=null){
					if(userReview.getReviewFlag().equals("0")) {
						//跳转到等待审核页面
						ret.addObject("userCode", userReview.getUserCode());
						ret.addObject("userName", userReview.getUserName());
//						String yyyy = userReview.getApplyAt().substring(0, 4);
//						String MM = userReview.getApplyAt().substring(4, 6);
//						String dd = userReview.getApplyAt().substring(6, 8);
						String apply_at = userReview.getApplyAt();
						ret.addObject("applyAt", apply_at);
						ret.addObject("message", "您的申请正在等待审核");
						ret.setViewName("reviewing");
					}else if (userReview.getReviewFlag().equals("1")) {
						// 审核通过，但是用户表中不存在该用户，跳转到登录
						ret.addObject("userName", fullname);
						ret.addObject("userCode", userCode.replace("DJB", ""));
						ret.addObject("message", "出错！可能原因：1，DB审核表状态不正确；2，您的账户已被删除！");
						ret.setViewName("login");
					}else if (userReview.getReviewFlag().equals("2")) {
						// 审核被拒绝，跳转到被拒绝页面
//						ret.addObject("openId", userReview.getOpenId());
//						ret.addObject("userCode", userReview.getUserCode());
//						ret.addObject("userName", userReview.getUserName());
						ret.addObject("message", "您的申请已被拒绝");
						ret.setViewName("review_confuse");
					}
				}
			} else if (!fullname.trim().equals(user.getUserFullname())) {
				ret.addObject("message", "用户名错误");
				ret.addObject("userCode", userCode);
				ret.addObject("userName",fullname );
				ret.setViewName("login");
			} else {
				HttpSession session = request.getSession();
				//作为用户登录
				ret.addObject("message", "用户"+user.getUserFullname()+"，欢迎登陆！");
				if (user.getUserAuthFlag().equals("0")) {
					// 获取有效图书数目
					BookCount book_count_all = bookRepository.selectBookCount();
					// 存入用户信息
					session.setAttribute("user", user);
					//存入一个全局登录信息
					onlineUserMap.put(user.getUserCode(),user.getUserFullname()+"_"+request.getRemoteAddr()+"_"+user.getUserCode());
					application.setAttribute("onlineUserMap", onlineUserMap);
					// 将主页关键数据存入到session中
					// 查询到的图书数量
					session.setAttribute("book_count", book_count_all.getCount());
					// 页码数量
					session.setAttribute("page_count", book_count_all.getCount() / 10 + 1);
					// 当前页码
					session.setAttribute("page", 1);
					// 借阅次数升降序
					session.setAttribute("borrow_times", 0);
					// 录入时间升降序
					session.setAttribute("created_time", 0);
					// 图书类型选择
					session.setAttribute("book_type", 0);
					// 模糊查询文本框
					session.setAttribute("bookNameOrBarcode", "");
					// 初始化图书类型下拉框
					List<BookType> bookTypes = bookTypeService.getBookTypes();
					ret.addObject("bookTypes", bookTypes);
					BookOption bookOption = new BookOption();
					//将主页关键数据取出来
					//查询到的图书数量
					Integer book_count = (Integer) session.getAttribute("book_count");
					//页码数量
					//Integer page_count = (Integer) session.getAttribute("page_count");
					//当前页码
					Integer page = (Integer) session.getAttribute("page");
					//借阅次数升降序
					Integer borrow_times = (Integer) session.getAttribute("borrow_times");
					//录入时间升降序
					Integer created_time = (Integer) session.getAttribute("created_time");
					//图书类型选择
					Integer book_type = (Integer) session.getAttribute("book_type");
					//模糊查询文本框
					String bookNameOrBarcode = (String) session.getAttribute("bookNameOrBarcode");
					//对取出的主页参数进行初始化
					if (bookNameOrBarcode.trim() != null) {
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
						bookOption.setPage(page * 10 - 10);
						request.setAttribute("page", page);
					} else {
						request.setAttribute("page", 1);
					}
					//判断当前用户是否登录，具备还书资格
					Integer userId = 0;
					if (user != null) {
						userId = user.getUserId();
						bookOption.setUserId(userId);
					}
					//执行查询
					List<Book> books = bookService.getBooksByOptions(bookOption);
					//				List<Book> books_count = bookService.getBooksByOptionsCount(bookOption);
					//				int book_count = books_count.size();
					ret.addObject("books", books);
					ret.addObject("book_count", book_count);
					ret.addObject("page_count", (book_count % 10 == 0 ? book_count / 10 : book_count / 10 + 1));
					ret.setViewName("home_book");
				}else if(user.getUserAuthFlag().equals("1")) {
					//作为管理员登录
					ret.addObject("message", "管理员"+user.getUserFullname()+"，欢迎登陆！");
					//存入一个全局登录信息
					onlineUserMap.put(user.getUserCode(),user.getUserFullname()+"_"+request.getRemoteAddr()+"_"+user.getUserCode());
					application.setAttribute("onlineUserMap", onlineUserMap);
					//application.setAttribute(user.getUserCode(), user.getUserCode()+"="+request.getRemoteAddr());
					//将用户为图书管理员的信息添加session中
					session.setAttribute("system_manager", user);
					session.setAttribute("user", user);
					// 获取上架图书数目
					BookCount book_count = bookRepository.selectBookCount();
					if (book_count!=null) {
						ret.addObject("bookCount", book_count.getCount());
						session.setAttribute("validBookCount", book_count.getCount());
					}
					//获取待审核人员数目
					int notReviewUserCount = systemManagerService.getNotReviewUserCount();
					ret.addObject("notReviewUserCount",notReviewUserCount);
					session.setAttribute("notReviewUserCount", notReviewUserCount);
					//获取有效人员数目
					int validUserCount = systemManagerService.getValidUserCount();
					ret.addObject("validUserCount",validUserCount);
					session.setAttribute("validUserCount", validUserCount);
					//取得当前在线用户列表
					ret.addObject("onlineUsersMap",onlineUserMap);
					ret.setViewName("home_manager");
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "登录失败");
			ret.addObject("userCode", code);
			ret.addObject("userName",fullname );
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 用户退出登录
	@RequestMapping(value = "/userLogOut")
	public ModelAndView userLogOut(
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			//销毁session对象
			session.invalidate();
			ret.addObject("message", "用户已退出登录");
			ret.setViewName("login");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "登录失败");
			ret.setViewName("login");
		}
		return ret;
	}

	// 系统管理员登录验证
	@RequestMapping(value = "/right_manage")
	public ModelAndView get_right_manage(@RequestParam("password") String password,
			@RequestParam("manager_name") String manager_name, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//取出服务器全局对象
			ServletContext application = request.getServletContext();
			//根据用户名取出管理员对象			
			SystemManager ManagerByName = systemManagerService.selectManagerIdByName(manager_name);
			if (ManagerByName == null) {
				ret.addObject("message", "用户名不存在");
				ret.setViewName("login");
			} else {
				String loginManagerId = (String) application.getAttribute("systemManager"+ManagerByName.getManagerId());
				if(loginManagerId!=null) {
					String currentIp = request.getRemoteAddr();
					String[] infoStr = loginManagerId.split("=");
					String logInIp = infoStr[1];
					if(!currentIp.equals(logInIp)) {
						ret.addObject("message", "该管理员已登录！无法同时在线！");
						ret.setViewName("login");
						return ret;
					}
				}
				if (password.equals(ManagerByName.getManagerPassword())) {
					//ret.addObject("message", "登陆成功！");
					//存入一个全局登录信息
					application.setAttribute(""+ManagerByName.getManagerId(), ManagerByName.getManagerId()+"="+request.getRemoteAddr());
					//将管理员对象添加到SESSION中
					HttpSession session = request.getSession();
					session.setAttribute("system_manager", ManagerByName);
					application.setAttribute("systemManager"+ManagerByName.getManagerId(), ManagerByName.getManagerName()+"="+request.getRemoteAddr());
					// 获取上架图书数目
					BookCount book_count = bookRepository.selectBookCount();
					if (book_count!=null) {
						ret.addObject("bookCount", book_count.getCount());
						session.setAttribute("validBookCount", book_count.getCount());
					}
					//获取待审核人员数目
					int notReviewUserCount = systemManagerService.getNotReviewUserCount();
					ret.addObject("notReviewUserCount",notReviewUserCount);
					session.setAttribute("notReviewUserCount", notReviewUserCount);
					//获取有效人员数目
					int validUserCount = systemManagerService.getValidUserCount();
					ret.addObject("validUserCount",validUserCount);
					session.setAttribute("validUserCount", validUserCount);
					ret.addObject("message", "系统管理员"+ManagerByName.getManagerName()+",欢迎登陆！お久しぶりですね！");
					//取得当前在线用户列表
					@SuppressWarnings("unchecked")
					ConcurrentHashMap<String, String> onlineUserMap = (ConcurrentHashMap<String, String>) application.getAttribute("onlineUserMap");
					ret.addObject("onlineUsersMap",onlineUserMap);
					ret.setViewName("home_manager");
				} else {
					ret.addObject("message", "密码不正确！");
					ret.setViewName("login");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}

	// 用户申请验证
	@RequestMapping(value = "/userApply")
	public ModelAndView userApply(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取出REQUSET中的用户注册信息
			String user_code = "DJB" + String.format("%03d", Integer.parseInt(request.getParameter("user_code")));
			String user_fullname = request.getParameter("user_fullname");
			String open_id = "USER-1234-EFGH-"
					+ String.format("%04d", Integer.parseInt(request.getParameter("user_code")));
			// 将信息存入到对象中，进行查询和插入操作
			User user = new User();
			if (user_fullname != null) {
				user.setUserCode(user_code);
				user.setUserFullname(user_fullname);
				user.setOpenId(open_id);
				// 查询是否已存在该用户
				User ifUserExists = userService.getUserByCode(user_code);
				// 如果用户已经存在
				if (ifUserExists != null) {
					ret.addObject("message", "该员工已经存在，请重新选择员工号！");
					ret.setViewName("apply");
				} else {
					// 有效用户不存在，符合条件，插入申请记录
					// 判断当前用户的申请状态
					UserReview userReview = new UserReview();
					userReview.setUserName(user_fullname);
					userReview.setUserCode(user_code);
					userReview.setOpenId(open_id);
					UserReview userStatus = userReviewService.selectUserReviewStatus(userReview);
					if (userStatus != null) {
						// 判断是否曾经发起过申请
						// 查询申请状态
						if (userStatus.getReviewFlag().equals("0")) {
							// 等待审核
							ret.addObject("userCode", user_code);
							ret.addObject("userName", user_fullname);
							String yyyy = userStatus.getApplyAt().substring(0, 4);
							String MM = userStatus.getApplyAt().substring(4, 6);
							String dd = userStatus.getApplyAt().substring(6, 8);
							String apply_at = yyyy + "-" + MM + "-" + dd;
							ret.addObject("applyAt", apply_at);
							ret.addObject("message", "您的申请正在等待审核");
							ret.setViewName("reviewing");
						} else if (userStatus.getReviewFlag().equals("1")) {
							// 审核通过，跳转到登录
							ret.addObject("userName", user_fullname);
							ret.addObject("userName", user_code);
							ret.addObject("message", "您的申请已通过审核！");
							ret.setViewName("login");
//							return new ModelAndView("forward:start", null);
						} else if (userStatus.getReviewFlag().equals("2")) {
							// 审核被拒绝，跳转到被拒绝页面
							ret.addObject("openId", open_id);
							ret.addObject("userCode", user_code);
							ret.addObject("userName", user_fullname);
							ret.addObject("message", "您的申请已被拒绝");
							ret.setViewName("review_confuse");
						}
					} else {
						// 不存在申请记录
						// 发起申请操作
						Integer applyResult = userReviewService.createUserReview(userReview);
						if (applyResult != null) {
							// 等待审核
							ret.addObject("userCode", user_code);
							ret.addObject("userName", user_fullname);
							String apply_at = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
							ret.addObject("applyAt", apply_at);
							// 插入操作成功
							ret.addObject("message", "申请提交成功！");
							ret.setViewName("reviewing");
						}
					}
				}
			} else {
				// 插入记录失败，返回注册页面
				ret.addObject("message", "注册失败");
				ret.setViewName("apply");
			}
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "注册失败");
			ret.setViewName("apply");
		}
		return ret;
	}

	// 申请被拒绝后再次提交
	@RequestMapping(value = "/applyAgain")
	public ModelAndView applyAgain(@RequestParam("userCode") String user_code,
			@RequestParam("userName") String user_fullame, @RequestParam("openId") String open_id, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();

		// 发起申请操作
		UserReview userReview = new UserReview();
		userReview.setUserName(user_fullame);
		userReview.setUserCode(user_code);
		userReview.setOpenId(open_id);
		Integer applyResult = userReviewService.createUserReview(userReview);
		if (applyResult != null) {
			// 插入操作成功
			ret.addObject("message", "已重新提交申请");
			ret.setViewName("login");
			// return new ModelAndView("forward:start", null);
		}
		return ret;
	}

	// 跳转到当前在线用户页面
	@RequestMapping(value = "/onlineUsersListPage")
	public ModelAndView onlineUsersListPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		//取出服务器全局对象
		ServletContext application = request.getServletContext();
		//取得当前在线用户列表
		@SuppressWarnings("unchecked")
		ConcurrentHashMap<String, String> onlineUserMap = (ConcurrentHashMap<String, String>) application.getAttribute("onlineUserMap");
		ret.addObject("onlineUsersMap",onlineUserMap);
		ret.setViewName("online_users_list");
		return ret;
	}
	
	// 跳转到消息中心
	@RequestMapping(value = "/msgCenter")
	public ModelAndView msgCenter(@RequestParam("userCode") String userCode,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		ret.addObject("receiveUserCode",userCode);
		User user = userService.getUserByCode(userCode);
		ret.addObject("receiveUserName",user.getUserFullname());
		ret.setViewName("msg_center");
		return ret;
	}
	
	// 跳转到注册页面
	@RequestMapping(value = "/apply")
	public ModelAndView apply(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		ret.setViewName("apply");
		return ret;
	}

	// 跳回到登录页面
	@RequestMapping(value = "/start")
	public ModelAndView start(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		ret.setViewName("login");
		return ret;
	}

	// 跳转到用户中心页面
	@RequestMapping(value = "/user")
	public ModelAndView user(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			Integer user_id = user.getUserId();
			// 取得各种点赞收藏借阅总数
			User userInfo = userService.getUserBookInfoById(user_id);
			ret.addObject("userInfo", userInfo);
			// 取得我的借阅详情
			List<BookBorrow> myBorrows = bookBorrowService.getMyBorrow(user_id);
			ret.addObject("myBorrows", myBorrows);
			ret.addObject("mode", 1);
			// 跳转到用户中心页面
			ret.setViewName("user");
		} else {
			ret.setViewName("login");
		}

		return ret;
	}

	// 修改计划还书时间
	@RequestMapping(value = "/changePlanReturnDate")
	public ModelAndView changePlanReturnDate(@RequestParam("book_id") Integer book_id,
			@RequestParam("planReturnDate") String plan_return_date, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			Integer user_id = user.getUserId();
			BookBorrow bookBorrow = new BookBorrow();
			bookBorrow.setBookId(book_id);
			bookBorrow.setUserId(user_id);
			bookBorrow.setPlanReturnDate(plan_return_date.replace("-", ""));
			Integer changeResult = bookBorrowService.updatePlanReturnDate(bookBorrow);
			if (changeResult != null) {
				ret.addObject("message", "修改计划还书日期成功！");
				// 取得各种点赞收藏借阅总数
				User userInfo = userService.getUserBookInfoById(user_id);
				ret.addObject("userInfo", userInfo);
				// 取得我的借阅详情
				List<BookBorrow> myBorrows = bookBorrowService.getMyBorrow(user_id);
				ret.addObject("myBorrows", myBorrows);
				ret.addObject("mode", 1);
				// 跳转到用户中心页面
				ret.setViewName("user");
			} else {
				ret.addObject("message", "修改计划还书日期失败！");
				// 取得各种点赞收藏借阅总数
				User userInfo = userService.getUserBookInfoById(user_id);
				ret.addObject("userInfo", userInfo);
				// 取得我的借阅详情
				List<BookBorrow> myBorrows = bookBorrowService.getMyBorrow(user_id);
				ret.addObject("myBorrows", myBorrows);
				ret.addObject("mode", 1);
				// 跳转到用户中心页面
				ret.setViewName("user");
			}
		} else {
			ret.addObject("message", "请登录后再操作！");
			ret.setViewName("start");
		}

		return ret;
	}

	// 用户中心的还书
	@RequestMapping(value = "/returnBook")
	public ModelAndView returnBook(@RequestParam("book_id") Integer book_id, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			Integer user_id = user.getUserId();
			BookBorrow bookBorrow = new BookBorrow();
			bookBorrow.setBookId(book_id);
			bookBorrow.setUserId(user_id);
			// 更新借书记录
			Integer returnResult = bookBorrowService.returnBook(bookBorrow);
			// 更新图书状态
			Integer changeStatusResult = bookService.enableBorrowStatus(book_id);
			if (returnResult != null && changeStatusResult != null) {
				// 取得各种点赞收藏借阅总数
				User userInfo = userService.getUserBookInfoById(user_id);
				ret.addObject("userInfo", userInfo);
				// 取得我的借阅详情
				List<BookBorrow> myBorrows = bookBorrowService.getMyBorrow(user_id);
				ret.addObject("myBorrows", myBorrows);
				ret.addObject("mode", 1);
				ret.addObject("message", "还书成功！");
				// 跳转到用户中心页面
				ret.setViewName("user");
			} else {
				ret.addObject("message", "还书失败！");
				// 取得各种点赞收藏借阅总数
				User userInfo = userService.getUserBookInfoById(user_id);
				ret.addObject("userInfo", userInfo);
				// 取得我的借阅详情
				List<BookBorrow> myBorrows = bookBorrowService.getMyBorrow(user_id);
				ret.addObject("myBorrows", myBorrows);
				ret.addObject("mode", 1);
				// 跳转到用户中心页面
				ret.setViewName("user");
			}
		} else {
			ret.addObject("message", "请登录后再操作！");
			ret.setViewName("start");
		}

		return ret;
	}

	// 用户中心的收藏详情
	@RequestMapping(value = "/getMyFavorite")
	public ModelAndView getMyFavorite(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			Integer user_id = user.getUserId();
			// 取得各种点赞收藏借阅总数
			User userInfo = userService.getUserBookInfoById(user_id);
			ret.addObject("userInfo", userInfo);
			try {
				List<Book> books = bookService.getMyFavorite(user_id);
				ret.addObject("books", books);
				ret.addObject("mode", 2);
				ret.setViewName("user");
			} catch (Exception e) {
				System.out.print(e.toString());
				ret.addObject("message", "查询失败");
				List<Book> books = bookService.getAllBooks(0);
				ret.addObject("books", books);
				ret.addObject("mode", 2);
				ret.setViewName("user");
			}
		} else {
			ret.addObject("message", "请登录后再操作！");
			ret.setViewName("start");
		}

		return ret;
	}

	// 用户中心的点赞详情
	@RequestMapping(value = "/getMyPraise")
	public ModelAndView getMyPraise(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			Integer user_id = user.getUserId();
			// 取得各种点赞收藏借阅总数
			User userInfo = userService.getUserBookInfoById(user_id);
			ret.addObject("userInfo", userInfo);
			try {
				List<Book> books = bookService.getMyPraise(user_id);
				ret.addObject("books", books);
				ret.addObject("mode", 3);
				ret.setViewName("user");
			} catch (Exception e) {
				System.out.print(e.toString());
				ret.addObject("message", "查询失败");
				List<Book> books = bookService.getAllBooks(0);
				ret.addObject("books", books);
				ret.addObject("mode", 3);
				ret.setViewName("user");
			}
		} else {
			ret.addObject("message", "请登录后再操作！");
			ret.setViewName("start");
		}

		return ret;
	}
	
	// 用户中心的评论详情
	@RequestMapping(value = "/getMyComment")
	public ModelAndView getMyComment(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			Integer user_id = user.getUserId();
			// 取得各种点赞收藏借阅总数
			User userInfo = userService.getUserBookInfoById(user_id);
			ret.addObject("userInfo", userInfo);
			try {
				List<BookComment> bookComments = bookCommentService.getMyComment(user_id);
				ret.addObject("bookComments", bookComments);
				ret.addObject("mode", 4);
				ret.setViewName("user");
			} catch (Exception e) {
				System.out.print(e.toString());
				ret.addObject("message", "查询失败");
				return new ModelAndView("forward:user",null);
			}
		} else {
			ret.addObject("message", "请登录后再操作！");
			ret.setViewName("start");
		}
		
		return ret;
	}

	// 用户中心收藏页面创建借书记录
	@RequestMapping(value = "/userFavoriteBorrowBook")
	public ModelAndView userFavoriteBorrowBook(@RequestParam("book_id") Integer book_id, Model model,
			HttpServletRequest request, HttpServletResponse response) {
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
				Integer user_id = user.getUserId();
				// 取得各种点赞收藏借阅总数
				User userInfo = userService.getUserBookInfoById(user_id);
				ret.addObject("userInfo", userInfo);
				// 取得收藏的图书列表
				List<Book> books = bookService.getMyFavorite(user_id);
				ret.addObject("books", books);
				ret.addObject("mode", 2);
				ret.setViewName("user");
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
				} else {
					ret.addObject("message", "借书失败！");
				}
			}
			Integer user_id = user.getUserId();
			// 取得各种点赞收藏借阅总数
			User userInfo = userService.getUserBookInfoById(user_id);
			ret.addObject("userInfo", userInfo);
			// 取得收藏的图书列表
			List<Book> books = bookService.getMyFavorite(user_id);
			ret.addObject("books", books);
			ret.addObject("mode", 2);
			ret.setViewName("user");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "登录失败");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 用户中心点赞页面创建借书记录
	@RequestMapping(value = "/userPraiseBorrowBook")
	public ModelAndView userPraiseBorrowBook(@RequestParam("book_id") Integer book_id, Model model,
			HttpServletRequest request, HttpServletResponse response) {
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
				Integer user_id = user.getUserId();
				// 取得各种点赞收藏借阅总数
				User userInfo = userService.getUserBookInfoById(user_id);
				ret.addObject("userInfo", userInfo);
				// 取得收藏的图书列表
				List<Book> books = bookService.getMyPraise(user_id);
				ret.addObject("books", books);
				ret.addObject("mode", 3);
				ret.setViewName("user");
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
				} else {
					ret.addObject("message", "借书失败！");
				}
			}
			Integer user_id = user.getUserId();
			// 取得各种点赞收藏借阅总数
			User userInfo = userService.getUserBookInfoById(user_id);
			ret.addObject("userInfo", userInfo);
			// 取得收藏的图书列表
			List<Book> books = bookService.getMyPraise(user_id);
			ret.addObject("books", books);
			ret.addObject("mode", 3);
			ret.setViewName("user");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "登录失败");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 用户中心删除评论
	@RequestMapping(value = "/deleteComment")
	public ModelAndView deleteComment(@RequestParam("commentId") Integer commentId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user != null) {
				BookComment bookComment = new BookComment();
				bookComment.setCommentId(commentId);;
				Integer delete_result = bookCommentService.deleteBookComment(bookComment);
				if (delete_result != null) {
					ret.addObject("message", "删除评论成功！");
				} else {
					ret.addObject("message", "删除评论失败！");
				}
			}else {
				ret.addObject("message", "请先登录后再操作");
				ret.setViewName("start");
			}
			Integer user_id = user.getUserId();
			// 取得各种点赞收藏借阅总数
			User userInfo = userService.getUserBookInfoById(user_id);
			ret.addObject("userInfo", userInfo);
			try {
				List<BookComment> bookComments = bookCommentService.getMyComment(user_id);
				ret.addObject("bookComments", bookComments);
				ret.addObject("mode", 4);
				ret.setViewName("user");
			} catch (Exception e) {
				System.out.print(e.toString());
				ret.addObject("message", "查询失败");
				return new ModelAndView("forward:user",null);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "删除评论失败");
			ret.setViewName("user");
		}
		return ret;
	}
}
