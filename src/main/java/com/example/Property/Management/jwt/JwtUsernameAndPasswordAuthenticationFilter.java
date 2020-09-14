package com.example.Property.Management.jwt;

import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.entity.RegistrationForm;
import com.example.Property.Management.repository.OwnerRepository;
import com.example.Property.Management.utility.CallAPI;
import com.example.Property.Management.utility.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;

@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final OwnerRepository ownerRepository;
    private final UserService userService;
    RequestMatcher registerMatcher = new AntPathRequestMatcher("/api/register/**");

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey, OwnerRepository ownerRepository, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.ownerRepository = ownerRepository;
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UsernameAndPasswordAuthenticationRequest authenticationRequest;
        ServletInputStream servletInputStream = request.getInputStream();
        if (registerMatcher.matches(request)) {
            // register
            try {
                log.info("brefore form");
                RegistrationForm form = new ObjectMapper()
                        .readValue(servletInputStream, RegistrationForm.class);
                authenticationRequest = new Data().registerUser(userService, ownerRepository, form);

            } catch (IOException exception1) {
                throw new IOException(exception1);
            }
        }
        else {
            try{
                authenticationRequest = new ObjectMapper()
                        .readValue(servletInputStream, UsernameAndPasswordAuthenticationRequest.class);
            } catch (IOException exception){
                throw new IOException(exception);
            }
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authentication);
        return authenticate;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();
        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);

        CallAPI apin = new CallAPI();
        String data = apin.callHome(token, authResult.getName());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(data);
        out.flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        log.info("Authresult: " + failed);

        //super.unsuccessfulAuthentication(request, response, failed);
        response.sendError(401);
        response.setContentType("application/json");

        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(failed);
        out.flush();
    }
}
























