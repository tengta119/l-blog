package top.lbwxxc.domain.user.service.login.node;


import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.user.model.entity.UserAccountEntity;
import top.lbwxxc.domain.user.model.entity.LoginUserEntity;
import top.lbwxxc.domain.user.service.login.AbstractLoginUserSupport;
import top.lbwxxc.domain.user.service.login.factory.DefaultUserLoginStrategyFactory;
import top.lbwxxc.types.common.Constants;
import top.lbwxxc.types.design.framework.tree.StrategyHandler;

@Service
@Slf4j
public class LoginNode extends AbstractLoginUserSupport<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> {

    @Resource
    private EndNode endNode;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected UserAccountEntity doApply(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("登录注册服务 - LoginNode");
        String reqPassword = requestParameter.getReqPassword();
        String code = stringRedisTemplate.opsForValue().get(Constants.buildVerificationCodeKey(requestParameter.getPhone()));

        if (reqPassword != null && passwordEncoder.matches(reqPassword, dynamicContext.getPassword())) {
            dynamicContext.setLogin(true);
        } else if (requestParameter.getCode() != null && requestParameter.getCode().equals(code)) {
            dynamicContext.setLogin(true);
        }

        if (dynamicContext.isLogin()) {
            // satoken 会将 token 注入到 response 中
            StpUtil.login(dynamicContext.getUserId());
        }

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> get(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }
}
