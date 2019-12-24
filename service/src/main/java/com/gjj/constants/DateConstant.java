package com.gjj.constants;

public final class DateConstant {

	public final static String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

	public final static String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public final static String DATE_FORMAT_CN_PATTERN = "yyyy年MM月dd日";

	public final static String DATE_TIME_FORMAT_NO_PATTERN = "yyyyMMddHHmmss";

	public final static String[] DATE_PARSE_PATTERNS = { DATE_FORMAT_PATTERN, DATE_TIME_FORMAT_PATTERN,
			DATE_FORMAT_CN_PATTERN, "yyyy-M-dd", "yyyy-M-d" };

}
