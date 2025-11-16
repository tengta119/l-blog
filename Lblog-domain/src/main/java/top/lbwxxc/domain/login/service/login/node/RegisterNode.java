package top.lbwxxc.domain.login.service.login.node;


import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.login.adapter.repository.IUserAccountRepository;
import top.lbwxxc.domain.login.model.entity.UserAccountEntity;
import top.lbwxxc.domain.login.model.entity.LoginUserEntity;
import top.lbwxxc.domain.login.model.entity.LoginUserDetailEntity;
import top.lbwxxc.types.enums.CreateUserType;
import top.lbwxxc.types.enums.VerificationTypeVO;
import top.lbwxxc.domain.login.service.login.AbstractLoginUserSupport;
import top.lbwxxc.domain.login.service.login.factory.DefaultUserLoginStrategyFactory;
import top.lbwxxc.types.common.Constants;
import top.lbwxxc.types.design.framework.tree.StrategyHandler;

@Service
@Slf4j
public class RegisterNode extends AbstractLoginUserSupport<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IUserAccountRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private EndNode endNode;

    @Override
    protected UserAccountEntity doApply(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("登录注册 RegisterNode");

        String code = null;
        if (requestParameter.getType().equals(VerificationTypeVO.PHONE.getCode())) {
            code = stringRedisTemplate.opsForValue().get(Constants.buildVerificationCodeKey(requestParameter.getPhone()));
        } else if (requestParameter.getType().equals(VerificationTypeVO.EMAIL.getCode())) {
            code = stringRedisTemplate.opsForValue().get(Constants.buildVerificationCodeKey(requestParameter.getEmail()));
        }

        if (code == null || !code.equals(requestParameter.getReqCode())) {
            throw new RuntimeException("验证码异常");
        }

        String reqPassword = passwordEncoder.encode(requestParameter.getReqPassword());
        LoginUserDetailEntity userLogin = null;
        // mysql 会有触发器，自动创建用户的相关信息
        if (requestParameter.getType().equals(VerificationTypeVO.PHONE.getCode())) {

            userLogin = userRepository.createUser(requestParameter.getPhone(), reqPassword, CreateUserType.CREATE_USER_PHONE);

        } else if (requestParameter.getType().equals(VerificationTypeVO.EMAIL.getCode())) {

            userLogin = userRepository.createUser(requestParameter.getEmail(), reqPassword, CreateUserType.CREATE_USER_EMAIL);
        }

        if (userLogin == null) {
            throw new RuntimeException("创建账号失败");
        }

        dynamicContext.setUserId(userLogin.getId());

        dynamicContext.setLogin(true);
        // satoken 会将 token 注入到 response 中
        StpUtil.login(dynamicContext.getUserId());

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> get(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }

}
