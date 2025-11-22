package top.lbwxxc.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
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


            SaRouter.match(SaHttpMethod.OPTIONS)
                    .free(r -> System.out.println("--------OPTIONS预检请求，不做处理"))
                    .back();

            SaRouter
                    .match("/**")
                    .notMatch("/article/**")
                    .notMatch("/category/**")
                    .notMatch("/tag/**")
                    .notMatch("/login/login")
                    .notMatch("/login/check")
                    .notMatch("/code/**")
                    .notMatch("/api/v1/weixin/portal/**")
                    .check(r -> StpUtil.checkLogin());


        }))
        .addPathPatterns("/**")
        .excludePathPatterns("/error");
    }
}
