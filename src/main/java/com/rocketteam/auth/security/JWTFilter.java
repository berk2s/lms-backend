package com.rocketteam.auth.security;

import com.nimbusds.jwt.JWTClaimsSet;
import com.rocketteam.auth.services.JwtService;
import com.rocketteam.auth.web.controllers.*;
import com.rocketteam.auth.web.exceptions.JWTException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

    /** JwtService injection */
    private final JwtService jwtService;

    /** UserDetailsService injection that injected as SecurityUserDetailsService */
    private final SecurityUserDetailsService securityUserDetailsService;

    /**
     * URLS that will be configured respectively to cors
     */
    private final List<String> corsUrls = List.of(LoginController.ENDPOINT,
            RegisterController.ENDPOINT,

            TokenController.ENDPOINT,
            JWKSetController.ENDPOINT);

    /** URLs that ignored by filter */
    private final List<String> skipUrls = List.of(
            "/swagger-ui.html",
            "/swagger-ui",
            "/api-docs");

    /**
     * Filters request by token
     * @param request Contains information about coming request
     * @param response Does action for response
     * @param filterChain It is a chain that contains filters
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String headers = request.getHeader(HttpHeaders.AUTHORIZATION);

            if ("OPTIONS".equalsIgnoreCase(request.getMethod())
                    || corsUrls.stream().anyMatch(skipUrl -> request.getRequestURI().startsWith(skipUrl))) {
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");
                filterChain.doFilter(request, response);
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }

            if(StringUtils.isEmpty(headers) || !headers.startsWith("Bearer ")) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            final String accessToken = headers.split(" ")[1].trim();

            if (!jwtService.validate(accessToken)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            JWTClaimsSet jwtClaimsSet = jwtService.parse(accessToken);

            Date date = jwtClaimsSet.getExpirationTime();
            Instant now = Instant.now();

            if (date.toInstant().isBefore(now)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            String username = jwtClaimsSet.getStringClaim("username");

            SecurityUser securityUser =
                    securityUserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (UsernameNotFoundException | JWTException | ParseException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }

    /**
     * Determines which endpoints should not be in the filter
     * @param request contains information about request
     * @return Status of if filter will be applied
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return skipUrls.stream().anyMatch(skipUrl -> request.getRequestURI().startsWith(skipUrl));
    }
}
