package com.waitless.user.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.waitless.user.application.dto.SignupDto;
import com.waitless.user.application.dto.SignupResponseDto;
import com.waitless.user.application.dto.ValidateUserDto;
import com.waitless.user.application.dto.ValidateUserResponseDto;
import com.waitless.user.domain.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserServiceMapper {

	SignupResponseDto toSignupResponseDto(User user);

	@Mapping(target = "password", source = "encodedPassword")
	User toUser(SignupDto signupDto, String encodedPassword);

	@Mapping(target = "userId", source = "id")
	@Mapping(target = "role", source = "role")
	ValidateUserResponseDto toValidateUserResponseDto(User user);
}
