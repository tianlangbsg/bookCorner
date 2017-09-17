package com.djb.art.repository;


import java.util.List;

import com.djb.art.cms.annotations.MybatisRepository;
import com.djb.art.model.SystemParmset;

@MybatisRepository
public interface SystemParmsetRepository {

	//取出系统设置的参数
	public List<SystemParmset> selectParams();

	//设置默认借阅周期长度
	public Integer updateDefaultBorrowDays(String days);

	//设置最大借阅本数
	public Integer updateMaxBorrowCount(String count);

}
