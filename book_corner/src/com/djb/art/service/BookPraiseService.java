package com.djb.art.service;

import com.djb.art.model.BookPraise;

public interface BookPraiseService {

	public Integer createPraise(BookPraise BookPraise);

	public Integer cancelPraise(BookPraise BookPraise);
	
	public BookPraise selectPraise(BookPraise bookPraise);

}
