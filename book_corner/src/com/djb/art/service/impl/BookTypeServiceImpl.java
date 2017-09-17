package com.djb.art.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djb.art.model.BookSourceType;
import com.djb.art.model.BookType;
import com.djb.art.repository.BookTypeRepository;
import com.djb.art.repository.BookSourceTypeRepository;
import com.djb.art.service.BookSourceTypeService;
import com.djb.art.service.BookTypeService;

@Service
public class BookTypeServiceImpl implements BookSourceTypeService {

	@Autowired
	private BookSourceTypeRepository bookSourceTypeRepository;

	@Override
	public List<BookSourceType> getBookSourceTypes() {
		return bookSourceTypeRepository.selectBookSourceTypes();
	}

	@Override
	public List<BookSourceType> getAllBookSourceTypes() {
		return bookSourceTypeRepository.selectAllBookSourceTypes();
	}

	@Override
	public Integer enableBookSourceType(Integer bookSourceTypeId) {
		return bookSourceTypeRepository.enableBookSourceType(bookSourceTypeId);
	}

	@Override
	public Integer disableBookSourceType(Integer bookSourceTypeId) {
		return bookSourceTypeRepository.disableBookSourceType(bookSourceTypeId);
	}

	@Override
	public Integer deleteBookSourceType(Integer bookSourceTypeId) {
		return bookSourceTypeRepository.deleteBookSourceType(bookSourceTypeId);
	}


}
