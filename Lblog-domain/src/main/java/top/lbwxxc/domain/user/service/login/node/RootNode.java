package top.lbwxxc.domain.user.service.login.node;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.user.adapter.repository.IUserRepository;
import top.lbwxxc.domain.user.model.entity.UserAccountEntity;
import top.lbwxxc.domain.user.model.entity.LoginUserEntity;
import top.lbwxxc.domain.user.model.entity.UserDetailEntity;
import top.lbwxxc.domain.user.service.login.AbstractLoginUserSupport;
import top.lbwxxc.domain.user.service.login.factory.DefaultUserLoginStrategyFactory;
import top.lbwxxc.types.design.framework.tree.StrategyHandler;

@Service
@Slf4j
public class RootNode extends AbstractLoginUserSupport<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> {

    @Resource
    private IUserRepository userRepository;
    @Resource
    private LoginNode loginNode;
    @Resource
    private RegisterNode registerNode;


    @Override
    protected UserAccountEntity doApply(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("登录注册 - RootNode 从数据库加载用户信息, requestParameter：{}", requestParameter);

        String phone = requestParameter.getPhone();

        UserDetailEntity userByUsername = userRepository.getUserByPhone(phone);
        log.info("用户信息为 {}", userByUsername);

        if (userByUsername != null) {
            dynamicContext.setUserId(userByUsername.getId());
            dynamicContext.setPassword(userByUsername.getPassword());
        }

        dynamicContext.setCode(requestParameter.getCode());
        dynamicContext.setPhone(phone);

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> get(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        if (dynamicContext.getUserId() != null) {
            return loginNode;
        } else if (requestParameter.getCode() != null) {
            log.info("phone：{}， code： {}，该用户未注册", requestParameter.getPhone(), requestParameter.getCode());
            return registerNode;
        }
        return defaultStrategyHandler;
    }
}
