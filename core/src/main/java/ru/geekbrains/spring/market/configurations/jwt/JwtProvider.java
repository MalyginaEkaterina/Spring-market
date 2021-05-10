package ru.geekbrains.spring.market.configurations.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.market.repositories.TokenRedisRepository;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Log
public class JwtProvider {

    @Autowired
    private TokenRedisRepository tokenRedisRepository;

    private static final String JWT_SECRET = "kjiugf";

    private static final String USERID_CLAIM = "id";
    private static final String LOGIN_CLAIM = "login";
    private static final String ROLE_CLAIM = "roles";

    public String generateToken(Integer userId, String login, List<String> roles) {
        Instant expirationTime = Instant.now().plus(5, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        String compactTokenString = Jwts.builder()
                //.setSubject(login)
                .claim(USERID_CLAIM, userId)
                .claim(LOGIN_CLAIM, login)
                .claim(ROLE_CLAIM, String.join(",", roles))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        return "Bearer " + compactTokenString;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            if (tokenRedisRepository.getToken(token) != null) {
                log.severe("token was logged out");
                return false;
            }
            return true;
        } catch (Exception e) {
            log.severe("invalid token");
        }
        return false;
    }

    public Integer getUserIdFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().get(USERID_CLAIM, Integer.class);
    }

    public String getLoginFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().get(LOGIN_CLAIM, String.class);
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        return Arrays.asList(claims.get(ROLE_CLAIM, String.class).split(","));
    }

    public void setTokenLogout(String token) {
        Date expDate = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getExpiration();
        Duration duration = Duration.between(Instant.now(), expDate.toInstant());
        tokenRedisRepository.putToken(token, duration);
    }
}
