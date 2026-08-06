package com.shyc.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtUtils —— JWT 工具类
 * 负责两件事:生成令牌(generateToken)、解析令牌(parseToken)。
 *
 * 它的作用 = 电影院的"售票 + 检票":
 *   登录成功 → generateToken 发一张"电影票"(JWT)给前端
 *   以后请求 → parseToken 检票,验真后才放行
 *
 * @author shiyc
 * @date 2026/8/6 23:10
 */
public class JwtUtils {

    // SECRET = Secret(秘密) | 只有服务器知道的"签名密钥/章"
    // 生成和解析都要用它,别人没它伪造不了你的 JWT
    // 注意:HS256 算法要求密钥至少 32 字节(长度要够)
    private static final String SECRET = "shyc-javaweb-tlias-jwt-secret-key-2026";

    // SECRET_KEY = Secret(秘密) + Key(钥匙) | 一把"秘密钥匙"对象(签名专用)
    // Keys.hmacShaKeyFor = Keys(钥匙)+hmacSha(HMAC算法)+keyFor(为...生成)
    //   → 把上面的字符串密钥,包装成一个规范要用的"密钥对象"
    //   → 生成和解析签章时传它,更方便(Key 里已含算法)
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // EXPIRATION_TIME = Expiration(过期) + Time(时间) | 令牌有效时间
    // 1000*60*60*24 = 1 天(毫秒)。过了就作废,前端得重新登录
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    /**
     * 生成 JWT(发电影票)
     *
     * @param id       用户的 id(写进票里的乘客信息)
     * @param username 用户名(写进票里的另一条乘客信息)
     * @return 一个 JWT 字符串(那张"票")
     *
     * Jwts = JSON Web Token 的缩写(工具类)
     * Jwts.builder() | builder=构建者 | 一步步"搭"出一个 token
     * setClaims    | set=设置 + Claims=声明/乘客信息 | 把你信息装进票里
     * setExpiration| Expiration=过期 | 给票定一个"有效期到啥时候"
     * signWith     | sign=签名 + with=用 | 用密钥盖防伪章(伪造不了)
     * compact      | =压缩/合并 | 把搭好的东西合成一长串字符串
     */
    public static String generateToken(Integer id, String username) {
        // claims = Claims(乘客/声明) | 要写进票里的信息,用 Map(键值对)装
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);            // 把 id 放进乘客信息
        claims.put("username", username); // 把用户名也放进去

        return Jwts.builder()                       // 开始搭"票"
                .setClaims(claims)                  // 装入乘客消息(id、username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间=现在+1天
                .signWith(SECRET_KEY) // 用密钥盖防伪章
                .compact();                         // 合成最终那串"票"(JWT 字符串)
    }

    /**
     * 解析 JWT(检票)
     *
     * @param token 前端带回来的 JWT 字符串(那张票)
     * @return Claims(乘客消息盒子),能用 .get("id")/.get("username") 取出信息
     *
     * parserBuilder = parser(解析器)+builder(构建者) | 解析器的"构建工厂"
     * setSigningKey = Signing(签名)+Key(钥匙) | 用同一个密钥验签(拆防伪章)
     * build         | 建造/装配 | 装配好解析器
     * parseClaimsJws| parse(解析)+Claims+JWS(JWT签名串) | 真正解析这张票
     * getBody       | Body=主体 | 拿出票上的"乘客信息"(claims)
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()       // 创建解析器构建者
                .setSigningKey(SECRET_KEY) // 用同一个密钥验签(和生成一致,否则解析失败)
                .build()                   // 装配成可用的解析器
                .parseClaimsJws(token)     // 解析这张 JWT
                .getBody();                // 拿出乘客信息能取出 id/username
    }

}
