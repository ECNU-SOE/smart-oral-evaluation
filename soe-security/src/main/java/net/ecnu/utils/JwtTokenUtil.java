package net.ecnu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.model.JwtProperties;
import net.ecnu.model.common.LoginUser;
import net.ecnu.util.CommonUtil;
import net.ecnu.util.JsonUtil;

import java.util.Date;

@Slf4j
public class JwtTokenUtil {

    private JwtProperties jwtProperties;

    public JwtTokenUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 生成JWT
     * 入参为user表对应的部分信息
     */
    public String geneJsonWebToken(LoginUser loginUser) {
        if (loginUser == null) {
            throw new NullPointerException("loginUser can't be null when geneJsonWebToken");
        }
        String token = Jwts.builder().setSubject(jwtProperties.getSubject())
                .claim("loginUser", loginUser)
                .setIssuedAt(new Date())
                .setExpiration(new Date(CommonUtil.getCurrentTimestamp() + jwtProperties.getExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret()).compact();
        token = jwtProperties.getTokenPrefix() + token;
        return token;
    }

    /**
     * 解密JWT
     */
    public LoginUser checkJWT(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token.replace(jwtProperties.getTokenPrefix(), "")).getBody();
            String loginUserJson = JsonUtil.obj2Json(claims.get("loginUser"));
            return JsonUtil.json2Obj(loginUserJson, LoginUser.class);
        } catch (Exception e) {
            log.error("JWT解密失败");
            return null;
        }
    }


    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token.replace(jwtProperties.getTokenPrefix(), "")).getBody();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }


}
