package top.lbwxxc.domain.login.service.login;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.cache.Cache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.login.adapter.port.ILoginPort;
import top.lbwxxc.domain.login.adapter.repository.IUserRepository;
import top.lbwxxc.domain.login.model.entity.*;
import top.lbwxxc.types.enums.CreateUserType;
import top.lbwxxc.types.enums.SelectUserType;
import top.lbwxxc.types.enums.VerificationTypeVO;
import top.lbwxxc.domain.login.service.ILoginService;
import top.lbwxxc.domain.login.service.login.factory.DefaultUserLoginStrategyFactory;
import top.lbwxxc.types.common.Constants;
import top.lbwxxc.types.design.framework.tree.StrategyHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginService implements ILoginService {


    @Resource
    private ILoginPort loginPort;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    DefaultUserLoginStrategyFactory defaultUserLoginStrategyFactory;
    @Resource
    private IUserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private Cache<String, String> cache;
    private final ConcurrentHashMap<String, String> ticketToOpenid = new ConcurrentHashMap<>();

    @Override
    public UserAccountEntity LoginOrRegister(LoginUserEntity loginEntity) throws Exception {
        StrategyHandler<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> handler = defaultUserLoginStrategyFactory.strategyHandler();

        return handler.apply(loginEntity, new DefaultUserLoginStrategyFactory.DynamicContext());
    }

    @Override
    public void sendVerificationCode(VerificationCodeEntity verificationCodeEntity) {
        String code = RandomUtil.randomNumbers(6);

        if (verificationCodeEntity.getType().equals(VerificationTypeVO.PHONE.getCode())) {
            stringRedisTemplate.opsForValue().set(Constants.buildVerificationCodeKey(verificationCodeEntity.getPhone()), code, 120, TimeUnit.SECONDS);
            loginPort.sendVerificationPhoneCode(verificationCodeEntity.getPhone(), code);

        } else if (verificationCodeEntity.getType().equals(VerificationTypeVO.EMAIL.getCode())) {
            stringRedisTemplate.opsForValue().set(Constants.buildVerificationCodeKey(verificationCodeEntity.getEmail()), code, 120, TimeUnit.SECONDS);
            loginPort.sendVerificationEmailCode(verificationCodeEntity.getEmail(), code);
        }
    }

    @Override
    public String requestWxTicket() throws IOException {

        return loginPort.getWxQrCodeTicket();
    }

    @Override
    public void saveWxLoginState(String ticket, String openid) {
        log.info("用户 openid = {}，ticket = {} 扫码成功", openid, ticket);
        ticketToOpenid.put(ticket, openid);
    }

    @Override
    public Long checkLoginState(String ticket) {

        String openid = ticketToOpenid.get(ticket);
        if (openid == null) {
            return null;
        }
        UserDetailEntity userByOpenId = userRepository.getUser(openid, SelectUserType.SELECT_USER_OPENID);
        if (userByOpenId == null) {
            UserRegisterEntity userRegisterEntity = UserRegisterEntity.builder()
                    .createUserType(CreateUserType.CREATE_USER_OPENID)
                    .openid(openid)
                    .build();
            UserDetailEntity userDetailEntity = userRepository.createUser(userRegisterEntity);
            return userDetailEntity.getId();
        }

        StpUtil.login(userByOpenId.getId());
        return userByOpenId.getId();
    }

    @Override
    public void updatePassword(String str, SelectUserType selectUserType, String newPassword, String ReqCode) {
        UserDetailEntity userDetailEntity = userRepository.getUser(str, selectUserType);
        if (userDetailEntity == null) {
            throw new RuntimeException("该用户不存在");
        }

        String code = stringRedisTemplate.opsForValue().get(Constants.buildVerificationCodeKey(String.valueOf(str)));
        if (code == null || !code.equals(ReqCode)) {
            throw new RuntimeException("验证错误");
        }

        int index = userRepository.updateUserPasswordById(str, selectUserType, passwordEncoder.encode(newPassword));
        if (index != 1) {
            throw new RuntimeException("更新密码失败");
        }

        log.info("{} 用户更新密码成功", str);
    }

}
