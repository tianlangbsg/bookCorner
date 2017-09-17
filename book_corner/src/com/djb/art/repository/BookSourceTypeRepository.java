package com.djb.art.repository;

import java.util.List;

import com.djb.art.cms.annotations.MybatisRepository;
import com.djb.art.model.BookSourceType;
import com.djb.art.model.BookType;

@MybatisRepository
public interface BookSourceTypeRepository {
	
	//获取有效的图书来源
	public List<BookSourceType> selectBookSourceTypes();

	//获取有效的图书来源
	public List<BookSourceType> selectAllBookSourceTypes();

	//新建图书来源
	public Integer insertSourceType(BookSourceType bookSourceType);

	//更新图书来源
	public Integer updateBookSourceType(BookSourceType bookSourceType);

	//生效来源
	public Integer enableBookSourceType(Integer bookSourceTypeId);
	
	//失去效来源
	public Integer disableBookSourceType(Integer bookSourceTypeId);

	//删除来源
	public Integer deleteBookSourceType(Integer bookSourceTypeId);

}
