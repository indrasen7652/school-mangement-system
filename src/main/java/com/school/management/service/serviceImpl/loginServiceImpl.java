package com.school.management.service.serviceImpl;

import com.school.management.config.CustomAuthenticationProvider;
import com.school.management.config.JwtService;
import com.school.management.constants.MessageConstants;
import com.school.management.dto.LoginDTO;
import com.school.management.exception.ValidationException;
import com.school.management.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class loginServiceImpl implements LoginService {

    @Autowired
    JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailService userDetailService;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    public Map<String, String> loginAdmin(LoginDTO loginDTO) {
        Map<String, String> tokenMap = new HashMap<>();
        try {
            Authentication authentication = customAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(), loginDTO.getPassword(),
                            AuthorityUtils.createAuthorityList(loginDTO.getRole())
                    )
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = userDetailService.loadUserByUsernameAndRole(
                        loginDTO.getUsername(), loginDTO.getRole()
                );
                tokenMap = jwtService.generateToken(userDetails);
                if (tokenMap == null || tokenMap.isEmpty()) {
                    throw new ValidationException(MessageConstants.TOKEN_GENERATE_FAILED);
                }
            } else {
                throw new ValidationException(MessageConstants.USER_AUTHENTICATION_FAILED);
            }
        } catch (BadCredentialsException e) {
            throw new ValidationException(MessageConstants.USER_PASSWORD_INCORRECT);
        }
        return tokenMap;
    }


}
