package com.myz.springboot2.service.impl;

import com.myz.springboot2.manager.JwtManager;
import com.myz.springboot2.pojo.User;
import com.myz.springboot2.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author maoyz
 */
@Service
public class AuthServiceImpl implements IAuthService {

    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier(value = "jwtUserServiceImpl")
    private UserDetailsService userDetailsService;

    private String secret;

    @Autowired(required = false)
    public AuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User register(User userToAdd) {
        if (userToAdd == null) {
            return null;
        }
        // todo 注册user check
        return null;
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        //
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String token = JwtManager.generateToken(userDetails.getUsername());

        return token;
    }

    @Override
    public String refresh(String oldToken) {
        return null;
    }

    private Date expirate(long hours) {
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(hours);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
