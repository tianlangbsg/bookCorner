package com.djb.art.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djb.art.model.BookFavorite;
import com.djb.art.repository.BookFavoriteRepository;
import com.djb.art.service.BookFavoriteService;

@Service
public class BookFavoriteServiceImpl implements BookFavoriteService {

	@Autowired
	private BookFavoriteRepository bookFavoriteRepository;

	@Override
	public Integer cancelFavorite(BookFavorite bookFavorite) {
		return bookFavoriteRepository.cancelFavorite(bookFavorite);
	}
 
	@Override
	public BookFavorite selectFavorite(BookFavorite bookFavorite) {
		return bookFavoriteRepository.selectIfFavorite(bookFavorite);
	}

	@Override
	public Integer updateFavorite(BookFavorite BookFavorite) {
		return bookFavoriteRepository.updateFavorite(BookFavorite);
	}

	@Override
	public Integer createFavorite(BookFavorite BookFavorite) {
		return bookFavoriteRepository.insertFavorite(BookFavorite);
	}

}
