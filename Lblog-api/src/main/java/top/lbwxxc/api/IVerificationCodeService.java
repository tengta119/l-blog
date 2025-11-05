package top.lbwxxc.api;


import top.lbwxxc.api.dto.VerificationCodeRequestDTO;
import top.lbwxxc.api.response.Response;

public interface IVerificationCodeService {

    Response<String> sendVerificationCode(VerificationCodeRequestDTO verificationCodeRequestDTO);
}
