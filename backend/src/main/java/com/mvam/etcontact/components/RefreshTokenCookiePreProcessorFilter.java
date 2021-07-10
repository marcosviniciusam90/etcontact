package com.mvam.etcontact.components;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

/**
 * Todas as requisições vão passar por este filtro.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Not necessary
    }

    @Override
    public void destroy() {
        //Not necessary
    }

    /**
     * Iremos tratar aquelas que forem requisições para obter o token e possuirem cookie.
     * Isto é:
     * - deve possuir URI "/oauth/token";
     * - deve possuir parâmetro "grant_type" com valor "refresh_token";
     * - deve possuir um Cookie com nome "refreshToken".
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if("/oauth/token".equalsIgnoreCase(request.getRequestURI())
            && "refresh_token".equals(request.getParameter("grant_type"))
            && request.getCookies() != null) {

            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("refreshToken")) {
                    String refreshToken = cookie.getValue();
                    request = new RefreshTokenServletResquestWrapper(request, refreshToken);
                }
            }

        }

        filterChain.doFilter(request, servletResponse);

    }

    /**
     * Foi necessário criar uma nova requisição acrescentando o parâmetro "refresh_token"
     * com valor obtido através do Cookie
     * Por que uma nova requisição?
     * Pois quando a requisição foi interceptada ela já estava escrita, não sendo possível
     * modificar os parâmetros, por isso criamos uma nova requisição com os mesmos parâmetros
     * acrescentando o parâmetro "refresh_token"
     */
    static class RefreshTokenServletResquestWrapper extends HttpServletRequestWrapper {

        private final String refreshToken;

        public RefreshTokenServletResquestWrapper(HttpServletRequest request, String refreshToken) {
            super(request);
            this.refreshToken = refreshToken;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
            map.put("refresh_token", new String[] { refreshToken });
            map.setLocked(true);
            return map;
        }
    }
}
