package com.djb.art.service;

import java.util.List;

import com.djb.art.model.BookSourceType;

public interface BookSourceTypeService {
	
	//获取所有有效的图书来源
	public List<BookSourceType> getBookSourceTypes();

	//获取所有图书来源
	public List<BookSourceType> getAllBookSourceTypes();

	//生效来源
	public Integer enableBookSourceType(Integer bookSourceTypeId);
	
	//失效来源
	public Integer disableBookSourceType(Integer bookSourceTypeId);

	//删除图书来源（真的删除记录）
	public Integer deleteBookSourceType(Integer bookSourceTypeId);

}
