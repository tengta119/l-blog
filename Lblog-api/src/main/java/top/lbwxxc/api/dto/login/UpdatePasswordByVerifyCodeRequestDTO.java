package top.lbwxxc.api.dto.login;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdatePasswordByVerifyCodeRequestDTO {

    // 1：手机验证码，2：邮箱验证码
    String type;
    String phone;
    String email;
    private String newPassword;
    private String verificationCode;
}
