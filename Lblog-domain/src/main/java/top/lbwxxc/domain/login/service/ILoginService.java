package top.lbwxxc.domain.login.service;


import top.lbwxxc.domain.login.model.entity.UserAccountEntity;
import top.lbwxxc.domain.login.model.entity.LoginUserEntity;
import top.lbwxxc.domain.login.model.entity.VerificationCodeEntity;
import top.lbwxxc.types.enums.SelectUserType;

import java.io.IOException;

public interface ILoginService {

    UserAccountEntity LoginOrRegister(LoginUserEntity loginUserEntity) throws Exception;

    void sendVerificationCode(VerificationCodeEntity verificationCodeEntity);

    String requestWxTicket() throws IOException;

    void saveWxLoginState(String ticket, String openid);

    Long checkLoginState(String ticket);

    void updatePassword(String str, SelectUserType selectUserType, String newPassword, String ReqCode);
}
