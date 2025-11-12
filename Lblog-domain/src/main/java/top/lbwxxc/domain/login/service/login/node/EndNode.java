package top.lbwxxc.domain.login.service.login.node;


import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.login.adapter.repository.IUserAccountRepository;
import top.lbwxxc.domain.login.model.entity.UserAccountEntity;
import top.lbwxxc.domain.login.model.entity.LoginUserEntity;
import top.lbwxxc.domain.login.model.entity.UserRoleEntity;
import top.lbwxxc.domain.login.service.login.AbstractLoginUserSupport;
import top.lbwxxc.domain.login.service.login.factory.DefaultUserLoginStrategyFactory;
import top.lbwxxc.types.common.Constants;
import top.lbwxxc.types.design.framework.tree.StrategyHandler;
import top.lbwxxc.types.util.JsonUtils;

@Service
@Slf4j
public class EndNode extends AbstractLoginUserSupport<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IUserAccountRepository userAccountRepository;

    @Override
    protected UserAccountEntity doApply(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("登录注册 EndNode");
        if (dynamicContext.isLogin()) {
            Long userId = dynamicContext.getUserId();
            UserRoleEntity userRoleById = userAccountRepository.getUserRoleById(userId);
            if (userRoleById == null) {
                throw new RuntimeException("用户权限不存在");
            }
            stringRedisTemplate.opsForValue().set(Constants.buildUserRoleKey(userId), JsonUtils.toJsonString(userRoleById.getRoleIds()));

            return UserAccountEntity.builder()
                    .id(userId)
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
