package com.gjj.params;

import javax.validation.constraints.NotNull;

public class LoginParam {
	
	@NotNull
	public String account;
	
	@NotNull
	public String password;
	
	public String code;

}
