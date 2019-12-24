package com.gjj.dao;

import com.gjj.models.AdminUser;

public interface UserRepositoryCustom {

	AdminUser findOneByAccountOrPhone(String account, String phone);

}
