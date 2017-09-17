
package com.djb.art.repository;

import com.djb.art.cms.annotations.MybatisRepository;
import com.djb.art.model.Book;
import com.djb.art.model.SystemManager;

@MybatisRepository
public interface SystemManagerRepository {

	//根据用户名查询管理员对象
	public SystemManager selectManagerIdByName(String managerName);
	
	//用不到但是不好删除的方法
	@Deprecated
	public SystemManager selectManagerIdByPassword(String managerPassword);
	
	//查询待审核人员数目
	public Integer selectNotReviewUserCount();

	//查询有效成员数目
	public Integer selectValidUserCount();

	//插入图书
	public Integer insertBook(Book book);

	//上架图书
	public Integer enableBook(Book book);
	
	//下架图书
	public Integer disableBook(Book book);

	//更新图书
	public Integer updateBook(Book book);

	//删除图书编辑页面的图片
	public Integer deleteBookPic(Book book);

	//添加管理员权限
	public Integer addManagerRight(Integer userId);

	//删除管理员权限
	public Integer deleteManagerRight(Integer userId);

	//查询个人最大借阅本数
	public String selectMaxBorrowCount();
	
}
