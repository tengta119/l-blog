package top.lbwxxc.domain.login.service.login.node;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.login.adapter.repository.IUserAccountRepository;
import top.lbwxxc.domain.login.model.entity.UserAccountEntity;
import top.lbwxxc.domain.login.model.entity.LoginUserEntity;
import top.lbwxxc.domain.login.model.entity.UserDetailEntity;
import top.lbwxxc.types.enums.SelectUserType;
import top.lbwxxc.types.enums.VerificationTypeVO;
import top.lbwxxc.domain.login.service.login.AbstractLoginUserSupport;
import top.lbwxxc.domain.login.service.login.factory.DefaultUserLoginStrategyFactory;
import top.lbwxxc.types.design.framework.tree.StrategyHandler;

@Service
@Slf4j
public class RootNode extends AbstractLoginUserSupport<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> {

    @Resource
    private IUserAccountRepository userRepository;
    @Resource
    private LoginNode loginNode;
    @Resource
    private RegisterNode registerNode;


    @Override
    protected UserAccountEntity doApply(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("登录注册 - RootNode 从数据库加载用户信息, requestParameter：{}", requestParameter);

        UserDetailEntity userDetail = null;
        if (requestParameter.getType().equals(VerificationTypeVO.PHONE.getCode())) {
            String phone = requestParameter.getPhone();
            userDetail = userRepository.getUser(phone, SelectUserType.SELECT_USER_PHONE);

        } else if (requestParameter.getType().equals(VerificationTypeVO.EMAIL.getCode())) {
            String email = requestParameter.getEmail();
            userDetail = userRepository.getUser(email, SelectUserType.SELECT_USER_EMAIL);
        }

        log.info("用户信息为 {}", userDetail);

        if (userDetail != null) {
            dynamicContext.setUserId(userDetail.getId());
            dynamicContext.setPassword(userDetail.getPassword());
        }

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, UserAccountEntity> get(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws Exception {
        if (dynamicContext.getUserId() != null) {
            return loginNode;
        } else if (requestParameter.getReqCode() != null) {
            log.info("该用户未注册 {}", requestParameter);
            return registerNode;
        }
        return defaultStrategyHandler;
    }
}
