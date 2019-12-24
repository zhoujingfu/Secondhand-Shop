package com.gjj.enums;

/**
 * Created by peng on 2017/8/29.
 */
public enum ErrorMessage {
    EMPTY_LOGIN_NAME("用户名不能为空"), EMPTY_PASSWORD("密码不能为空"),
    NOT_FOUND_USER("该用户不存在"),
    ERROR_LOGIN__NAME_OR_PASSWORD("用户名或密码错误"),
    ERROR_CHANGE_TYPE("类型转化出错"),
    USERNAME_EXIST("用户名已存在"),
    IMG_NOT_EMPTY("图片已存在"),
    IMG_FORMAT_ERROR("图片格式化出错"),
    SAVE_IMG_ERROE("图片保存出错"),
    NOT_FOUND_GOODS("该商品不存在"),
    COMMENT_NOT_EXIST("该评论已不存在"),
    LOGIN_NAME_FORBIDDEN("账号已被停用，请与管理员联系"),
    INVALID_TOKEN("身份认证令牌验证失败，请重新登录"),
    LOGIN_NAME_NOT_EXIST("账号不存在"), FILE_SIZE_TOO_LARGE("上传文件过大，单个文件大小不能超过2MB"),
    ENTITY_ALREADY_EXIST("已存在，操作失败"),
    DATA_CONFLICT("数据处理冲突"),
    FORBIDDEN_DELETE("该数据已被使用，禁止删除"),
    NOT_FOUND("请求的资源未找到"), REQUIRED_ARGUMENT_ERROR("请求参数为空或错误"),
    TOO_MANY_REQUESTS("请求过于频繁，请稍后再试"),;


    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
