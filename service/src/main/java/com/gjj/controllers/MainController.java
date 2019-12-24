package com.gjj.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("main")
public class MainController {

	@PostMapping("getUser")
	public String getUser() {
		return "周靖富(管理员)";
	}

}
