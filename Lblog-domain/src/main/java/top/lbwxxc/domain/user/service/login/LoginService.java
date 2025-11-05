package top.lbwxxc.domain.user.service.login;


import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.user.adapter.port.ISendCodePort;
import top.lbwxxc.domain.user.model.entity.UserAccountEntity;
import top.lbwxxc.domain.user.model.entity.LoginUserEntity;
import top.lbwxxc.domain.user.model.entity.VerificationCodeEntity;
import top.lbwxxc.domain.user.service.ILoginService;
import top.lbwxxc.domain.user.service.login.factory.DefaultUserLoginStrategyFactory;
import top.lbwxxc.types.common.Constants;
import top.lbwxxc.types.design.framework.tree.StrategyHandler;

import java.util.concurrent.TimeUnit;

@Service
public class LoginService implements ILoginService {

    @Resource
    private ISendCodePort sendCodePort;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    DefaultUserLoginStrategyFactory defaultUserLoginStrategyFactory;

    @Override
    public UserAccountEntity LoginOrRegister(LoginUserEntity loginEntity) throws Exception {
        StrategyHandler<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> handler = defaultUserLoginStrategyFactory.strategyHandler();
        LoginUserEntity loginUserEntity = LoginUserEntity.builder()
                .type(loginEntity.getType())
                .reqPassword(loginEntity.getReqPassword())
                .phone(loginEntity.getPhone())
                .code(loginEntity.getCode())
                .build();
        return handler.apply(loginUserEntity, new DefaultUserLoginStrategyFactory.DynamicContext());
    }

    @Override
    public void sendVerificationCode(VerificationCodeEntity verificationCodeEntity) {
        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(Constants.buildVerificationCodeKey(verificationCodeEntity.getPhone()), code, 30, TimeUnit.SECONDS);
        sendCodePort.sendVerificationPhoneCode(verificationCodeEntity.getPhone(), code);
    }


}
