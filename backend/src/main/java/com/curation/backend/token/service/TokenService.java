package com.curation.backend.token.service;

import com.curation.backend.token.domain.Token;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {

    Logger logger = LoggerFactory.getLogger(TokenService.class);

    private String secretKey = "token-secret-key";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    // 인증 token 10분, 리프레쉬 토큰 = 3주;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60L * 10L;            // 10분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60L * 60L * 24L * 7L * 3L;  // 3주

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Token generateToken(Long id, String uid, String profileImg, String name, String role) {


        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("pk", id);
        claims.put("img", profileImg);
        claims.put("name",name);
        claims.put("role", role);

        Claims claims_re = Jwts.claims().setSubject(uid);

        Date now = new Date();
        return new Token(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims_re)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact());
    }

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            logger.trace("jwt 유효성 검증 return값 확인 : {}",claims.getBody().getExpiration().after(new Date()));
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (ExpiredJwtException e) {
            logger.debug("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.debug("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.debug("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Long getId(String token){
        Long pk = Long.parseLong(String.valueOf(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("pk")));
        return pk;
    }

    public String getUid(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getProfileImg(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("img");
    }

    public String getNickName(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("name");
    }
}