package top.lbwxxc.domain.user.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginUserEntity {

    // 0： 账号密码，1：验证码, 2：二维码
    private String type;

    private String phone;

    private String reqPassword;

    private String code;
}
