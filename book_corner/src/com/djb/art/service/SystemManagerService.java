
package com.djb.art.service;

import java.util.List;

import com.djb.art.model.Book;
import com.djb.art.model.BookSourceType;
import com.djb.art.model.BookType;
import com.djb.art.model.SystemManager;
import com.djb.art.model.SystemParmset;

public interface SystemManagerService {

	public SystemManager selectManagerIdByName(String manager_name);
	
	public SystemManager selectManagerIdByPassword(String manager_password);
	
	//查询待审核人员数目
	public Integer getNotReviewUserCount();

	//查询待审核人员数目
	public Integer getValidUserCount();

	//插入图书
	public Integer insertBook(Book book);
	
	//更新图书
	public Integer updateBook(Book book);
	
	//上架图书
	public Integer enableBook(Book book);
	
	//下架图书
	public Integer disableBook(Book book);
	
	//删除图书详情编辑页面的图片
	public Integer deleteBookPic(Book book);
	
	//查找系统设置参数
	public List<SystemParmset> getParams();
	
	//设置默认借阅周期
	public Integer setDefaultBorrowDays(String days);
	
	//设置默认借阅周期
	public Integer setMaxBorrowCount(String count);

	//更新图书类型
	public Integer updateBookType(BookType bookType);

	//新建图书类型
	public Integer insertBookType(BookType bookType);

	//新建来源类型
	public Integer insertSourceType(BookSourceType bookSourceType);

	//更新来源类型
	public Integer updateBookSourceType(BookSourceType bookSourceType);

	//添加管理员权限
	public Integer addManagerRight(Integer userId);
	
	//删除管理员权限
	public Integer deleteManagerRight(Integer userId);
	
	//查询每人最大借阅本数
	public String getMaxBorrowCount();
	

}
