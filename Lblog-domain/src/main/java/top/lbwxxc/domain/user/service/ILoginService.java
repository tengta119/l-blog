package top.lbwxxc.domain.user.service;


import top.lbwxxc.domain.user.model.entity.UserAccountEntity;
import top.lbwxxc.domain.user.model.entity.LoginUserEntity;
import top.lbwxxc.domain.user.model.entity.VerificationCodeEntity;

public interface ILoginService {

    UserAccountEntity LoginOrRegister(LoginUserEntity loginUserEntity) throws Exception;

    void sendVerificationCode(VerificationCodeEntity verificationCodeEntity);
}
