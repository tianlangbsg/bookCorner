package com.djb.art.service;

import java.util.List;

import com.djb.art.model.BookType;

public interface BookTypeService {

	//取得有效的图书类别名字和ID
	public List<BookType> getBookTypes();
	
	//取得全部的图书类别
	public List<BookType> getAllBookTypes();

	//删除图书类型
	public Integer deleteBookType(Integer bookTypeId);
	
	//生效图书类型
	public Integer enableBookType(Integer bookTypeId);
	
	//失效图书类型
	public Integer disableBookType(Integer bookTypeId);

}
