package com.djb.art.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djb.art.model.BookPraise;
import com.djb.art.repository.BookPraiseRepository;
import com.djb.art.service.BookPraiseService;

@Service
public class BookPraiseServiceImpl implements BookPraiseService {

	@Autowired
	private BookPraiseRepository bookPraiseRepository;

	@Override
	public Integer createPraise(BookPraise bookPraise) {
		return bookPraiseRepository.insertPraise(bookPraise);
	}

	@Override
	public Integer cancelPraise(BookPraise bookPraise) {
		return bookPraiseRepository.cancelPraise(bookPraise);
	}

	@Override
	public BookPraise selectPraise(BookPraise bookPraise) {
		return bookPraiseRepository.selectIfPraise(bookPraise);
	}


}
