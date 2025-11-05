package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.lbwxxc.api.IVerificationCodeService;
import top.lbwxxc.api.dto.VerificationCodeRequestDTO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.user.model.entity.VerificationCodeEntity;
import top.lbwxxc.domain.user.service.login.LoginService;
import top.lbwxxc.types.enums.ResponseCode;


@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/code")
public class VerificationCodeController implements IVerificationCodeService {

    @Resource
    private LoginService loginService;


    @Override
    @PostMapping("/send")
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
}
