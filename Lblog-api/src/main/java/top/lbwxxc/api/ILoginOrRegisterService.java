package top.lbwxxc.api;


import top.lbwxxc.api.dto.LoginRequestDTO;
import top.lbwxxc.api.dto.LoginResponseDTO;
import top.lbwxxc.api.dto.WxLoginRequestDTO;
import top.lbwxxc.api.dto.WxLoginResponseDTO;
import top.lbwxxc.api.response.Response;

public interface ILoginOrRegisterService {

    Response<LoginResponseDTO> loginOrRegister(LoginRequestDTO loginRequestDTO);


    Response<WxLoginResponseDTO> wxLoginCheck(WxLoginRequestDTO wxLoginRequestDTO);
}
