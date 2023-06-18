package com.ezreal.autobi.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT 工具类
 */
@Component
public class JwtUtils {

    public static final String defaultBase64EncodedSecretKey = "B*B^";

    public static final SignatureAlgorithm defaultSignatureAlgorithm = SignatureAlgorithm.HS256;

    public JwtUtils() {
        this(defaultBase64EncodedSecretKey, defaultSignatureAlgorithm);
    }

    private String base64EncodedSecretKey;

    private SignatureAlgorithm signatureAlgorithm;


    public JwtUtils(String encodedSecretKey, SignatureAlgorithm signatureAlgorithm) {
        this.base64EncodedSecretKey = Base64.encodeBase64String(encodedSecretKey.getBytes());
        this.signatureAlgorithm = signatureAlgorithm;
    }

    /**
     * 获取 JWT
     *
     * @param issUser 用户
     * @param ttlTime 过期时间
     * @param claims  其他信息
     * @return 结果
     */
    public String encode(String issUser, long ttlTime, Map<String, Object> claims) {

        if (claims == null) {
            claims = new HashMap<>();
        }

        long now = System.currentTimeMillis();
        JwtBuilder jwtBuilder = Jwts.builder()
                // 设置其他附带的信息
                .setClaims(claims)
                // 设置签发的时间
                .setIssuedAt(new Date(now))
                // 设置签发的用户
                .setIssuer(issUser)
                // 设置唯一标识
                .setId(UUID.randomUUID().toString())
                // 设置算法签名信息
                .signWith(signatureAlgorithm, base64EncodedSecretKey);

        // 设置过期时间
        if (ttlTime > 0) {
            long exp = ttlTime + now;
            jwtBuilder.setExpiration(new Date(exp));
        }

        return jwtBuilder.compact();
    }

    /**
     * 解析 jwt 获取载荷信息
     *
     * @param jwt 字符串
     * @return 载荷信息
     */
    public Claims decode(String jwt) {

        return Jwts.parser()
                .setSigningKey(base64EncodedSecretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 验证 jwt
     *
     * @param jwt 字符串
     * @return 结果
     */
    public Boolean verify(String jwt) {
        Algorithm algorithm = null;

        switch (signatureAlgorithm) {
            case HS256:
                algorithm = Algorithm.HMAC256(Base64.decodeBase64(base64EncodedSecretKey));
                break;
            default:
                throw new RuntimeException("not support other algorithm");
        }
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        jwtVerifier.verify(jwt);
        // 校验不通过会抛出异常
        // 判断合法的标准：1. 头部和荷载部分没有篡改过。2. 没有过期
        return true;
    }

}
