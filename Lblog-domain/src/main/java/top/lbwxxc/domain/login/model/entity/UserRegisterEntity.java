package top.lbwxxc.domain.login.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.lbwxxc.types.enums.CreateUserType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRegisterEntity {

    CreateUserType createUserType;

    private String phone;
    private String email;
    private String openid;
    private String password;
}
