package com.djb.art.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djb.art.model.BookType;
import com.djb.art.repository.BookTypeRepository;
import com.djb.art.service.BookTypeService;

@Service
public class BookSourceTypeServiceImpl implements BookTypeService {

	@Autowired
	private BookTypeRepository BookTypeRepository;

	@Override
	public List<BookType> getBookTypes() {
		return BookTypeRepository.selectBookTypes();
	}
	
	@Override
	public List<BookType> getAllBookTypes() {
		return BookTypeRepository.selectAllBookTypes();
	}

	@Override
	public Integer deleteBookType(Integer bookTypeId) {
		return BookTypeRepository.deleteBookType(bookTypeId);
	}

	@Override
	public Integer enableBookType(Integer bookTypeId) {
		return BookTypeRepository.enableBookType(bookTypeId);
	}

	@Override
	public Integer disableBookType(Integer bookTypeId) {
		return BookTypeRepository.disableBookType(bookTypeId);
	}

}
