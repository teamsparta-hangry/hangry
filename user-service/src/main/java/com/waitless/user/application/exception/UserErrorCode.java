package com.waitless.user.application.exception;

import org.springframework.http.HttpStatus;

import com.waitless.common.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public enum UserErrorCode implements ErrorCode {

	USER_NOT_FOUND("USER_1", "해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	USER_ALREADY_EXISTS("USER_2", "이미 가입된 계정입니다.", HttpStatus.CONFLICT),
	USER_INVALID_PASSWORD("USER_3", "비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
	USER_ALREADY_DELETED("USER_4", "이미 삭제된 사용자입니다.", HttpStatus.CONFLICT);

	private final String code;
	private final String message;
	private final int status;

	UserErrorCode(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status.value();
	}
}
