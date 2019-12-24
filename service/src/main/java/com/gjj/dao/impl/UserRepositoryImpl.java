package com.gjj.dao.impl;

import com.gjj.abstracts.AbstractDao;
import com.gjj.dao.UserRepositoryCustom;
import com.gjj.models.AdminUser;
import com.gjj.repositories.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public class UserRepositoryImpl extends AbstractDao implements UserRepositoryCustom{
	
	@Autowired
	private AdminUserRepository repository;

	@Override
	public AdminUser findOneByAccountOrPhone(String account, String phone) {
		Page<AdminUser> page = repository.findAllByAccountOrPhone(account, phone, getFirstPageable());
		return page.hasContent() ? page.iterator().next() : null;
	}

}
