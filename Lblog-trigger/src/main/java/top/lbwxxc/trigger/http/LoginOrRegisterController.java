package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.lbwxxc.api.ILoginOrRegisterService;
import top.lbwxxc.api.dto.LoginRequestDTO;
import top.lbwxxc.api.dto.LoginResponseDTO;
import top.lbwxxc.api.dto.WxLoginRequestDTO;
import top.lbwxxc.api.dto.WxLoginResponseDTO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.user.model.entity.UserAccountEntity;
import top.lbwxxc.domain.user.model.entity.LoginUserEntity;
import top.lbwxxc.domain.user.service.login.LoginService;
import top.lbwxxc.types.enums.ResponseCode;

@RestController
@CrossOrigin("*")
@RequestMapping()
public class LoginOrRegisterController implements ILoginOrRegisterService {

    @Resource
    private LoginService loginService;

    // 登录
    // 1、账号密码登录
    // 2、电话验证码登录，不存在账号时自动注册后登录
    @PostMapping("/login")
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
                    .phone(userAccountEntity.getPhone())
                    .email(userAccountEntity.getEmail())
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

    @PostMapping("/login/check")
    @Override
    public Response<WxLoginResponseDTO> wxLoginCheck(@RequestBody WxLoginRequestDTO wxLoginRequestDTO) {
        String ticket = wxLoginRequestDTO.getTicket();
        Long userId = loginService.checkLoginState(ticket);

        return Response.<WxLoginResponseDTO>builder()
                .data(new WxLoginResponseDTO(userId))
                .build();
    }

}
