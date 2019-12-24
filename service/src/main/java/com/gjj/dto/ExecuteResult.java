package com.gjj.dto;

import java.io.Serializable;

public final class ExecuteResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4467073382353735654L;

	/**
	 * 执行成功
	 */
	private boolean success;

	private T value;

	/**
	 * 错误码
	 */
	private int errorCode;

	/**
	 * 错误信息
	 */
	private String message;

	public boolean isSuccess() {
		return success;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	private ExecuteResult() {

	}

	public static <T> ExecuteResult<T> ok() {
		ExecuteResult<T> result = new ExecuteResult<>();
		result.errorCode = 0;
		result.success = true;
		return result;
	}

	public static <T> ExecuteResult<T> ok(T value) {
		ExecuteResult<T> result = new ExecuteResult<T>();
		result.errorCode = 0;
		result.success = true;
		result.value = value;
		return result;
	}

	public static <T> ExecuteResult<T> fail(String message) {
		ExecuteResult<T> result = new ExecuteResult<>();
		result.errorCode = -1;
		result.success = false;
		result.message = message;
		return result;
	}

	public static <T> ExecuteResult<T> fail(int errorCode) {
		ExecuteResult<T> result = new ExecuteResult<>();
		result.errorCode = errorCode;
		result.success = false;
		return result;
	}

	public static <T> ExecuteResult<T> fail(int errorCode, String message) {
		ExecuteResult<T> result = new ExecuteResult<>();
		result.errorCode = errorCode;
		result.success = false;
		result.message = message;
		return result;
	}
}
