package top.lbwxxc.api.dto.login;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VerificationCodeRequestDTO {

    // 1：手机验证码，2：邮箱验证码
    String type;
    String phone;
    String email;
}
