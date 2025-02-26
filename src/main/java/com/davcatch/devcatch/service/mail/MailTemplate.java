package com.davcatch.devcatch.service.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailTemplate {

	VERIFY_TITLE("[데브캐치] 이메일 인증코드", "mail/verifyCode.html")
	;

	private final String title;
	private final String templatePath;
}
