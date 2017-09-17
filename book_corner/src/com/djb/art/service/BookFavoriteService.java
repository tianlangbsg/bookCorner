package com.djb.art.service;

import com.djb.art.model.BookFavorite;

public interface BookFavoriteService {

	public Integer createFavorite(BookFavorite BookFavorite);

	public Integer cancelFavorite(BookFavorite BookFavorite);
	
	public BookFavorite selectFavorite(BookFavorite bookFavorite);

	public Integer updateFavorite(BookFavorite BookFavorite);
	
}
