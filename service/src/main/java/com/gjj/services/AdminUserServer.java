package com.gjj.services;

import com.gjj.dto.ExecuteResult;
import com.gjj.params.LoginParam;
import com.gjj.models.AdminUser;
import com.gjj.repositories.AdminUserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminUserServer {
	
	private static final String DEFAULT_ACCOUNT = "admin";

	private static final String DEFAULT_PASSWORD = "123456";
	
	@Autowired
	private AdminUserRepository repository;
	
	public ExecuteResult<AdminUser> getByAccount(LoginParam param){
		if (StringUtils.isBlank(param.password)) {
			return ExecuteResult.fail("请输入密码");
		}

		AdminUser user = repository.findOneByAccountOrPhone(param.account, param.account);
		if (user == null) {
			if (DEFAULT_ACCOUNT.equals(param.account) && DEFAULT_PASSWORD.equals(param.password)) {
				AdminUser po = new AdminUser();
				po.account = param.account;
				po.name = "系统管理员";
				po.addedDate = new Date();
				po.admin = true;
				po.lastLoginTime = po.addedDate;
				repository.save(po);
				return ExecuteResult.ok(po);
			}
			
			return ExecuteResult.fail("用户不存在");
		}
		
		if (StringUtils.isBlank(user.password)) {
			if (DEFAULT_PASSWORD.equals(param.password)) {
				return ExecuteResult.ok(user);
			}
			return ExecuteResult.fail("密码错误");
		}
		
		String pwd = StringUtils.isBlank(user.salt) ? param.password : (param.password + user.salt);
		String md5Hex = DigestUtils.md5Hex(pwd);
		if (!md5Hex.equals(user.password)) {
			return ExecuteResult.fail("密码错误");
		}
		
		user.lastLoginTime = new Date();
		repository.save(user);
		return ExecuteResult.ok(user);
		
	}

}
