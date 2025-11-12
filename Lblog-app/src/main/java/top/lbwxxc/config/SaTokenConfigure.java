package top.lbwxxc.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class SaTokenConfigure implements WebMvcConfigurer {

    // 注册 Sa-Token 的拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            log.info("Sa-Token 拦截器： {}", handler);

            SaRouter
                    .match("/**")
                    .notMatch("/code/**")
                    .notMatch("/api/v1/weixin/portal/**")
                    .notMatch("/login/**")
                    .check(r -> StpUtil.checkLogin());


        })).addPathPatterns("/**");
    }
}
