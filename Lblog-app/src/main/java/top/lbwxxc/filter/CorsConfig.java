package top.lbwxxc.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对所有接口生效
                .allowedOrigins(
                        "http://localhost:5173",  // 前端本地域名
                        "http://lbwxxc.natapp1.cc"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方法（必须包含OPTIONS，预检请求用）
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true) // 允许携带cookie（如果前端需要）
                .maxAge(3600); // 预检请求的有效期（秒）
    }
}
