package com.github.mhzhou95.javaSpringBootTemplate.csrf;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Example of a Csrf filter
public class CSRFTokenLoggerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        CsrfToken csrfToken = (CsrfToken) httpServletRequest.getAttribute("_csrf");
        System.out.println(csrfToken.getToken());

        filterChain.doFilter(httpServletRequest,httpServletResponse );

    }
}
