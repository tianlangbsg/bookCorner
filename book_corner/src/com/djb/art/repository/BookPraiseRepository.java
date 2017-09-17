package com.djb.art.repository;


import com.djb.art.cms.annotations.MybatisRepository;
import com.djb.art.model.BookPraise;

@MybatisRepository
public interface BookPraiseRepository {

	public Integer insertPraise(BookPraise bookPraise);

	public BookPraise selectIfPraise(BookPraise bookPraise);

	public Integer updatePraise(BookPraise bookPraise);

	public Integer cancelPraise(BookPraise bookPraise);

}
