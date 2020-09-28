package com.example.Property.Management.jwt;

import com.example.Property.Management.service.DataService;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final DataService dataService;

    public JwtTokenVerifier(SecretKey secretKey, JwtConfig jwtConfig, DataService dataService) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.dataService = dataService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {

            filterChain.doFilter(request,response);
            return;
        }

        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
        JSONObject jsonErrorPayload = new JSONObject();
        response.setContentType("application/json");

        try {
            Jws<Claims>  claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();

            String tokenDB = dataService.getUserJWTToken(username);
            if(!token.equals(tokenDB)){
                //throw new IllegalStateException(String.format("Token %s is no longer valid", token));
                //throw new JwtException(String.format("Token %s is no longer valid", token));
                jsonErrorPayload.put("Error", "Token is no longer valid");

                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write(jsonErrorPayload.toJSONString());
                return;
            }

            var authorities = (List<Map<String,String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );
            // from this point the user can be authenticated
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authenticated: \n" + authentication);

        }catch (JwtException jwtException){
            //filterChain.doFilter(request, response);
            jsonErrorPayload.put("Error", "Token cannot be trusted");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(jsonErrorPayload.toJSONString());
            return;
        }
        // very important, sends info to next filter in the filter chain....
        filterChain.doFilter(request, response);
    }
}















