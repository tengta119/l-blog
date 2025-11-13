package top.lbwxxc.trigger.http;


import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.lbwxxc.api.ILoginOrRegisterService;
import top.lbwxxc.api.dto.*;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.login.model.entity.UserAccountEntity;
import top.lbwxxc.domain.login.model.entity.LoginUserEntity;
import top.lbwxxc.domain.login.service.ILoginService;
import top.lbwxxc.types.enums.ResponseCode;
import top.lbwxxc.types.enums.SelectUserType;
import top.lbwxxc.types.enums.VerificationTypeVO;

@RestController
@Slf4j
@RequestMapping("/login/")
public class LoginOrRegisterController implements ILoginOrRegisterService {

    @Resource
    private ILoginService loginService;

    // 登录
    // 1、账号密码登录
    // 2、电话验证码登录，不存在账号时自动注册后登录
    @PostMapping("login")
    @Override
    public Response<LoginResponseDTO> loginOrRegister(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginUserEntity loginUserEntity = LoginUserEntity.builder()
                .type(loginRequestDTO.getType())
                .phone(loginRequestDTO.getPhone())
                .email(loginRequestDTO.getEmail())
                .reqCode(loginRequestDTO.getCode())
                .reqPassword(loginRequestDTO.getPassword())
                .build();
        try {
            UserAccountEntity userAccountEntity = loginService.LoginOrRegister(loginUserEntity);
            LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                    .id(userAccountEntity.getId())
                    .token(userAccountEntity.getToken())
                    .build();

            return Response.<LoginResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(loginResponseDTO)
                    .build();

        } catch (Exception e) {
            return Response.<LoginResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(e.getMessage())
                    .build();
        }
    }

    @PostMapping("check")
    @Override
    public Response<WxLoginResponseDTO> wxLoginCheck(@RequestBody WxLoginRequestDTO wxLoginRequestDTO) {
        log.info("前端轮询：ticket {}", wxLoginRequestDTO.getTicket());
        String ticket = wxLoginRequestDTO.getTicket();
        Long userId = loginService.checkLoginState(ticket);
        WxLoginResponseDTO wxLoginResponseDTO = WxLoginResponseDTO.builder()
                .id(userId)
                .token(StpUtil.getTokenValue())
                .build();
        if (userId != null) {
            return Response.<WxLoginResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(wxLoginResponseDTO)
                    .build();
        } else {
            return Response.<WxLoginResponseDTO>builder()
                    .code(ResponseCode.WX_NOT_LOGIN.getCode())
                    .info(ResponseCode.WX_NOT_LOGIN.getInfo())
                    .build();
        }
    }

    @PostMapping("updatePassword")
    @Override
    public Response<String> updateUserPasswordByVerifyCode(@RequestBody UpdatePasswordByVerifyCodeRequestDTO updatePasswordByVerifyCodeRequestDTO) {
        String newPassword = updatePasswordByVerifyCodeRequestDTO.getNewPassword();
        String verificationCode = updatePasswordByVerifyCodeRequestDTO.getVerificationCode();
        String str = null;
        SelectUserType selectUserType = null;
        if (updatePasswordByVerifyCodeRequestDTO.getType().equals(VerificationTypeVO.EMAIL.getCode())) {
            str = updatePasswordByVerifyCodeRequestDTO.getEmail();
            selectUserType = SelectUserType.SELECT_USER_EMAIL;
        }  else if (updatePasswordByVerifyCodeRequestDTO.getType().equals(VerificationTypeVO.PHONE.getCode())) {
            str = updatePasswordByVerifyCodeRequestDTO.getPhone();
            selectUserType = SelectUserType.SELECT_USER_PHONE;
        }

        if (str == null) {
            throw new RuntimeException("请输入正确的账号");
        }

        try {
            loginService.updatePassword(str, selectUserType, newPassword, verificationCode);
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data("修改密码成功")
                    .build();
        } catch (Exception e) {
            return Response.<String>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(e.getMessage())
                    .build();
        }
    }

    @PostMapping("logout")
    @Override
    public Response<String> logout() {

        loginService.logout();

        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .build();
    }

}
