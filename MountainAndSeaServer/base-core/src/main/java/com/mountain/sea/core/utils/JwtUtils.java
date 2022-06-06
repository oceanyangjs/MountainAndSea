package com.mountain.sea.core.utils;

import io.jsonwebtoken.*;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-06 15：07
 */
public class JwtUtils {
    private static final String CLAIM_KEY = "claim";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM;

    public JwtUtils() {
    }

    public static Map<String, Object> getUserInfoFromToken(String token, PublicKey publicKey) {
        Claims claims = getClaimsFromToken(token, publicKey);
        return (Map)claims.get("claim");
    }

    public static Boolean isTokenExpired(String token, PublicKey publicKey) throws JwtException, IllegalArgumentException {
        Date expiration = getExpirationDateFromToken(token, publicKey);
        return expiration.before(new Date());
    }

    public static Date getExpirationDateFromToken(String token, PublicKey publicKey) throws JwtException, IllegalArgumentException {
        Claims claims = getClaimsFromToken(token, publicKey);
        Date expiration = claims.getExpiration();
        return expiration;
    }

    private static Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000L);
    }

    public static String generateToken(String subject, Map<String, Object> claims, PrivateKey privateKey, long expiration) {
        String token = Jwts.builder().claim("claim", claims).setSubject(subject).setId(UUID.randomUUID().toString()).setIssuedAt(new Date()).setExpiration(generateExpirationDate(expiration)).compressWith(CompressionCodecs.DEFLATE).signWith(SIGNATURE_ALGORITHM, privateKey).compact();
        return token;
    }

    private static Claims getClaimsFromToken(String token, PublicKey publicKey) throws JwtException, IllegalArgumentException {
        Claims claims;
        try {
            claims = (Claims)Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException var4) {
            claims = var4.getClaims();
        }

        return claims;
    }

    static {
        SIGNATURE_ALGORITHM = SignatureAlgorithm.RS256;
    }
}
