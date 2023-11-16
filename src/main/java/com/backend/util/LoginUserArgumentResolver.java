package com.backend.util;

import com.backend.domain.auth.dto.Login;
import com.backend.domain.auth.dto.LoginUser;
import com.backend.error.ErrorCode;
import com.backend.error.exception.custom.BusinessException;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasLoginUserType = LoginUser.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasLoginUserType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        return LoginUser.builder()
                .email(authentication.getName())
                .build();
    }
}