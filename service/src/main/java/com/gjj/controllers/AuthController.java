package com.gjj.controllers;

import com.gjj.constants.SessionConstant;
import com.gjj.dto.ExecuteResult;
import com.gjj.params.LoginParam;
import com.gjj.models.AdminUser;
import com.gjj.services.AdminUserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private AdminUserServer server;

	@PostMapping("login")
	public ExecuteResult<AdminUser> login(@RequestBody @Valid LoginParam param, HttpSession session) {
		ExecuteResult<AdminUser> result = server.getByAccount(param);
		if (result.isSuccess()) {
			session.setAttribute(SessionConstant.KEY_USER_ID, result.getValue().id);
		}
		return result;
	}
}
