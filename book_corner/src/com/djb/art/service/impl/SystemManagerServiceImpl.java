package com.djb.art.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djb.art.model.Book;
import com.djb.art.model.BookSourceType;
import com.djb.art.model.BookType;
import com.djb.art.model.SystemManager;
import com.djb.art.model.SystemParmset;
import com.djb.art.repository.BookSourceTypeRepository;
import com.djb.art.repository.BookTypeRepository;
import com.djb.art.repository.SystemManagerRepository;
import com.djb.art.repository.SystemParmsetRepository;
import com.djb.art.service.SystemManagerService;

@Service
public class SystemManagerServiceImpl implements SystemManagerService {
	
	@Autowired
	private SystemManagerRepository systemManagerRepository;
	
	@Autowired
	private SystemParmsetRepository systemParmsetRepository;
	
	@Autowired
	private BookTypeRepository bookTypeRepository;
	
	@Autowired
	private BookSourceTypeRepository bookSourceTypeRepository;

	@Override
	public SystemManager selectManagerIdByName(String manager_name) {
		return systemManagerRepository.selectManagerIdByName(manager_name);
	}

	@Override
	public SystemManager selectManagerIdByPassword(String manager_password) {
		return systemManagerRepository.selectManagerIdByPassword(manager_password);
	}

	@Override
	public Integer getNotReviewUserCount() {
		return systemManagerRepository.selectNotReviewUserCount();
	}

	@Override
	public Integer getValidUserCount() {
		return systemManagerRepository.selectValidUserCount();
	}

	@Override
	public Integer insertBook(Book book) {
		return systemManagerRepository.insertBook(book);
	}

	@Override
	public Integer enableBook(Book book) {
		return systemManagerRepository.enableBook(book);
	}
	
	@Override
	public Integer disableBook(Book book) {
		return systemManagerRepository.disableBook(book);
	}

	@Override
	public Integer updateBook(Book book) {
		return systemManagerRepository.updateBook(book);
	}

	@Override
	public Integer deleteBookPic(Book book) {
		return systemManagerRepository.deleteBookPic(book);
	}

	@Override
	public List<SystemParmset> getParams() {
		return systemParmsetRepository.selectParams();
	}

	@Override
	public Integer setDefaultBorrowDays(String days) {
		return systemParmsetRepository.updateDefaultBorrowDays(days);
	}

	@Override
	public Integer setMaxBorrowCount(String count) {
		return systemParmsetRepository.updateMaxBorrowCount(count);
	}

	@Override
	public Integer updateBookType(BookType bookType) {
		return bookTypeRepository.updateBookType(bookType);
	}
	
	@Override
	public Integer insertBookType(BookType bookType) {
		return bookTypeRepository.insertBookType(bookType);
	}

	@Override
	public Integer insertSourceType(BookSourceType bookSourceType) {
		return bookSourceTypeRepository.insertSourceType(bookSourceType);
	}


	@Override
	public Integer updateBookSourceType(BookSourceType bookSourceType) {
		return bookSourceTypeRepository.updateBookSourceType(bookSourceType);
	}

	@Override
	public Integer addManagerRight(Integer userId) {
		return systemManagerRepository.addManagerRight(userId);
	}

	@Override
	public Integer deleteManagerRight(Integer userId) {
		return systemManagerRepository.deleteManagerRight(userId);
	}

	@Override
	public String getMaxBorrowCount() {
		return systemManagerRepository.selectMaxBorrowCount();
	}
}
