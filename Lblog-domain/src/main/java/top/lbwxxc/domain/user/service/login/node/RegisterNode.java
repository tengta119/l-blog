package top.lbwxxc.domain.user.service.login.node;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.user.adapter.repository.IUserRepository;
import top.lbwxxc.domain.user.model.entity.UserAccountEntity;
import top.lbwxxc.domain.user.model.entity.LoginUserEntity;
import top.lbwxxc.domain.user.model.entity.UserRegisterEntity;
import top.lbwxxc.domain.user.model.entity.UserDetailEntity;
import top.lbwxxc.domain.user.service.login.AbstractLoginUserSupport;
import top.lbwxxc.domain.user.service.login.factory.DefaultUserLoginStrategyFactory;
import top.lbwxxc.types.common.Constants;
import top.lbwxxc.types.design.framework.tree.StrategyHandler;

@Service
@Slf4j
public class RegisterNode extends AbstractLoginUserSupport<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IUserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private LoginNode loginNode;

    @Override
    protected UserAccountEntity doApply(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("登录注册 RegisterNode");

        String code = stringRedisTemplate.opsForValue().get(Constants.buildVerificationCodeKey(requestParameter.getPhone()));
        if (code != null && code.equals(requestParameter.getCode())) {

            UserRegisterEntity userRegisterEntity = UserRegisterEntity.builder()
                    .phone(requestParameter.getPhone())
                    .password(requestParameter.getReqPassword() == null ? "" : passwordEncoder.encode(requestParameter.getReqPassword()))
                    .build();
            UserDetailEntity userDetail = userRepository.createUser(userRegisterEntity);

            dynamicContext.setPhone(userDetail.getPhone());
            dynamicContext.setPassword(userDetail.getPassword());
            dynamicContext.setUserId(userDetail.getId());
        }

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> get(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return loginNode;
    }

}
