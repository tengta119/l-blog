package top.lbwxxc.filter;


import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.lbwxxc.domain.holder.LoginUserContextHolder;
import top.lbwxxc.types.common.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class HeaderUserId2ContextFilter extends OncePerRequestFilter {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Header 头中 Token 的 Key
     */
    private static final String TOKEN_HEADER_KEY = "Authorization";

    /**
     * Token 前缀
     */
    private static final String TOKEN_HEADER_VALUE_PREFIX = "Bearer ";

    ArrayList<String> uriNotMatch = new ArrayList<>(List.of(new String[]{"/login/login", "/login/check", "/code/send", "/code/wxTicket", "/api/v1/weixin/portal/receive/"}));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        log.info("请求源：{}", request.getRequestURL().toString());
        if (uriNotMatch.contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        log.info("==================> TokenConvertFilter");
        // 从请求头中获取 Token 数据
        String tokenValue = request.getHeader(TOKEN_HEADER_KEY);

        if (tokenValue == null) {
            chain.doFilter(request, response);
            return;
        }

        // 将 Token 前缀去除
        String token = tokenValue.replace(TOKEN_HEADER_VALUE_PREFIX, "");

        // 构建 Redis Key
        String tokenRedisKey = Constants.SA_TOKEN_TOKEN_KEY_PREFIX + token;
        // 查询 Redis, 获取用户 ID
        Integer userId = Integer.valueOf(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(tokenRedisKey)));

        log.info("===== 设置 userId 到 ThreadLocal 中， 用户 ID: {}", userId);
        LoginUserContextHolder.setUserId(userId);

        try {
            chain.doFilter(request, response);
        } finally {
            // 一定要删除 ThreadLocal ，防止内存泄露
            LoginUserContextHolder.remove();
            log.info("===== 删除 ThreadLocal， userId: {}", userId);
        }
    }



}
