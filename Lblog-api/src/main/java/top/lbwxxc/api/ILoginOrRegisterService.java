package top.lbwxxc.api;


import top.lbwxxc.api.dto.*;
import top.lbwxxc.api.response.Response;

public interface ILoginOrRegisterService {

    Response<LoginResponseDTO> loginOrRegister(LoginRequestDTO loginRequestDTO);


    Response<WxLoginResponseDTO> wxLoginCheck(WxLoginRequestDTO wxLoginRequestDTO);

    Response<String> updateUserPasswordByVerifyCode(UpdatePasswordByVerifyCodeRequestDTO updatePasswordByVerifyCodeRequestDTO);
}
