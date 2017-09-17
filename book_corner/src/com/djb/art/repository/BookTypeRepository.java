package com.djb.art.repository;

import java.util.List;

import com.djb.art.cms.annotations.MybatisRepository;
import com.djb.art.model.BookType;

@MybatisRepository
public interface BookTypeRepository {
	
	//取出有效的图书类别和ID
	public List<BookType> selectBookTypes();
	
	//取出全部的图书类别
	public List<BookType> selectAllBookTypes();
	
	//删除图书类型
	public Integer deleteBookType(Integer bookTypeId);

	//更新图书类型
	public Integer updateBookType(BookType bookType);
	
	//生效图书类型
	public Integer enableBookType(Integer bookTypeId);
	
	//生效图书类型
	public Integer disableBookType(Integer bookTypeId);

	//新建图书类型
	public Integer insertBookType(BookType bookType);
	
}
