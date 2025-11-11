package top.lbwxxc.domain.login.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VerificationCodeEntity {

    private String type;
    private String code;
    private String phone;
    private String email;
}
