package top.lbwxxc.api;


import top.lbwxxc.api.dto.login.VerificationCodeRequestDTO;
import top.lbwxxc.api.response.Response;

public interface IVerificationCodeService {

    Response<String> sendVerificationCode(VerificationCodeRequestDTO verificationCodeRequestDTO);

    Response<String> sendWxTicket();
}
