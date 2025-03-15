package com.davcatch.devcatch.exception;

public enum ErrorCode {

	EXISTS_EMAIL,

	SERVER_ERROR,

	SOURCE_NOT_FOUND,
	TAG_NOT_FOUND,

	RSS_PARSE_ERROR,
	CONTENT_PARSE_ERROR,

	GPT_REQUEST_ERROR,
	GPT_REQUEST_BODY_NULL,

	VERIFY_CODE_EXPIRED,
	VERIFY_CODE_WRONG,

	MAIL_SEND_FAIL,

	NO_SUPPORTS_STRATEGY,
	MEMBER_NOT_FOUND, BAD_REQUEST;
}
