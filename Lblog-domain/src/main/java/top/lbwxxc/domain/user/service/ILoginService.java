package top.lbwxxc.domain.user.service;


import top.lbwxxc.domain.user.model.entity.UserAccountEntity;
import top.lbwxxc.domain.user.model.entity.LoginUserEntity;
import top.lbwxxc.domain.user.model.entity.VerificationCodeEntity;
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
