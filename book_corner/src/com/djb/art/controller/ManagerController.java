package com.djb.art.controller;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.djb.art.model.Book;
import com.djb.art.model.BookBorrow;
import com.djb.art.model.BookBorrowOption;
import com.djb.art.model.BookCount;
import com.djb.art.model.BookOption;
import com.djb.art.model.BookSourceType;
import com.djb.art.model.BookType;
import com.djb.art.model.SystemManager;
import com.djb.art.model.SystemParmset;
import com.djb.art.model.User;
import com.djb.art.model.UserReview;
import com.djb.art.repository.BookRepository;
import com.djb.art.service.BookBorrowService;
import com.djb.art.service.BookService;
import com.djb.art.service.BookSourceTypeService;
import com.djb.art.service.BookTypeService;
import com.djb.art.service.SystemManagerService;
import com.djb.art.service.UserReviewService;
import com.djb.art.service.UserService;

@RestController
public class ManagerController {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookBorrowService bookBorrowService;

	@Autowired
	private SystemManagerService systemManagerService;

	@Autowired
	private BookTypeService bookTypeService;
	
	@Autowired
	private BookSourceTypeService bookSourceTypeService;
	
	@Autowired
	private UserReviewService userReviewService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private BookRepository bookRepository;

	private String picPath = null;
	
	// ajax 测试
	@RequestMapping(value = "/ajaxChat")
	public ModelAndView ajaxChat(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			PrintWriter out = response.getWriter();
			String msg = request.getParameter("msg");
			out.print(msg);
			//out.print("ajax測試");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 用户网页聊天(发送消息)
	@RequestMapping(value = "/userChatSendMsg")
	public void userChatSendMsg( HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
			//取出服务器全局对象
			ServletContext application = request.getServletContext();
			//取出发送人员工号
			int sendUserCode = Integer.parseInt(request.getParameter("sendUserCode").toUpperCase().replace("DJB", ""));
			//取出发送人姓名
			String sendUserName= request.getParameter("sendUserName");
			//取出接收人员工号
			int receiveUserCode = Integer.parseInt(request.getParameter("receiveUserCode").toUpperCase().replace("DJB", ""));
			//取得该会话的消息数组
			//计算出两个人本次会话的消息数组名(员工号小的在前)
			String msgArrName = "usermsg:";
			if(sendUserCode>receiveUserCode) {
				msgArrName = msgArrName+receiveUserCode+"AND"+sendUserCode;
			}else {
				msgArrName = msgArrName+sendUserCode+"AND"+receiveUserCode;
			}
			@SuppressWarnings("unchecked")
			CopyOnWriteArrayList<String> chatMsg = (CopyOnWriteArrayList<String>) application.getAttribute(msgArrName);
			if(chatMsg==null) {
				chatMsg = new CopyOnWriteArrayList<String>();
			}
			//取得发送用户的消息文本内容
			String msg = request.getParameter("msg");
			chatMsg.add(sendUserName+"::"+df.format(new Date())+"\r\n"+msg);
			application.setAttribute(msgArrName, chatMsg);
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
	}
	
	// 用户网页聊天(接收消息)
	@RequestMapping(value = "/userChatReceiveMsg")
	public void userChatReceiveMsg( HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//取出服务器全局对象
			ServletContext application = request.getServletContext();
			//取出发送人员工号
			int sendUserCode = Integer.parseInt(request.getParameter("sendUserCode").toUpperCase().replace("DJB", ""));
			//取出接收人员工号
			int receiveUserCode = Integer.parseInt(request.getParameter("receiveUserCode").toUpperCase().replace("DJB", ""));
			//取出客户端消息总数
			int clientMsgCount = Integer.parseInt(request.getParameter("msgCount"));
			//取得该会话的消息数组
			//计算出两个人本次会话的消息数组名(员工号小的在前)
			String msgArrName = "usermsg:";
			if(sendUserCode>receiveUserCode) {
				msgArrName = msgArrName+receiveUserCode+"AND"+sendUserCode;
			}else {
				msgArrName = msgArrName+sendUserCode+"AND"+receiveUserCode;
			}
			//取得该会话的消息数组
			@SuppressWarnings("unchecked")
			CopyOnWriteArrayList<String> chatMsg = (CopyOnWriteArrayList<String>) application.getAttribute(msgArrName);
			//从服务端取得该用户的所有消息
			String msg = "";
			int serverMsgCount = 0;
			if (chatMsg!=null) {
				serverMsgCount = chatMsg.size();
				if (serverMsgCount>clientMsgCount) {
//					for (String m : chatMsg) {
//						msg = msg + m + "\r\n";
//					}
					for(int i=clientMsgCount;i<serverMsgCount;i++) {
						msg = msg + chatMsg.get(i) + "\r\n";
					}
					PrintWriter out = response.getWriter();
					out.print(serverMsgCount + "_" + msg);
					out.flush();
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ModelAndView().setViewName("start");
		}
	}
	
	// 跳转到消息中心页面
	@RequestMapping(value = "/ajaxChatPage")
	public ModelAndView ajaxChatPage(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			ret.setViewName("ajaxChatPage");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员跳转到录入图书页面
	@RequestMapping(value = "/insertBookPage")
	public ModelAndView insertBookPage(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 初始化图书类型下拉框
			List<BookType> bookTypes = bookTypeService.getBookTypes();
			ret.addObject("bookTypes", bookTypes);
			// 初始化图书来源下拉框
			List<BookSourceType> bookSourceTypes = bookSourceTypeService.getBookSourceTypes();
			ret.addObject("bookSourceTypes", bookSourceTypes);
			// 初始化员工捐赠下拉框
			List<User> contributors = userService.getAllUsers();
			ret.addObject("contributors", contributors);
			ret.setViewName("insert_book");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员跳回到管理员起始页
	@RequestMapping(value = "/homeManagerPage")
	public ModelAndView homeManagerPage(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				//取出服务器全局对象
				ServletContext application = request.getServletContext();
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
				@SuppressWarnings("unchecked")
				ConcurrentHashMap<String, String> onlineUserMap = (ConcurrentHashMap<String, String>) application.getAttribute("onlineUserMap");
				ret.addObject("onlineUsersMap",onlineUserMap);
				ret.setViewName("home_manager");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员跳转到用户权限管理页面（管理员列表）
	@RequestMapping(value = "/userAdminPage")
	public ModelAndView userAdminPage(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if (systemManager == null) {
				ret.addObject("message", "添加管理员操作需要系统管理员权限！请登录");
				ret.setViewName("login");
				return ret;
			}
			if(systemManager != null) {
				if(systemManager.getManagerPassword()==null) {
					ret.addObject("message", "您是图书管理员，不具备添加管理员权限，请登录系统管理员账号");
					ret.setViewName("login");
					return ret;
				}
				// 查找所有的管理员列表
				List<User> users = userService.getBookManagers();
				ret.addObject("users", users);
				ret.addObject("user_admin_type", 1);
				ret.setViewName("user_admin");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员跳转到用户权限管理页面（添加管理员页面）
	@RequestMapping(value = "/addUserAdminPage")
	public ModelAndView addUserAdminPage(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 查找所有的非管理员列表
			List<User> users = userService.getNotBookManagers();
			ret.addObject("users", users);
			ret.addObject("user_admin_type", 2);
			ret.setViewName("user_admin");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员跳转到设置页面
	@RequestMapping(value = "/bookSetPage")
	public ModelAndView bookSetPage(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取得参数LIST
			List<SystemParmset> systemParmsets= systemManagerService.getParams();
			ret.addObject("systemParmsets", systemParmsets);
			ret.setViewName("book_set");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员跳转到图书类别管理
	@RequestMapping(value = "/bookTypePage")
	public ModelAndView bookTypePage( HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取得全部的图书类别
			List<BookType> bookTypes= bookTypeService.getAllBookTypes();
			ret.addObject("bookTypes", bookTypes);
			ret.setViewName("book_type");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员跳转到图书来源类别管理
	@RequestMapping(value = "/bookSourceTypePage")
	public ModelAndView bookSourceTypePage( HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取得全部的图书来源类别
			List<BookSourceType> bookSourceTypes= bookSourceTypeService.getAllBookSourceTypes();
			ret.addObject("bookSourceTypes", bookSourceTypes);
			ret.setViewName("book_resource");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员跳转到用户审核页面
	@RequestMapping(value = "/userReviewPage")
	public ModelAndView userReviewPage( HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取得待审核人员名单
			List<UserReview> userReviews= userReviewService.getNotReviewUsers();
			ret.addObject("userReviews", userReviews);
			ret.addObject("review_type", 1);
			ret.setViewName("user_review");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员跳转到用户排行(借阅数排行)页面
	@RequestMapping(value = "/userChartBorrow")
	public ModelAndView userChartBorrow( HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取得用户借阅排行榜
			List<User> users= userService.getUserBorrowchart();
			ret.addObject("users", users);
			ret.addObject("user_chart_type", 1);
			ret.setViewName("user_chart");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员跳转到用户排行(加入时间升序)页面
	@RequestMapping(value = "/userChartJoinAtAsc")
	public ModelAndView userChartJoinAtAsc( HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取得用户排行榜(加入时间升序)
			List<User> users= userService.getUserChartJoinAtAsc();
			ret.addObject("users", users);
			ret.addObject("user_chart_type", 2);
			ret.setViewName("user_chart");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员跳转到用户排行(加入时间降序)页面
	@RequestMapping(value = "/userChartJoinAtDesc")
	public ModelAndView userChartJoinAtDesc( HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取得用户排行榜(加入时间升序)
			List<User> users= userService.getUserChartJoinAtDesc();
			ret.addObject("users", users);
			ret.addObject("user_chart_type", 3);
			ret.setViewName("user_chart");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 跳转到用户书单
	@RequestMapping(value = "/userBookList")
	public ModelAndView userBookList(@RequestParam("userId") Integer userId, 
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				//根据ID取出用户
				User user = userService.getUserById(userId);
				ret.addObject("user", user);
				//取出用户书单信息
				List<BookBorrow> bookBorrows = bookBorrowService.getBorrowsByByUserId(userId);
				ret.addObject("bookBorrows", bookBorrows);
				//跳转到用户书单页面
				ret.setViewName("user_book_list");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 批量通过用户审核
	@RequestMapping(value = "/adoptUsers")
	public ModelAndView adoptUsers(@RequestParam("idList") String idList,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		String message = "";
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			}
			String id[] = idList.split(",");
			for (int i = 0; i < id.length; i++) {
				if (!id[i].equals("")) {
					System.out.println(Integer.parseInt(id[i]));
					//修改user_review表中的审核状态
					Integer adoptUsersResult = userReviewService.adoptReviewUser(Integer.parseInt(id[i]));
					UserReview userReview = userReviewService.getUserById(Integer.parseInt(id[i]));
					//往user_info表中插入新的用户数据记录
					User user = new User();
					user.setUserCode(userReview.getUserCode());
					user.setUserFullname(userReview.getUserName());
					user.setOpenId(userReview.getOpenId());
					user.setApplyAt(userReview.getApplyAt().replace("-", ""));
					user.setUpdatedUserId(systemManager.getManagerId());
					user.setCreatedUserId(systemManager.getManagerId());
					Integer createUserResult = userService.createUser(user);
					if(adoptUsersResult!=null) {
						message = message+id[i]+"通过成功;";
					}else{
						message = message+id[i]+"通过失败;";
					}
					if(createUserResult!=null) {
						message = message+id[i]+"添加用户成功;";
					}else{
						message = message+id[i]+"添加用户失败;";
					}
				}
			}
			// 取得通过审核人员名单
			List<UserReview> userReviews= userReviewService.getNotReviewUsers();
			ret.addObject("userReviews", userReviews);
			ret.addObject("review_type", 1);
			ret.addObject("message", message);
			ret.setViewName("user_review");
		} catch (Exception e) {
			e.printStackTrace();
			// 取得通过审核人员名单
			List<UserReview> userReviews= userReviewService.getNotReviewUsers();
			ret.addObject("userReviews", userReviews);
			ret.addObject("review_type", 2);
			ret.addObject("message", "操作异常!");
			ret.setViewName("user_review");
		}
		return ret;
	}
	
	// 批量拒绝用户审核
	@RequestMapping(value = "/rejectUsers")
	public ModelAndView rejectUsers(@RequestParam("idList") String idList,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		String message = "";
		try {
			String id[] = idList.split(",");
			for (int i = 0; i < id.length; i++) {
				if (!id[i].equals("")) {
					// 删除来源类别
					Integer rejectUsersResult = userReviewService.rejectReviewUser(Integer.parseInt(id[i]));
					if (rejectUsersResult != null) {
						message = message + id[i] + "拒绝成功;";
					} else {
						message = message + id[i] + "拒绝失败;";
					} 
				}
			}
			// 取得被等待审核人员名单
			List<UserReview> userReviews= userReviewService.getNotReviewUsers();
			ret.addObject("message",message);
			ret.addObject("userReviews", userReviews);
			ret.addObject("review_type", 1);
			ret.setViewName("user_review");
		} catch (Exception e) {
			e.printStackTrace();
			// 取得等待审核人员名单
			List<UserReview> userReviews= userReviewService.getNotReviewUsers();
			ret.addObject("userReviews", userReviews);
			ret.addObject("message", "操作异常!");
			ret.addObject("review_type", 3);
			ret.setViewName("user_review");
		}
		return ret;
	}
	
	
	// 批量添加管理员权限
	@RequestMapping(value = "/addManagerRight")
	public ModelAndView addManagerRight(@RequestParam("idList") String idList,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		String message = "";
		try {
			String id[] = idList.split(",");
			for (int i = 0; i < id.length; i++) {
				if (!id[i].equals("")) {
					System.out.println(Integer.parseInt(id[i]));
					Integer addManagerRightResult = systemManagerService.addManagerRight(Integer.parseInt(id[i]));
					if(addManagerRightResult!=null) {
						message = message+id[i]+"添加管理员权限成功;";
					}else{
						message = message+id[i]+"添加管理员权限失败;";
					}
				}
			}
			// 查找所有的非管理员列表
			List<User> users = userService.getNotBookManagers();
			ret.addObject("users", users);
			ret.addObject("user_admin_type", 2);
			ret.setViewName("user_admin");
		} catch (Exception e) {
			e.printStackTrace();
			// 取得通过审核人员名单
			List<UserReview> userReviews= userReviewService.getNotReviewUsers();
			ret.addObject("userReviews", userReviews);
			ret.addObject("review_type", 2);
			ret.addObject("message", "操作异常!");
			ret.setViewName("user_review");
		}
		return ret;
	}
	
	// 批量删除管理员权限
	@RequestMapping(value = "/deleteManagerRight")
	public ModelAndView deleteManagerRight(@RequestParam("idList") String idList,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		String message = "";
		try {
			String id[] = idList.split(",");
			for (int i = 0; i < id.length; i++) {
				if (!id[i].equals("")) {
					System.out.println(Integer.parseInt(id[i]));
					Integer addManagerRightResult = systemManagerService.deleteManagerRight(Integer.parseInt(id[i]));
					if(addManagerRightResult!=null) {
						message = message+id[i]+"删除管理员权限成功;";
					}else{
						message = message+id[i]+"删除管理员权限失败;";
					}
				}
			}
			// 查找所有的管理员列表
			List<User> users = userService.getBookManagers();
			ret.addObject("users", users);
			ret.addObject("user_admin_type", 1);
			ret.setViewName("user_admin");
		} catch (Exception e) {
			e.printStackTrace();
			// 取得通过审核人员名单
			List<UserReview> userReviews= userReviewService.getNotReviewUsers();
			ret.addObject("userReviews", userReviews);
			ret.addObject("review_type", 2);
			ret.addObject("message", "操作异常!");
			ret.setViewName("user_review");
		}
		return ret;
	}
	
	// 用户审核页面通过申请用户列表
	@RequestMapping(value = "/adoptReviewUsers")
	public ModelAndView adoptReviewUsers( HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取得通过审核人员名单
			List<UserReview> userReviews= userReviewService.getAdoptReviewUsers();
			ret.addObject("userReviews", userReviews);
			ret.addObject("review_type", 2);
			ret.setViewName("user_review");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 用户审核页面被拒绝申请用户列表
	@RequestMapping(value = "/rejectReviewUsers")
	public ModelAndView rejectReviewUsers( HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取得被拒绝审核人员名单
			List<UserReview> userReviews= userReviewService.getRejectReviewUsers();
			ret.addObject("userReviews", userReviews);
			ret.addObject("review_type", 3);
			ret.setViewName("user_review");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 成员名单页面有效用户列表
	@RequestMapping(value = "/validUsers")
	public ModelAndView validUsers( HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取得有效成员名单
			List<User> users= userService.getVaildUsers();
			ret.addObject("users", users);
			ret.addObject("user_type", 1);
			ret.setViewName("user_list");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 成员名单页面失效用户列表
	@RequestMapping(value = "/invalidUsers")
	public ModelAndView invalidUsers( HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 取得失效成员名单
			List<User> users= userService.getInvaildUsers();
			ret.addObject("users", users);
			ret.addObject("user_type", 2);
			ret.setViewName("user_list");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 跳转到编辑用户信息页面
	@RequestMapping(value = "/editUserPage")
	public ModelAndView editUserPage(@RequestParam("userId") Integer userId, 
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				//取出要编辑的用户信息
				User user = userService.getUserById(userId);
				ret.addObject("user", user);
				ret.addObject("userCode", user.getUserCode().replace("DJB", ""));
				//跳转到用户编辑信息页面
				ret.setViewName("user_edit");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 提交编辑用户信息
	@RequestMapping(value = "/editUser")
	public ModelAndView editUser(@RequestParam("userId") Integer userId, 
			@RequestParam("userFullname") String userFullname, 
			@RequestParam("userCode") String userCode, 
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				User user = new User();
				user.setUserId(userId);
				user.setUserFullname(userFullname);
				user.setUserCode("DJB"+String.format("%03d", Integer.parseInt(request.getParameter("userCode"))));
				user.setUpdatedUserId(systemManager.getManagerId());
				//提交编辑用户信息
				Integer updateUserResult = userService.updateUser(user);
				if(updateUserResult!=null) {
					ret.addObject("message","用户信息更新成功！");
				}else {
					ret.addObject("message","用户信息更新失败！");
				}
				//从数据库中取出修改后的用户对象
				User nextuser = userService.getUserById(userId);
				ret.addObject("user", nextuser);
				ret.addObject("userCode", nextuser.getUserCode().replace("DJB", "").trim());
				//跳转到用户编辑信息页面
				ret.setViewName("user_edit");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 使用户生效
	@RequestMapping(value = "/enableUser")
	public ModelAndView enableUser(@RequestParam("userId") Integer userId, 
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				//使用户生效
				User user = new User();
				user.setUserId(userId);
				user.setUpdatedUserId(systemManager.getManagerId());
				Integer enableUserResult = userService.enableUser(user);
				if(enableUserResult!=null) {
					ret.addObject("message","用户信息更新成功！");
				}else {
					ret.addObject("message","用户信息更新失败！");
				}
				//从数据库中取出修改后的用户对象
				User nextuser = userService.getUserById(userId);
				ret.addObject("user", nextuser);
				ret.addObject("userCode", nextuser.getUserCode().replace("DJB", "").trim());
				//跳转到用户编辑信息页面
				ret.setViewName("user_edit");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 使用户失效
	@RequestMapping(value = "/disableUser")
	public ModelAndView disableUser(@RequestParam("userId") Integer userId, 
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				//使用户生效
				User user = new User();
				user.setUserId(userId);
				user.setUpdatedUserId(systemManager.getManagerId());
				Integer disbleUserResult = userService.disableUser(user);
				if(disbleUserResult!=null) {
					ret.addObject("message","用户信息更新成功！");
				}else {
					ret.addObject("message","用户信息更新失败！");
				}
				//从数据库中取出修改后的用户对象
				User nextuser = userService.getUserById(userId);
				ret.addObject("user", nextuser);
				ret.addObject("userCode", nextuser.getUserCode().replace("DJB", "").trim());
				//跳转到用户编辑信息页面
				ret.setViewName("user_edit");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 更新图书类别
	@RequestMapping(value = "/updateBookType")
	public ModelAndView updateBookType(@RequestParam("bookTypeId") Integer bookTypeId,
			@RequestParam("bookTypeName") String bookTypeName,
			@RequestParam("bookTypeColor") String bookTypeColor,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//更新选中的图书类别
			BookType bookType = new BookType();
			bookType.setBookTypeId(bookTypeId);
			bookType.setBookTypeName(bookTypeName);
			bookType.setBookTypeColor(bookTypeColor.replace("#", "").toUpperCase());
			Integer updateTypeResult = systemManagerService.updateBookType(bookType);
			if(updateTypeResult!=null){
				ret.addObject("message", "更新图书类型成功！");
			}else {
				ret.addObject("message", "更新图书类型失败！");
			}
			// 取得全部的图书类别
			List<BookType> bookTypes= bookTypeService.getAllBookTypes();
			ret.addObject("bookTypes", bookTypes);
			ret.setViewName("book_type");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "该颜色已存在，请重新选择！");
			// 取得全部的图书类别
			List<BookType> bookTypes= bookTypeService.getAllBookTypes();
			ret.addObject("bookTypes", bookTypes);
			ret.setViewName("book_type");
		}
		return ret;
	}
	
	// 更新来源类别
	@RequestMapping(value = "/updateBookSourceType")
	public ModelAndView updateBookSourceType(@RequestParam("bookSourceTypeId") Integer bookSourceTypeId,
			@RequestParam("bookSourceTypeName") String bookSourceTypeName,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//更新选中的图书类别
			BookSourceType bookSourceType = new BookSourceType();
			bookSourceType.setSourceTypeId(bookSourceTypeId);
			bookSourceType.setSourceTypeName(bookSourceTypeName);
			Integer updateSourceTypeResult = systemManagerService.updateBookSourceType(bookSourceType);
			if(updateSourceTypeResult!=null){
				ret.addObject("message", "更新来源类型成功！");
			}else {
				ret.addObject("message", "更新来源类型失败！");
			}
			// 取得全部的来源类别
			List<BookSourceType> bookSourceTypes= bookSourceTypeService.getAllBookSourceTypes();
			ret.addObject("bookSourceTypes", bookSourceTypes);
			ret.setViewName("book_resource");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "该来源名已存在，请重新选择！");
			// 取得全部的来源类别
			List<BookSourceType> bookSourceTypes= bookSourceTypeService.getAllBookSourceTypes();
			ret.addObject("bookSourceTypes", bookSourceTypes);
			ret.setViewName("book_resource");
		}
		return ret;
	}
	
	// 新建图书类别
	@RequestMapping(value = "/insertBookType")
	public ModelAndView insertBookType(
			@RequestParam("bookTypeName") String bookTypeName,
			@RequestParam("bookTypeColor") String bookTypeColor,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//更新选中的图书类别
			BookType bookType = new BookType();
			bookType.setBookTypeName(bookTypeName);
			bookType.setBookTypeColor(bookTypeColor.replace("#", "").toUpperCase());
			Integer updateTypeResult = systemManagerService.insertBookType(bookType);
			if(updateTypeResult!=null){
				ret.addObject("message", "新建图书类型成功！");
			}else {
				ret.addObject("message", "新建图书类型失败！");
			}
			// 取得全部的图书类别
			List<BookType> bookTypes= bookTypeService.getAllBookTypes();
			ret.addObject("bookTypes", bookTypes);
			ret.setViewName("book_type");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "该颜色已存在，请重新选择！");
			// 取得全部的图书类别
			List<BookType> bookTypes= bookTypeService.getAllBookTypes();
			ret.addObject("bookTypes", bookTypes);
			ret.setViewName("book_type");
		}
		return ret;
	}
	
	// 新建来源类别
	@RequestMapping(value = "/insertBookSourceType")
	public ModelAndView insertBookSourceType(
			@RequestParam("bookSourceTypeName") String bookSourceTypeName,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//更新选中的图书类别
			BookSourceType bookSourceType = new BookSourceType();
			bookSourceType.setSourceTypeName(bookSourceTypeName.trim());
			Integer insertSourceTypeResult = systemManagerService.insertSourceType(bookSourceType);
			if(insertSourceTypeResult!=null){
				ret.addObject("message", "新建图书来源类型成功！");
			}else {
				ret.addObject("message", "新建图书来源类型失败！");
			}
			// 取得全部的图书来源类别
			List<BookSourceType> bookSourceTypes= bookSourceTypeService.getAllBookSourceTypes();
			ret.addObject("bookSourceTypes", bookSourceTypes);
			ret.setViewName("book_resource");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "该类型名字已存在，请重新选择！");
			// 取得全部的图书来源类别
			List<BookSourceType> bookSourceTypes= bookSourceTypeService.getAllBookSourceTypes();
			ret.addObject("bookSourceTypes", bookSourceTypes);
			ret.setViewName("book_resource");
		}
		return ret;
	}
	
	// 删除图书类别
	@RequestMapping(value = "/deleteBookType")
	public ModelAndView deleteBookType(@RequestParam("bookTypeId") Integer bookTypeId,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 删除图书类别
			Integer deleteBookTypeResult = bookTypeService.deleteBookType(bookTypeId);
			if(deleteBookTypeResult!=null) {
				ret.addObject("message", "删除成功！");
			}else{
				ret.addObject("message", "删除失败！");
			}
			// 取得全部的图书类别
			List<BookType> bookTypes= bookTypeService.getAllBookTypes();
			ret.addObject("bookTypes", bookTypes);
			ret.setViewName("book_type");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 删除来源类别
	@RequestMapping(value = "/deleteBookSourceType")
	public ModelAndView deleteBookSourceType(@RequestParam("bookSourceTypeId") Integer bookSourceTypeId,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 删除来源类别
			Integer deleteBookSourceTypeResult = bookSourceTypeService.deleteBookSourceType(bookSourceTypeId);
			if(deleteBookSourceTypeResult!=null) {
				ret.addObject("message", "删除成功！");
			}else{
				ret.addObject("message", "删除失败！");
			}
			// 取得全部的图书类别
			List<BookSourceType> bookSourceTypes= bookSourceTypeService.getAllBookSourceTypes();
			ret.addObject("bookSourceTypes", bookSourceTypes);
			ret.setViewName("book_resource");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 批量删除来源类别
	@RequestMapping(value = "/deleteBookSourceTypes")
	public ModelAndView deleteBookSourceTypes(@RequestParam("idList") String idList,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		String message = "";
		try {
			String id[] = idList.split(",");
			for (int i = 0; i < id.length; i++) {
				// 删除来源类别
				Integer deleteBookSourceTypeResult = bookSourceTypeService.deleteBookSourceType(Integer.parseInt(id[i]));
				if(deleteBookSourceTypeResult!=null) {
					message = message+id[i]+"类型删除成功;";
				}else{
					message = message+id[i]+"类型删除成功;";
				}
			}
			// 取得全部的图书类别
			List<BookSourceType> bookSourceTypes= bookSourceTypeService.getAllBookSourceTypes();
			ret.addObject("bookSourceTypes", bookSourceTypes);
			ret.addObject("message", message);
			ret.setViewName("book_resource");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", message);
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 使图书类别生效
	@RequestMapping(value = "/enableBookType")
	public ModelAndView enableBookType(@RequestParam("bookTypeId") Integer bookTypeId,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 使图书类别生效
			Integer deleteBookTypeResult = bookTypeService.enableBookType(bookTypeId);
			if(deleteBookTypeResult!=null) {
				ret.addObject("message", "生效成功！");
			}else{
				ret.addObject("message", "生效失败！");
			}
			// 取得全部的图书类别
			List<BookType> bookTypes= bookTypeService.getAllBookTypes();
			ret.addObject("bookTypes", bookTypes);
			ret.setViewName("book_type");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 使图书类别失效
	@RequestMapping(value = "/disableBookType")
	public ModelAndView disableBookType(@RequestParam("bookTypeId") Integer bookTypeId,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 使图书类别生效
			Integer deleteBookTypeResult = bookTypeService.disableBookType(bookTypeId);
			if(deleteBookTypeResult!=null) {
				ret.addObject("message", "失效成功！");
			}else{
				ret.addObject("message", "失效失败！");
			}
			// 取得全部的图书类别
			List<BookType> bookTypes= bookTypeService.getAllBookTypes();
			ret.addObject("bookTypes", bookTypes);
			ret.setViewName("book_type");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 使图书来源生效
	@RequestMapping(value = "/enableBookSourceType")
	public ModelAndView enableBookSourceType(@RequestParam("bookSourceTypeId") Integer bookSourceTypeId,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 使图书类别生效
			Integer enalbeBookSourceTypeResult = bookSourceTypeService.enableBookSourceType(bookSourceTypeId);
			if(enalbeBookSourceTypeResult!=null) {
				ret.addObject("message", "生效成功！");
			}else{
				ret.addObject("message", "生效失败！");
			}
			// 取得全部的图书类别
			List<BookSourceType> bookSourceTypes= bookSourceTypeService.getAllBookSourceTypes();
			ret.addObject("bookSourceTypes", bookSourceTypes);
			ret.setViewName("book_resource");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 使图书来源失效
	@RequestMapping(value = "/disableBookSourceType")
	public ModelAndView disableBookSourceType(@RequestParam("bookSourceTypeId") Integer bookSourceTypeId,
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			// 使图书类别生效
			Integer disalbeBookSourceTypeResult = bookSourceTypeService.disableBookSourceType(bookSourceTypeId);
			if(disalbeBookSourceTypeResult!=null) {
				ret.addObject("message", "失效成功！");
			}else{
				ret.addObject("message", "失效失败！");
			}
			// 取得全部的图书类别
			List<BookSourceType> bookSourceTypes= bookSourceTypeService.getAllBookSourceTypes();
			ret.addObject("bookSourceTypes", bookSourceTypes);
			ret.setViewName("book_resource");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统参数设置
	@RequestMapping(value = "/bookSet")
	public ModelAndView bookSet(@RequestParam("defaultBorrowDays") String defaultBorrowDays, 
			@RequestParam("maxBorrowCount") String maxBorrowCount, 
			HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				// 修改默认借阅周期
				Integer updateBorrowDaysResult = systemManagerService.setDefaultBorrowDays(defaultBorrowDays.trim());
				// 修改最大借阅本数
				Integer setMaxBorrowCountResult = systemManagerService.setMaxBorrowCount(maxBorrowCount.trim());
				if(updateBorrowDaysResult!=null&&setMaxBorrowCountResult!=null) {
					ret.addObject("message", "系统参数设置成功！");
				}
			}
			// 取得参数LIST
			List<SystemParmset> systemParmsets= systemManagerService.getParams();
			ret.addObject("systemParmsets", systemParmsets);
			ret.addObject("messgae", "系统参数更改成功！");
			//跳转到系统参数设置页面
			ret.setViewName("book_set");
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 系统管理员退出登录
	@RequestMapping(value = "/managerLogOut")
	public ModelAndView managerLogOut(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//取出服务器全局对象
			ServletContext application = request.getServletContext();
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if(systemManager!=null) {
				application.setAttribute("systemManager"+systemManager.getManagerId(), null);
			}
			//销毁session对象
			session.invalidate();
//			session.setAttribute("user", null);
//			session.setAttribute("system_manager", null);
			ret.addObject("message", "管理员已退出登录");
			ret.setViewName("login");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "退出失败");
			ret.setViewName("login");
		}
		return ret;
	}
	
	//判断图书表中是否已经存在相同的条形码
	@RequestMapping(value = "/ifHasBarcode")
	public ModelAndView ifHasBarcode(@RequestParam("barcode") Integer barcode,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			session.setAttribute("system_manager", null);
			ret.addObject("message", "管理员已退出登录");
			ret.setViewName("login");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "退出失败");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 跳转到图书管理页面
	@RequestMapping(value = "/bookManageListPage")
	public ModelAndView bookManageListPage(
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				// 获取所有的图书数目
				BookCount book_count_all = bookRepository.selectAllBookCount();
				// 将主页关键数据存入到session中
				// 查询到的图书数量
				session.setAttribute("book_count", book_count_all.getCount());
				// 页码数量
				session.setAttribute("page_count", book_count_all.getCount() / 10 + 1);
				// 当前页码
				session.setAttribute("page", 1);
				// 图书是否上下架
				session.setAttribute("del_flag", 0);
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
				//上下架选择
				Integer del_flag = (Integer) session.getAttribute("del_flag");
				//借阅次数升降序
				Integer borrow_times = (Integer) session.getAttribute("borrow_times");
				//录入时间升降序
				Integer created_time =(Integer) session.getAttribute("created_time");
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
				if (del_flag != null) {
					bookOption.setDel_flag(del_flag.intValue());
					request.setAttribute("del_flag", del_flag.intValue());
				} else {
					request.setAttribute("del_flag", 0);
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
				//执行查询
				List<Book> books = bookService.getBooksByOptionsManager(bookOption);
				ret.addObject("books", books);
				ret.addObject("book_count", book_count);
				ret.addObject("page_count",(book_count%10==0?book_count/10:book_count / 10 + 1));
				
				ret.setViewName("book_manage_list");
			}
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "登录失败");
			ret.setViewName("login");
		}
		return ret;
	}

	// 管理员条件查询带分页
	@RequestMapping(value = "/bookManageListQuery")
	public ModelAndView bookManageListQuery(@RequestParam("del_flag") Integer del_flag,
			@RequestParam("borrow_times") Integer borrow_times,
			@RequestParam("created_time") Integer created_time, @RequestParam("book_type") Integer book_type,
			@RequestParam("page") Integer page, @RequestParam("bookNameOrBarcode") String bookNameOrBarcode,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			}
			List<BookType> bookTypes = bookTypeService.getBookTypes();
			ret.addObject("bookTypes", bookTypes);
			BookOption bookOption = new BookOption();
			//对取出的主页参数进行初始化
			if (bookNameOrBarcode.trim() != null) {
				bookOption.setBookNameOrBarcode(bookNameOrBarcode.trim());
				request.setAttribute("bookNameOrBarcode", bookNameOrBarcode.trim());
			} else {
				request.setAttribute("bookNameOrBarcode", " ");
			}
			if (del_flag != null) {
				bookOption.setDel_flag(del_flag);
				request.setAttribute("del_flag", del_flag);
			} else {
				request.setAttribute("del_flag", 0);
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
			List<Book> books = bookService.getBooksByOptionsManager(bookOption);
			List<Book> books_count = bookService.getBooksByOptionsCountManager(bookOption);
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
			//上下架选择
			session.setAttribute("del_flag", del_flag);
			//借阅次数升降序
			session.setAttribute("borrow_times", borrow_times);
			//录入时间升降序
			session.setAttribute("created_time", created_time);
			//图书类型选择
			session.setAttribute("book_type", book_type);
			//模糊查询文本框
			session.setAttribute("bookNameOrBarcode", bookNameOrBarcode);
			ret.setViewName("book_manage_list");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("book_manage_list");
		}
		
		return ret;
	}
	
	// 从其他页面跳回到管理员图书管理时，保持原样
	@RequestMapping(value = "/backBookManageListQuery")
	public ModelAndView backBookManageListQuery(Model model, HttpServletRequest request, HttpServletResponse response) {
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
			//上下架选择状态
			Integer del_flag = (Integer) session.getAttribute("del_flag");
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
			if (del_flag != null) {
				bookOption.setDel_flag(del_flag.intValue());
				request.setAttribute("del_flag", del_flag.intValue());
			} else {
				request.setAttribute("del_flag", 0);
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
				bookOption.setPage(0);
				request.setAttribute("page", 1);
			}
			List<Book> books = bookService.getBooksByOptionsManager(bookOption);
			List<Book> books_count = bookService.getBooksByOptionsCountManager(bookOption);
			book_count = books_count.size();
			ret.addObject("books", books);
			if (book_count!=0) {
				ret.addObject("book_count", book_count);
				ret.addObject("page_count", (book_count % 10 == 0 ? book_count / 10 : book_count / 10 + 1));
			}else if(book_count==0){
				//取得所有图书总数
				book_count = bookService.getAllBookCount().getCount();
				ret.addObject("book_count", book_count);
				ret.addObject("page_count", (book_count % 10 == 0 ? book_count / 10 : book_count / 10 + 1));
			}
			//将主页关键数据存入到session中
			//查询到的图书数量
			session.setAttribute("book_count", book_count);
			//页码数量
			session.setAttribute("page_count",  book_count/10+1);
			//当前页码
			session.setAttribute("page", page);
			//上下架选择
			session.setAttribute("del_flag", del_flag);
			//借阅次数升降序
			session.setAttribute("borrow_times", borrow_times);
			//录入时间升降序
			session.setAttribute("created_time", created_time);
			//图书类型选择
			session.setAttribute("book_type", book_type);
			//模糊查询文本框
			session.setAttribute("bookNameOrBarcode", bookNameOrBarcode);
			ret.setViewName("book_manage_list");
		} catch (Exception e) {
			System.out.print(e.toString());
			ret.addObject("message", "查询失败");
			List<Book> books = bookService.getAllBooks(0);
			ret.addObject("books", books);
			ret.setViewName("home_book");
		}
		return ret;
	}

	// 详情页面系统管理员上架图书
	@RequestMapping(value = "/enableBook")
	public ModelAndView enableBook(@RequestParam("book_id") Integer book_id,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			//判断管理员是否登录
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				//取出管理员ID
				Integer managerId = systemManager.getManagerId();
				//创建一个图书对象存放参数
				Book book = new Book();
				book.setUpdatedUser(managerId);
				book.setBookId(book_id);
				//上架图书
				Integer enableResult = systemManagerService.enableBook(book);
				if(enableResult!=null) {
					ret.addObject("message", "上架成功！");
				}else {
					ret.addObject("message", "上架失败！");
				}
				return new ModelAndView("forward:bookDetailManage?book_id=" + book_id, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 详情页面系统管理员下架图书
	@RequestMapping(value = "/disableBook")
	public ModelAndView disableBook(@RequestParam("book_id") Integer book_id,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			//判断管理员是否登录
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				//取出管理员ID
				Integer managerId = systemManager.getManagerId();
				//创建一个图书对象存放参数
				Book book = new Book();
				book.setUpdatedUser(managerId);
				book.setBookId(book_id);
				//上架图书
				Integer enableResult = systemManagerService.disableBook(book);
				if(enableResult!=null) {
					ret.addObject("message", "下架成功！");
				}else {
					ret.addObject("message", "下架失败！");
				}
				return new ModelAndView("forward:bookDetailManage?book_id=" + book_id, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 图书管理页面系统管理员上架图书
	@RequestMapping(value = "/enableBookHome")
	public ModelAndView enableBookHome(@RequestParam("book_id") Integer book_id,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			//判断管理员是否登录
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				//取出管理员ID
				Integer managerId = systemManager.getManagerId();
				//创建一个图书对象存放参数
				Book book = new Book();
				book.setUpdatedUser(managerId);
				book.setBookId(book_id);
				//上架图书
				Integer enableResult = systemManagerService.enableBook(book);
				if(enableResult!=null) {
					ret.addObject("message", "上架成功！");
				}else {
					ret.addObject("message", "上架失败！");
				}
				return new ModelAndView("forward:backBookManageListQuery", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 图书管理页面系统管理员下架图书
	@RequestMapping(value = "/disableBookHome")
	public ModelAndView disableBookHome(@RequestParam("book_id") Integer book_id,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			//判断管理员是否登录
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				//取出管理员ID
				Integer managerId = systemManager.getManagerId();
				//创建一个图书对象存放参数
				Book book = new Book();
				book.setUpdatedUser(managerId);
				book.setBookId(book_id);
				//上架图书
				Integer enableResult = systemManagerService.disableBook(book);
				if(enableResult!=null) {
					ret.addObject("message", "下架成功！");
				}else {
					ret.addObject("message", "下架失败！");
				}
				return new ModelAndView("forward:backBookManageListQuery", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 查询单本图书图书的借阅历史
	@RequestMapping(value = "/bookBorrowHistory")
	public ModelAndView bookBorrowHistory(@RequestParam("book_id") Integer book_id,
			@RequestParam(name="return_status",required=false) Integer return_status,
			@RequestParam(name="start_date",required=false) String start_date,
			@RequestParam(name="end_date",required=false) String end_date,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			if(start_date==null) {
				start_date="";
			}
			if(end_date==null) {
				end_date="";
			}
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			//判断管理员是否登录
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				BookBorrowOption bookBorrowOption = new BookBorrowOption();
				bookBorrowOption.setBookId(book_id);
				bookBorrowOption.setReturnStatus(return_status);
				bookBorrowOption.setStartDate(start_date);
				bookBorrowOption.setEndDate(end_date);
				//根据条件查询单本图书的借阅历史
				if (book_id!=null) {
					List<BookBorrow> bookBorrows = bookBorrowService.getBorrowByBookId(bookBorrowOption);
					if(bookBorrows!=null) {
						ret.addObject("message", "查询成功！");
						ret.addObject("bookBorrows", bookBorrows);
						ret.addObject("book_id",book_id );
						if (return_status==null) {
							ret.addObject("return_status", 0);
						}else {
							ret.addObject("return_status", return_status);
						}
						ret.addObject("start_date",start_date );
						ret.addObject("end_date",end_date );
					}else {
						ret.addObject("message", "查询失败！");
						return new ModelAndView("forward:backBookManageListQuery", null);
					}
					ret.setViewName("book_history");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 借阅记录查询（带条件）
	@RequestMapping(value = "/bookBorrowRecord")
	public ModelAndView bookBorrowRecord(
			@RequestParam(name="return_status",required=false) Integer return_status,
			@RequestParam(name="start_date",required=false) String start_date,
			@RequestParam(name="end_date",required=false) String end_date,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			if(start_date==null) {
				start_date="";
			}
			if(end_date==null) {
				end_date="";
			}
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			//判断管理员是否登录
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				BookBorrowOption bookBorrowOption = new BookBorrowOption();
				bookBorrowOption.setReturnStatus(return_status);
				bookBorrowOption.setStartDate(start_date);
				bookBorrowOption.setEndDate(end_date);
				//根据条件查询单本图书的借阅历史
				List<BookBorrow> bookBorrows = bookBorrowService.getBorrowRecord(bookBorrowOption);
				if (bookBorrows != null) {
					ret.addObject("message", "查询成功！");
					ret.addObject("bookBorrows", bookBorrows);
					if (return_status == null) {
						ret.addObject("return_status", 0);
					} else {
						ret.addObject("return_status", return_status);
					}
					ret.addObject("start_date", start_date);
					ret.addObject("end_date", end_date);
				} else {
					ret.addObject("message", "查询失败！");
					return new ModelAndView("forward:backBookManageListQuery", null);
				}
				ret.setViewName("book_search");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 跳转到图书编辑页面
	@RequestMapping(value = "/bookEdit")
	public ModelAndView bookEdit(
			@RequestParam(name="book_id") Integer book_id,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			//判断管理员是否登录
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				// 初始化图书类型下拉框
				List<BookType> bookTypes = bookTypeService.getBookTypes();
				ret.addObject("bookTypes", bookTypes);
				// 初始化图书来源下拉框
				List<BookSourceType> bookSourceTypes = bookSourceTypeService.getBookSourceTypes();
				ret.addObject("bookSourceTypes", bookSourceTypes);
				// 初始化员工捐赠下拉框
				List<User> contributors = userService.getAllUsers();
				ret.addObject("contributors", contributors);
				//取出图书对象所有内容
				Book book = bookService.getEditBookById(book_id);
				ret.addObject("book",book);
				
				ret.setViewName("book_edit");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 图书编辑页面删除图片
	@RequestMapping(value = "/deleteBookPic")
	public ModelAndView deleteBookPic(
			@RequestParam(name="book_id") Integer book_id,
			@RequestParam(name="picId") Integer picId,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			//从session中取出管理员对象
			HttpSession session = request.getSession();
			SystemManager systemManager = (SystemManager) session.getAttribute("system_manager");
			//判断管理员是否登录
			if (systemManager == null) {
				ret.addObject("message", "请先登录后操作！");
				ret.setViewName("login");
			} else {
				//删除图片
				Book bookPicDelete = new Book();
				bookPicDelete.setPicId(picId);
				bookPicDelete.setBookId(book_id);
				Integer picDeleteResult = systemManagerService.deleteBookPic(bookPicDelete);
				if(picDeleteResult!=null) {
					ret.addObject("message","图片删除成功！");
				}else {
					ret.addObject("message","图片删除失败！");
				}
				// 初始化图书类型下拉框
				List<BookType> bookTypes = bookTypeService.getBookTypes();
				ret.addObject("bookTypes", bookTypes);
				// 初始化图书来源下拉框
				List<BookSourceType> bookSourceTypes = bookSourceTypeService.getBookSourceTypes();
				ret.addObject("bookSourceTypes", bookSourceTypes);
				//取出图书对象所有内容
				Book book = bookService.getEditBookById(book_id);
				ret.addObject("book",book);
				
				ret.setViewName("book_edit");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 录入新书
	@RequestMapping(value = "/insertBook")
	public ModelAndView insertBook(
			@RequestParam(name="book_type",required=false) Integer book_type,
			@RequestParam(name="book_source_type",required=false) Integer book_source_type,
			@RequestParam(name="contributor_id",required=false) Integer contributor_id,
			//@RequestParam(name="pic",required=false) MultipartFile pic,
			@RequestParam(name="picture1",required=false) MultipartFile picture1,
			@RequestParam(name="picture2",required=false) MultipartFile picture2,
			@RequestParam(name="picture3",required=false) MultipartFile picture3,
			@RequestParam(name="picture4",required=false) MultipartFile picture4,
			@RequestParam(name="picture5",required=false) MultipartFile picture5,
			@RequestParam(name="picture6",required=false) MultipartFile picture6,
			@RequestParam(name="picture7",required=false) MultipartFile picture7,
			@RequestParam(name="picture8",required=false) MultipartFile picture8,
			@RequestParam(name="picture9",required=false) MultipartFile picture9,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		picPath = request.getSession().getServletContext().getRealPath("")+"pics/";
//		String aa=request.getPathInfo();
//		System.out.println(aa);
		try {
			String barcode;
			String book_name;
			String book_intro;
			//上传的图片文件文件名
			//String pic_path = "";
			String picture1_path = "";
			String picture2_path = "";
			String picture3_path = "";
			String picture4_path = "";
			String picture5_path = "";
			String picture6_path = "";
			String picture7_path = "";
			String picture8_path = "";
			String picture9_path = "";
			//初始化参数
			if(request.getParameter("barcode").equals(null)) {
				barcode = "";
			}else {
				barcode = request.getParameter("barcode");
			}
			if(request.getParameter("book_name").equals(null)) {
				book_name = "";
			}else {
				book_name = request.getParameter("book_name");
			}
			if(request.getParameter("book_intro").equals(null)) {
				book_intro = "";
			}else {
				book_intro = request.getParameter("book_intro");
			}
			if(barcode.equals(null)) {
				barcode = "";
			}else {
				barcode = request.getParameter("barcode");
			}
			if(book_type == null) {
				book_type = 0;
			}
			if(book_source_type == null) {
				book_source_type = 0;
			}
			if(contributor_id == null) {
				contributor_id = 0;
			}
			String message = "";
			//如果图片文件不为空，则进行图片上传
//			if (!pic.getOriginalFilename().equals("")) {
//				pic_path = pic.getOriginalFilename();
//				if(uploadFile(pic)) {
//					message = message+pic.getOriginalFilename()+"成功！\r\n";
//				}else{
//					message = message+pic.getOriginalFilename()+"失败！\r\n";
//				};
//			}
			if (!picture1.getOriginalFilename().equals("")) {
				picture1_path = picture1.getOriginalFilename();
				if(uploadFile(picture1)) {
					message = message+picture1.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture1.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture2.getOriginalFilename().equals("")) {
				picture2_path = picture2.getOriginalFilename();
				if(uploadFile(picture2)) {
					message = message+picture2.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture2.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture3.getOriginalFilename().equals("")) {
				picture3_path = picture3.getOriginalFilename();
				if(uploadFile(picture3)) {
					message = message+picture3.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture3.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture4.getOriginalFilename().equals("")) {
				picture4_path = picture4.getOriginalFilename();
				if(uploadFile(picture4)) {
					message = message+picture4.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture4.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture5.getOriginalFilename().equals("")) {
				picture5_path = picture5.getOriginalFilename();
				if(uploadFile(picture5)) {
					message = message+picture5.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture5.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture6.getOriginalFilename().equals("")) {
				picture6_path = picture6.getOriginalFilename();
				if(uploadFile(picture6)) {
					message = message+picture6.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture6.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture7.getOriginalFilename().equals("")) {
				picture7_path = picture7.getOriginalFilename();
				if(uploadFile(picture7)) {
					message = message+picture7.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture7.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture8.getOriginalFilename().equals("")) {
				picture9_path = picture9.getOriginalFilename();
				if(uploadFile(picture8)) {
					message = message+picture8.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture8.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture9.getOriginalFilename().equals("")) {
				picture9_path = picture9.getOriginalFilename();
				if(uploadFile(picture9)) {
					message = message+picture9.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture9.getOriginalFilename()+"失败！\r\n";
				};
			}

			HttpSession session = request.getSession();
			//初始化图书对象的参数
			Book book = new Book();
			book.setBookBarcode(barcode);
			book.setBookName(book_name);
			book.setBookIntro(book_intro);
			book.setBookTypeId(book_type);
			book.setSourceId(book_source_type);
			book.setContributorId(contributor_id);
			//book.setPicPath(pic_path);
			if (!picture1_path.equals("")) {
				book.setPicture1(picture1_path);
			}
			if (!picture2_path.equals("")) {
				book.setPicture2(picture2_path);
			}
			if (!picture3_path.equals("")) {
				book.setPicture3(picture3_path);
			}
			if (!picture4_path.equals("")) {
				book.setPicture4(picture4_path);
			}
			if (!picture5_path.equals("")) {
				book.setPicture5(picture5_path);
			}
			if (!picture6_path.equals("")) {
				book.setPicture6(picture6_path);
			}
			if (!picture7_path.equals("")) {
				book.setPicture7(picture7_path);
			}
			if (!picture8_path.equals("")) {
				book.setPicture8(picture8_path);
			}
			if (!picture9_path.equals("")) {
				book.setPicture9(picture9_path);
			}
			SystemManager systemManager = (SystemManager)session.getAttribute("system_manager");
			if (systemManager!=null) {
				book.setUpdatedUser(systemManager.getManagerId());
				book.setCreatedUser(systemManager.getManagerId());
			}else {
				book.setUpdatedUser(0);
				book.setCreatedUser(0);
			}
			//往数据库中插入新的图书记录
			Integer insertResult = systemManagerService.insertBook(book);
			if(insertResult!=null) {
				ret.addObject("message", "图书记录插入成功！");
				//跳转到图书详情
				try {
					// 取出图书详情
					Integer book_id = null;
					if (bookService.getLastBook()!=null) {
						book_id = bookService.getLastBook().getBookId();
					}
					request.setAttribute("book_id",book_id);
					//ret.setViewName("insert_book");
					return new ModelAndView("redirect:bookDetailManage?book_id=" + book_id, null);
					//return new ModelAndView("forward:bookDetail",null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				ret.addObject("message", "图书记录插入失败！");
				ret.setViewName("insert_book");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	// 更新图书记录
	@RequestMapping(value = "/updateBook")
	public ModelAndView updateBook(//@RequestParam(name="pic",required=false) MultipartFile pic,
			@RequestParam(name="book_id") Integer book_id,
			@RequestParam(name="book_type",required=false) Integer book_type,
			@RequestParam(name="book_source_type",required=false) Integer book_source_type,
			@RequestParam(name="contributor_id",required=false) Integer contributor_id,
			@RequestParam(name="picture1",required=false) MultipartFile picture1,
			@RequestParam(name="picture2",required=false) MultipartFile picture2,
			@RequestParam(name="picture3",required=false) MultipartFile picture3,
			@RequestParam(name="picture4",required=false) MultipartFile picture4,
			@RequestParam(name="picture5",required=false) MultipartFile picture5,
			@RequestParam(name="picture6",required=false) MultipartFile picture6,
			@RequestParam(name="picture7",required=false) MultipartFile picture7,
			@RequestParam(name="picture8",required=false) MultipartFile picture8,
			@RequestParam(name="picture9",required=false) MultipartFile picture9,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView ret = new ModelAndView();
		try {
			picPath = request.getSession().getServletContext().getRealPath("")+"pics/";
			//System.out.println(request.getSession().getServletContext().getRealPath(""));
			String barcode;
			String book_name;
			String book_intro;
			//上传的图片文件文件名
			//String pic_path = "";
			String picture1_path = "";
			String picture2_path = "";
			String picture3_path = "";
			String picture4_path = "";
			String picture5_path = "";
			String picture6_path = "";
			String picture7_path = "";
			String picture8_path = "";
			String picture9_path = "";
			//初始化参数
			if(request.getParameter("barcode").equals(null)) {
				barcode = "";
			}else {
				barcode = request.getParameter("barcode");
			}
			if(request.getParameter("book_name").equals(null)) {
				book_name = "";
			}else {
				book_name = request.getParameter("book_name");
			}
			if(request.getParameter("book_intro").equals(null)) {
				book_intro = "";
			}else {
				book_intro = request.getParameter("book_intro");
			}
			if(barcode.equals(null)) {
				barcode = "";
			}else {
				barcode = request.getParameter("barcode");
			}
			if(book_type == null) {
				book_type = 0;
			}
			if(book_source_type == null) {
				book_source_type = 0;
			}
			if(contributor_id == null) {
				contributor_id = 0;
			}
			String message = "";
			//如果图片文件不为空，则进行图片上传
//			if (!pic.getOriginalFilename().equals("")) {
//				pic_path = pic.getOriginalFilename();
//				if(uploadFile(pic)) {
//					message = message+pic.getOriginalFilename()+"成功！\r\n";
//				}else{
//					message = message+pic.getOriginalFilename()+"失败！\r\n";
//				};
//			}
			if (!picture1.getOriginalFilename().equals("")) {
				picture1_path = picture1.getOriginalFilename();
				if(uploadFile(picture1)) {
					message = message+picture1.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture1.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture2.getOriginalFilename().equals("")) {
				picture2_path = picture2.getOriginalFilename();
				if(uploadFile(picture2)) {
					message = message+picture2.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture2.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture3.getOriginalFilename().equals("")) {
				picture3_path = picture3.getOriginalFilename();
				if(uploadFile(picture3)) {
					message = message+picture3.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture3.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture4.getOriginalFilename().equals("")) {
				picture4_path = picture4.getOriginalFilename();
				if(uploadFile(picture4)) {
					message = message+picture4.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture4.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture5.getOriginalFilename().equals("")) {
				picture5_path = picture5.getOriginalFilename();
				if(uploadFile(picture5)) {
					message = message+picture5.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture5.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture6.getOriginalFilename().equals("")) {
				picture6_path = picture6.getOriginalFilename();
				if(uploadFile(picture6)) {
					message = message+picture6.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture6.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture7.getOriginalFilename().equals("")) {
				picture7_path = picture7.getOriginalFilename();
				if(uploadFile(picture7)) {
					message = message+picture7.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture7.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture8.getOriginalFilename().equals("")) {
				picture9_path = picture9.getOriginalFilename();
				if(uploadFile(picture8)) {
					message = message+picture8.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture8.getOriginalFilename()+"失败！\r\n";
				};
			}
			if (!picture9.getOriginalFilename().equals("")) {
				picture9_path = picture9.getOriginalFilename();
				if(uploadFile(picture9)) {
					message = message+picture9.getOriginalFilename()+"成功！\r\n";
				}else{
					message = message+picture9.getOriginalFilename()+"失败！\r\n";
				};
			}
			
			HttpSession session = request.getSession();
			//初始化图书对象的参数
			Book book = new Book();
			book.setBookId(book_id);
			book.setBookBarcode(barcode);
			book.setBookName(book_name);
			book.setBookIntro(book_intro);
			book.setBookTypeId(book_type);
			book.setSourceId(book_source_type);
			book.setContributorId(contributor_id);
			//book.setPicPath(pic_path);
			if (!picture1_path.equals("")) {
				book.setPicture1(picture1_path);
			}
			if (!picture2_path.equals("")) {
				book.setPicture2(picture2_path);
			}
			if (!picture3_path.equals("")) {
				book.setPicture3(picture3_path);
			}
			if (!picture4_path.equals("")) {
				book.setPicture4(picture4_path);
			}
			if (!picture5_path.equals("")) {
				book.setPicture5(picture5_path);
			}
			if (!picture6_path.equals("")) {
				book.setPicture6(picture6_path);
			}
			if (!picture7_path.equals("")) {
				book.setPicture7(picture7_path);
			}
			if (!picture8_path.equals("")) {
				book.setPicture8(picture8_path);
			}
			if (!picture9_path.equals("")) {
				book.setPicture9(picture9_path);
			}
			SystemManager systemManager = (SystemManager)session.getAttribute("system_manager");
			if (systemManager!=null) {
				book.setUpdatedUser(systemManager.getManagerId());
				book.setCreatedUser(systemManager.getManagerId());
			}else {
				book.setUpdatedUser(0);
				book.setCreatedUser(0);
			}
			//往数据库中插入新的图书记录
			Integer insertResult = systemManagerService.updateBook(book);
			if(insertResult!=null) {
				ret.addObject("message", "图书记录插入成功！");
				//跳转到图书详情
				try {
					// 取出图书详情
					book_id = null;
					if (bookService.getLastBook()!=null) {
						book_id = bookService.getLastBook().getBookId();
					}
					request.setAttribute("book_id",book_id);
					//ret.setViewName("insert_book");
					return new ModelAndView("forward:bookDetailManage?book_id=" + book_id, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				ret.addObject("message", "图书记录插入失败！");
				ret.setViewName("insert_book");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.addObject("message", "登录失败！");
			ret.setViewName("login");
		}
		return ret;
	}
	
	/**
	 * 文件上传函数
	 * @param file
	 * @return
	 */
	private boolean uploadFile(MultipartFile file) {
		String file_name = file.getOriginalFilename();
//		File targetFile = new File("C:/Users/admin/eclipse-workspace/book_corner/WebContent/pics/" + file_name);
//		File targetFile = new File("C:/Users/admin/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/book_corner/pics" + file_name);
//		System.out.println(picPath);
		File targetFile = new File(picPath+ file_name);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}

}
