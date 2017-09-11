package com.dfoucs.gateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static long expiredTime = 60 * 60 * 24 * 30; //一个月

    public static String createToken() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        long iat=System.currentTimeMillis()/1000l;
        long exp=iat+expiredTime;
        String token = JWT.create()
                .withHeader(map)//header
                .withClaim("name", "wang")//payload
                .withClaim("age", "18")
                .withClaim("iat",String.valueOf(iat))
                .withClaim("exp",String.valueOf(exp))
                .sign(Algorithm.HMAC256("secret"));//加密

        return token;
    }
    public static void verifyToken(String token,String key) throws Exception{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key))
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        System.out.println(claims.get("name").asString());
    }
}
