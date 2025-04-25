package com.school.management.config;


import com.google.gson.Gson;
import com.school.management.constants.MessageConstants;
import com.school.management.constants.UrlConstants;
import com.school.management.dto.ResponseData;
import com.school.management.service.serviceImpl.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailService userDetailService;

    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> authHeader = Optional.ofNullable(request.getHeader("Authorization"));

        if (!isAuthorized(request.getRequestURI())) {
            if (authHeader.isEmpty()) {
                unauthorizedMessage(response, MessageConstants.AUTHORIZATION_HEADER_MISSING);
                return;
            }
            if (!authHeader.get().startsWith(MessageConstants.TOKEN_PREFIX)) {
                unauthorizedMessage(response, MessageConstants.INVALID_TOKEN_PREFIX);
                return;
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = authHeader.get().substring(7);
            String username = jwtService.extractUsername(jwt);
            String role = jwtService.extractRole(jwt);
            if(role!=null) {
                    role = role.replace("[", "").replace("]", "");
            }
            if (role != null && role.startsWith("ROLE_")) {
                role = role.substring(5);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailService.loadUserByUsernameAndRole(username, role);
                if (userDetails.isEnabled()) {
                    if (jwtService.isTokenValid(jwt)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                username,
                                userDetails.getPassword(),
                                userDetails.getAuthorities()
                        );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } else {
                    unauthorizedMessage(response, MessageConstants.USER_DEACTIVATED_EXPIRED);
                    return;
                }
            } else {
                unauthorizedMessage(response, MessageConstants.INVALID_TOKEN_EXPIRED_TOKEN);
                return;
            }
        } catch (UsernameNotFoundException e) {
            unauthorizedMessage(response, e.getMessage());
            return;
        } catch (Exception e) {
            unauthorizedMessage(response, MessageConstants.INVALID_TOKEN);
            return;
        }

        filterChain.doFilter(request, response);
    }



    public boolean urlMatcher(String url, String authorizedUrl) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher.match(authorizedUrl, url);
    }

    public boolean isAuthorized(String requestUrl) {
        return Arrays.stream(UrlConstants.PERMIT_URLS)
                .anyMatch(authorizedUrl -> urlMatcher(requestUrl, authorizedUrl));
    }

    public void unauthorizedMessage(HttpServletResponse response, String subMessage) throws IOException {
        ResponseData responseData = new ResponseData(
                "Unauthorized",
                subMessage,
                null
        );
        responseData.setData(null);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(new Gson().toJson(responseData));
    }

}
