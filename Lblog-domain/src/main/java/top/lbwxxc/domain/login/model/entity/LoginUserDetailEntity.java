package top.lbwxxc.domain.login.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginUserDetailEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 密码
     */
    private String password;


}
