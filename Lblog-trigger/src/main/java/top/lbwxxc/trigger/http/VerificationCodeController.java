package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.lbwxxc.api.IVerificationCodeService;
import top.lbwxxc.api.dto.VerificationCodeRequestDTO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.login.model.entity.VerificationCodeEntity;
import top.lbwxxc.domain.login.service.login.LoginService;
import top.lbwxxc.types.enums.ResponseCode;

import java.io.IOException;


@RestController
@Slf4j
@RequestMapping("/code/")
public class VerificationCodeController implements IVerificationCodeService {

    @Resource
    private LoginService loginService;


    @Override
    @PostMapping("send")
    public Response<String> sendVerificationCode(@RequestBody VerificationCodeRequestDTO verificationCodeRequestDTO) {
        VerificationCodeEntity verificationCodeEntity = VerificationCodeEntity.builder()
                .type(verificationCodeRequestDTO.getType())
                .phone(verificationCodeRequestDTO.getPhone())
                .email(verificationCodeRequestDTO.getEmail())
                .build();
        loginService.sendVerificationCode(verificationCodeEntity);
        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data("发送成功")
                .build();
    }

    @PostMapping("wxTicket")
    @Override
    public Response<String> sendWxTicket() {
        String ticket;
        try {
            ticket = loginService.requestWxTicket();
        } catch (IOException e) {
            log.error(e.getMessage());
            return Response.<String>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(e.getMessage())
                    .build();
        }

        return Response.<String>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(ticket)
                .build();
    }


}
