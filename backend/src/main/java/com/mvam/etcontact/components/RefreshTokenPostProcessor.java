package com.mvam.etcontact.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Vai intercepta e tratar a resposta das requisições que devolvem um objeto OAuth2AccessToken
 * Poderia tratar requisições de outros objetos da mesma forma
 */
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

    @Value("${security.oauth2.client.enable-https}")
    private boolean isEnableHttps;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //filtra para interceptar somente quando a requisição ocorrer pelo método específico
        return methodParameter.getMethod().getName().equals("postAccessToken");
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter methodParameter,
                                             MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                             ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        String refreshToken = body.getRefreshToken().getValue();
        HttpServletRequest request = ((ServletServerHttpRequest)serverHttpRequest).getServletRequest();
        HttpServletResponse response = ((ServletServerHttpResponse)serverHttpResponse).getServletResponse();

        adicionarRefreshTokenNoCookie(refreshToken, request, response);

        removerRefreshTokenDoBody(body);

        return body;

    }

    private void removerRefreshTokenDoBody(OAuth2AccessToken body) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
        token.setRefreshToken(null);
    }

    private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(isEnableHttps); //se true, só irá aceitar HTTPS
        cookie.setPath(request.getContextPath() + "/oauth/token");
        cookie.setMaxAge(2592000);
        response.addCookie(cookie);

    }
}
