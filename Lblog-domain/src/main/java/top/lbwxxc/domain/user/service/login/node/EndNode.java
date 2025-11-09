package top.lbwxxc.domain.user.service.login.node;


import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.user.model.entity.UserAccountEntity;
import top.lbwxxc.domain.user.model.entity.LoginUserEntity;
import top.lbwxxc.domain.user.service.login.AbstractLoginUserSupport;
import top.lbwxxc.domain.user.service.login.factory.DefaultUserLoginStrategyFactory;
import top.lbwxxc.types.design.framework.tree.StrategyHandler;

@Service
@Slf4j
public class EndNode extends AbstractLoginUserSupport<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> {

    @Override
    protected UserAccountEntity doApply(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("登录注册 EndNode");
        if (dynamicContext.isLogin()) {
            return UserAccountEntity.builder()
                    .id(dynamicContext.getUserId())
                    .phone(requestParameter.getPhone())
                    .email(requestParameter.getEmail())
                    .token(StpUtil.getTokenInfo().tokenValue)
                    .build();
        }
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> get(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }
}
