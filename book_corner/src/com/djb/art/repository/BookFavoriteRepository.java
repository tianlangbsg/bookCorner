package com.djb.art.repository;


import com.djb.art.cms.annotations.MybatisRepository;
import com.djb.art.model.BookFavorite;

@MybatisRepository
public interface BookFavoriteRepository {

	public Integer insertFavorite(BookFavorite bookFavorite);

	public BookFavorite selectIfFavorite(BookFavorite bookFavorite);

	public Integer updateFavorite(BookFavorite bookFavorite);

	public Integer cancelFavorite(BookFavorite bookFavorite);

}
